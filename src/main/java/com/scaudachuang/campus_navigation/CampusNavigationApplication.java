package com.scaudachuang.campus_navigation;

import com.scaudachuang.campus_navigation.fx.AbstractFxApplication;
import com.scaudachuang.campus_navigation.fx.FxmlView;
import com.scaudachuang.campus_navigation.fx.view.LoginView;
import com.scaudachuang.campus_navigation.fx.view.SplashScreenCustom;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CampusNavigationApplication extends AbstractFxApplication {

    public static void main(String[] args) {
        run(CampusNavigationApplication.class,
                Arrays.asList(FxmlView.MAIN,FxmlView.LOGIN),
                FxmlView.MAIN, args);    }
}
