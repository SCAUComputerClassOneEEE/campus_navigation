package com.scaudachuang.campus_navigation.fx.controller;

import com.scaudachuang.campus_navigation.service.BuildingService;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.Initializable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
@Component
public class ManagementViewController implements Initializable {

    @Resource
    private BuildingService buildingService;
    void fool(){
        buildingService.finAll();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
