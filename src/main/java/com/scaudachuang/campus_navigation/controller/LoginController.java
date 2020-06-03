package com.scaudachuang.campus_navigation.controller;

import com.alibaba.fastjson.JSONObject;
import com.scaudachuang.campus_navigation.config.WxAppConfig;
import com.scaudachuang.campus_navigation.controller.wxException.WxConnectionException;
import com.scaudachuang.campus_navigation.entity.User;
import com.scaudachuang.campus_navigation.service.UserService;
import com.scaudachuang.campus_navigation.utils.http.HttpClientUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回Map<String,String> retMap
 * {
 *      status: xxx
 *      definedLogStatus: xxx (only success)
 *      msg: xxx
 * }
 */
@RestController
@RequestMapping("/login")
public class LoginController  {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserService userService;

    @Resource
    private WxAppConfig wxAppConfig;

    /**
     * 已通过微信授权，重新登陆
     * @param code code
     * @param nickName 前端可以获得
     * @return 自定义登陆状态String
     */
    @RequestMapping(value = "/wxLogin")
    public Map<String,String> wxLogin(@RequestParam("code") String code,
                                      @RequestParam("nickName") String nickName)
                                            throws URISyntaxException {
        Map<String,String> retLoginMap = new HashMap<>();
        try{
            JSONObject jsonObject = this.getWxResult(code);
            String open_id = jsonObject.get("openid").toString();
            User user = userService.findByOpenId(Integer.parseInt(open_id));
            retLoginMap.put("status","200");
            retLoginMap.put("definedLogStatus", user.getDefinedLoginStatus());
            retLoginMap.put("msg","Login successful.");

            logger.info("Login successful - " + nickName);

            userService.updateUserByOpenId(Integer.parseInt(open_id),nickName);//更新用户信息
        } catch (IOException | WxConnectionException exception) {
            retLoginMap.put("status","500");
            retLoginMap.put("msg","WeChat error.");

            logger.error("WeChat error: " + exception.getMessage());
            return retLoginMap;
        }
        return retLoginMap;
    }

    /**
     * 尚未授权
     * @param code code
     * @param encryptedData 加密信息
     * @param iv 加密
     * @return 自定义登陆状态String
     */
    @RequestMapping(value = "/decodeUserInfo")
    public Map<String,String> decodeUserInfo(
            @RequestParam("code") String code,
            @RequestParam("encryptedData") String encryptedData,
            @RequestParam("iv") String iv)
            throws URISyntaxException {
        Map<String,String> retMap = new HashMap<>();
        //用户的完整信息userInfo
        try{
            JSONObject jsonObject = this.getWxResult(code);
            String sessionKey = jsonObject.get("session_key").toString();
            String open_id = jsonObject.get("openid").toString();
            JSONObject userInfo = getUserInfo(encryptedData, sessionKey, iv);
            User user = userService.findByOpenId(Integer.parseInt(open_id));
            if (user != null){
                //user is existed 说明用户清除授权，再次授权
                userService.updateUserByOpenId(Integer.parseInt(open_id),userInfo);//更新用户信息
                logger.info("Authorization successful - " + user.getUserName());
            }else {
                //user is not existed 说明用户第一次授权
                String user_name = userService.insertRegUser(userInfo);
                logger.info("Authorization successful - " + user_name);
            }
            retMap.put("status","200");
            retMap.put("definedLogStatus", userService.findByOpenId(Integer.parseInt(open_id)).getDefinedLoginStatus());
            retMap.put("msg","Authorization successful.");

            return retMap;
        } catch (NoSuchAlgorithmException | BadPaddingException |
                InvalidKeyException | InvalidAlgorithmParameterException |
                NoSuchPaddingException | InvalidParameterSpecException |
                NoSuchProviderException | IllegalBlockSizeException |
                IOException | WxConnectionException e) {
            retMap.put("status","500");
            retMap.put("msg","Decryption failed.");

            logger.error("Decryption failed: " + e.getMessage());
            return retMap;
        }
    }

    public JSONObject getWxResult(String code ) throws URISyntaxException, IOException, WxConnectionException {
        if(code == null||code.length() == 0)
            throw new WxConnectionException("Code error.");
        Map<String, String> param = new HashMap<>();
        param.put("appid", wxAppConfig.getWX_LOGIN_APP_ID());
        param.put("secret", wxAppConfig.getWX_LOGIN_SECRET());
        param.put("js_code",code);
        param.put("grant_type", wxAppConfig.getWX_LOGIN_GRANT_TYPE());
        String wxResult = HttpClientUtil.doGet(wxAppConfig.getWX_LOGIN_URL(),param);
        JSONObject jsonObject = JSONObject.parseObject(wxResult);

        if (jsonObject == null) throw new WxConnectionException("WxService connection error. But no result.");
        else if (jsonObject.getString("openid") == null || jsonObject.getString("session_key") == null){
            throw new WxConnectionException("WxService connection error. " +
                    "error_code: " + jsonObject.getString("errorcode") + " err_msg: " + jsonObject.getString("errmsg")
            ); }
        else return jsonObject;
    }

    public static JSONObject getUserInfo(String encryptedData,String sessionKey,String iv) throws NoSuchPaddingException,
            NoSuchAlgorithmException, NoSuchProviderException, InvalidParameterSpecException, BadPaddingException,
            IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyByte.length % base != 0) {
            int groups = keyByte.length / base + 1;
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
            keyByte = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
        byte[] resultByte = cipher.doFinal(dataByte);
        if (null != resultByte && resultByte.length > 0) {
            String result = new String(resultByte, StandardCharsets.UTF_8);
            return JSONObject.parseObject(result);
        }
        return null;
    }
}
