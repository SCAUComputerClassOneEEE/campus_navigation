package com.scaudachuang.campus_navigation.fx.controller;

import com.scaudachuang.campus_navigation.entity.Admin;
import com.scaudachuang.campus_navigation.fx.AbstractFxApplication;
import com.scaudachuang.campus_navigation.fx.FxmlView;
import com.scaudachuang.campus_navigation.fx.StageManager;
import com.scaudachuang.campus_navigation.service.AdminService;
import de.felixroske.jfxsupport.FXMLController;
import de.felixroske.jfxsupport.FXMLView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@FXMLController
@Component
public class SignInController implements Initializable{

    //通过service层，获取数据库的数据
    @Resource
    private AdminService adminService;

    @FXML
    private Button login;

    @FXML
    private TextField userID;

    @FXML
    private PasswordField userPassword;

    @FXML
    private CheckBox rememberPassword;

    @FXML
    private CheckBox autoLogin;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> loginMassage = getLoginMassage();
        userID.setText(loginMassage.get(0).substring(9));
        if(loginMassage.get(2).substring(17).equals("true")){
            userPassword.setText(loginMassage.get(1).substring(9));
        }
    }

    @FXML
    public void login(Event event){
        //输入的账户和密码都不为空
        if(!userID.getText().equals("")&&!userPassword.getText().equals("")){
            //根据账户找到用户信息
            Admin admin = adminService.findAdminByAdminName(userID.getText());
            if(admin!=null){
                String InputPassword = userPassword.getText();
                if (admin.getPassword().equals(InputPassword)){
                    System.out.println("登录成功");
                    AbstractFxApplication.stageManager.close();
                    AbstractFxApplication.stageManager.switchScene(FxmlView.MAIN);
                    AbstractFxApplication.stageManager.show();
                }
                else System.out.println("密码错误");
            }
            else {
                System.out.println("用户不存在");
            }
        }
        else {
            System.out.println("用户名或密码不能为空");
            //Alert alert = new Alert();
        }

    }

    @FXML
    public void cancel(Event event){
        StageManager.primaryStage.close();
    }

    @FXML
    public void rememberPassword(Event event){
        System.out.println("记住密码");
    }


    private ArrayList<String> getLoginMassage(){
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            File f = new File(System.getProperty("user.dir") +"\\src\\main\\resources\\loginMassage\\massage");
            FileReader fr = new FileReader(f);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
