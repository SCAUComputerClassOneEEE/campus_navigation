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
        launch(CampusNavigationApplication.class,LoginView.class,new SplashScreenCustom(), args);
    }
}
