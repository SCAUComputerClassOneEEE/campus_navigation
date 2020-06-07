package com.scaudachuang.campus_navigation.fx.view;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;

@FXMLView(value = "/fxml/login.fxml",title = "loginView")
public class LoginView extends AbstractFxmlView {

    private AnchorPane root = new AnchorPane();
    private Label title = new Label();
    private Label userNameLabel = new Label();
    private Label passwordLabel = new Label();
    private TextField userName = new TextField();
    private TextField password = new TextField();
    private CheckBox rememberPassword = new CheckBox();
    private CheckBox autoLogin = new CheckBox();
    private Button login = new Button("登录");
    private Button cancel = new Button("取消");

    public LoginView(){
        root.setPrefSize(600,400);
        title.setText("数据库管理员登录界面");
        userNameLabel.setText("账号");
        passwordLabel.setText("密码");
        rememberPassword.setText("记住密码");
        autoLogin.setText("自动登录");
        title.setLayoutX(246);
        title.setLayoutY(74);
        userNameLabel.setLayoutX(214);
        userNameLabel.setLayoutY(161);

        passwordLabel.setLayoutX(214);
        passwordLabel.setLayoutY(226);

        userName.setLayoutX(268);
        userName.setLayoutY(156);

        password.setLayoutX(268);
        password.setLayoutY(221);

        rememberPassword.setLayoutX(272);
        rememberPassword.setLayoutY(276);

        autoLogin.setLayoutX(383);
        autoLogin.setLayoutY(276);

        login.setLayoutX(396);
        login.setLayoutY(335);

        cancel.setLayoutX(470);
        cancel.setLayoutY(335);

        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(400);
        rectangle.setWidth(200);
        rectangle.setFill(Color.valueOf("lightskyblue"));
        rectangle.setLayoutX(0);
        rectangle.setLayoutY(0);

        root.setStyle("-fx-background-color: white");
        root.getChildren().addAll(title,userNameLabel,passwordLabel,userName,password,rememberPassword,autoLogin,rectangle,login,cancel);

    }
}
