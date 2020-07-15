package com.scaudachuang.campus_navigation.fx.controller;

import com.scaudachuang.campus_navigation.fx.model.DataEnum;
import com.scaudachuang.campus_navigation.service.AdminService;
import com.scaudachuang.campus_navigation.service.BuildingService;
import com.scaudachuang.campus_navigation.service.CommentService;
import com.scaudachuang.campus_navigation.service.UserService;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

@FXMLController
@Component
public class ManagementViewController implements Initializable {

    @Resource
    private BuildingService buildingService;
    @Resource
    private AdminService adminService;
    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;

    @FXML
    private TabPane tabPane;
    @FXML
    private VBox vBox;

    /**
     * 或者用监听器监听Map的改变
     */
    private final Map<DataEnum.DataForm, DataTab> tabMap = new HashMap<>();
    private DataTab dataTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);  //选项卡可关闭
        vBox.setPadding(new Insets(0));
    }

    @FXML
    private void Admin() throws ClassNotFoundException { displayTab(DataEnum.DataForm.Admin); }
    @FXML
    private void Building() throws ClassNotFoundException { displayTab(DataEnum.DataForm.Building); }
    @FXML
    private void Comment() throws ClassNotFoundException { displayTab(DataEnum.DataForm.Comment); }
    @FXML
    private void User() throws ClassNotFoundException { displayTab(DataEnum.DataForm.User); }

    private void displayTab(DataEnum.DataForm dataForm) throws ClassNotFoundException {
        if (tabMap.containsKey(dataForm)){
            tabPane.getSelectionModel().select(tabMap.get(dataForm));
        }else {
            switch (dataForm){
                case Admin:{dataTab = new DataTab(dataForm,adminService.findAll());break;}
                case Building:{dataTab = new DataTab(dataForm,buildingService.finAll());break;}
                case Comment:{dataTab = new DataTab(dataForm,commentService.findAll());break;}
                case User:{dataTab = new DataTab(dataForm,userService.findAll());break;}
            }
            tabPane.getTabs().add(dataTab);
            tabPane.getSelectionModel().select(dataTab);

            tabMap.put(dataForm,dataTab);
            dataTab.setOnClosed(event -> tabMap.remove(dataForm));
        }
    }
}
