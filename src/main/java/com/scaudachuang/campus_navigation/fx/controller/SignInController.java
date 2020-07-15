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
import java.io.*;
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


    private ArrayList<String> loginMassage;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //获取登录信息
        loginMassage = getLoginMassage();
        //记录上一次使用的用户名
        userID.setText(loginMassage.get(0).substring(9));
        //判断是否记住密码
        if(loginMassage.get(2).substring(17).equals("true")){
            rememberPassword.setSelected(true);
            userPassword.setText(loginMassage.get(1).substring(9));
        }
    }

    @FXML
    public void login(Event event){
       //异常框
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");

        //输入的账户和密码都不为空
        if(!userID.getText().equals("")&&!userPassword.getText().equals("")){
            //根据账户找到用户信息
            Admin admin = adminService.findAdminByAdminName(userID.getText());
            if(admin!=null){
                String InputPassword = userPassword.getText();
                if (admin.getPassword().equals(InputPassword)){
                    //System.out.println("登录成功");
                    //关闭原界面
                    AbstractFxApplication.stageManager.close();
                    //转换成数据界面
                    AbstractFxApplication.stageManager.switchScene(FxmlView.MAIN);
                    AbstractFxApplication.stageManager.show();
                    //改变登录信息
                    setLoginMassage();
                }
                else{
                    alert.setHeaderText("密码错误！");
                    alert.setContentText("请检查账号密码是否准确");
                    alert.showAndWait();
                    //System.out.println("密码错误");
                }
            }
            else {
                alert.setHeaderText("用户不存在！");
                alert.setContentText("请使用正确的管理员账号");
                alert.showAndWait();
                //System.out.println("用户不存在");
            }
        }
        else {
            alert.setHeaderText("用户名或密码不能为空！");
            alert.setContentText("请检查确保您已经输入账号与密码");
            alert.showAndWait();
            //System.out.println("用户名或密码不能为空");
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


    //读入登录数据
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

    //更新登录数据
    private void setLoginMassage(){
        loginMassage.set(0,loginMassage.get(0).substring(0,9)+userID.getText());
        String password = "";
        if(rememberPassword.selectedProperty().getValue()){
            password = userPassword.getText();
        }
        loginMassage.set(1,loginMassage.get(1).substring(0,9)+password);
        loginMassage.set(2,loginMassage.get(2).substring(0,17)+rememberPassword.selectedProperty().getValue().toString());
        try {
            File f = new File(System.getProperty("user.dir") +"\\src\\main\\resources\\loginMassage\\massage");
            FileWriter fw = new FileWriter(f);
            BufferedWriter bf = new BufferedWriter(fw);
            // 按行读取字符串
            for(String str:loginMassage){
                bf.write(str);
                bf.write("\r\n");
            }
            bf.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
