package com.scaudachuang.campus_navigation;

import com.scaudachuang.campus_navigation.view.LoginFXMLView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CampusNavigationApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(CampusNavigationApplication.class, LoginFXMLView.class, args);
    }

//    @Override
//    public void start(Stage stage) {
//        BorderPane root = new BorderPane();
////        root.setLeft(new Button());
//        stage.setScene(new Scene(root));
//        stage.setTitle("??");
//        stage.show();
//    }
}
