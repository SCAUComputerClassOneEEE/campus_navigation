package com.scaudachuang.campus_navigation;

import com.scaudachuang.campus_navigation.fx.view.LoginView;
import com.scaudachuang.campus_navigation.fx.view.SplashScreenCustom;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CampusNavigationApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(CampusNavigationApplication.class, args);
    }


    @Override
    public void start(Stage stage) {
        LoginView loginView = new LoginView();
        stage.setScene(new Scene(loginView.getRoot()));
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setTitle("登录");
        stage.show();
    }
}
