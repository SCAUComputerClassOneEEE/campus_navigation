package com.scaudachuang.campus_navigation.fx.controller;

import com.scaudachuang.campus_navigation.entity.Admin;
import com.scaudachuang.campus_navigation.fx.StageManager;
import com.scaudachuang.campus_navigation.service.AdminService;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
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

    private StageManager stageManager;

    @FXML
    private Button login;

    @FXML
    private TextField userID;

    @FXML
    private TextField userPassword;

    @FXML
    private CheckBox rememberPassword;

    @FXML
    private CheckBox autoLogin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        autoLogin.setDisable(true);
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
        userID.setText(arrayList.get(0).substring(9));
        if(arrayList.get(2).substring(17).equals("true")){
            userPassword.setText(arrayList.get(1).substring(9));
        }
    }

    @FXML
    public void dengLu(Event event){
        Admin admin = adminService.findAdminByAdminName(userID.getText());
        String password = userPassword.getText();
        if (admin.getPassword().equals(password)) System.out.println("yes");
        else System.out.println("error");

    }

    @FXML
    public void cancel(Event event){
        StageManager.myStage.close();
    }

    @FXML
    public void re(Event event){
        System.out.println("记住密码");
    }


}
