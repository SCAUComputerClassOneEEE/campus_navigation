package com.scaudachuang.campus_navigation.controller;

import com.scaudachuang.campus_navigation.CampusNavigationApplication;
import com.scaudachuang.campus_navigation.service.BuildingService;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.annotation.Resource;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class LoginController extends BorderPane implements Initializable {

    @FXML
    public Button button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
