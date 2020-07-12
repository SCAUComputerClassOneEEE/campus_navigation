package com.scaudachuang.campus_navigation.fx.controller;

import com.scaudachuang.campus_navigation.entity.*;
import com.scaudachuang.campus_navigation.fx.model.DataEnum;
import com.scaudachuang.campus_navigation.fx.model.DataTab;
import com.scaudachuang.campus_navigation.service.AdminService;
import com.scaudachuang.campus_navigation.service.BuildingService;
import com.scaudachuang.campus_navigation.service.CommentService;
import com.scaudachuang.campus_navigation.service.UserService;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);  //选项卡可关闭
        vBox.setPadding(new Insets(0));
    }

    @FXML
    private void Admin() throws ClassNotFoundException {
        if (tabMap.containsKey(DataEnum.DataForm.Admin)){
            tabPane.getSelectionModel().select(tabMap.get(DataEnum.DataForm.Admin));
        }else{
            DataTab<Admin> adminDataTab = new DataTab<>(DataEnum.DataForm.Admin,adminService.findAll());

            tabPane.getTabs().add(adminDataTab);
            tabPane.getSelectionModel().select(adminDataTab);

            tabMap.put(DataEnum.DataForm.Admin,adminDataTab);
            adminDataTab.setOnClosed(event -> tabMap.remove(DataEnum.DataForm.Admin));
        }
    }
    @FXML
    private void Building() throws ClassNotFoundException {
        if (tabMap.containsKey(DataEnum.DataForm.Building)){
            tabPane.getSelectionModel().select(tabMap.get(DataEnum.DataForm.Building));
        }else{
            DataTab<Building> buildingDataTab = new DataTab<>(DataEnum.DataForm.Building, buildingService.finAll());

            tabPane.getTabs().add(buildingDataTab);
            tabPane.getSelectionModel().select(buildingDataTab);

            tabMap.put(DataEnum.DataForm.Building,buildingDataTab);
            buildingDataTab.setOnClosed(event -> tabMap.remove(DataEnum.DataForm.Building));
        }
    }
    @FXML
    private void Comment() throws ClassNotFoundException {
        if (tabMap.containsKey(DataEnum.DataForm.Comment)){
            tabPane.getSelectionModel().select(tabMap.get(DataEnum.DataForm.Comment));
        }else{
            DataTab<Comment> commentDataTab = new DataTab<>(DataEnum.DataForm.Comment,commentService.findAll());

            tabPane.getTabs().add(commentDataTab);
            tabPane.getSelectionModel().select(commentDataTab);

            tabMap.put(DataEnum.DataForm.Comment,commentDataTab);
            commentDataTab.setOnClosed(event -> tabMap.remove(DataEnum.DataForm.Comment));
        }
    }
    @FXML
    private void User() throws ClassNotFoundException {
        if (tabMap.containsKey(DataEnum.DataForm.User)){
            tabPane.getSelectionModel().select(tabMap.get(DataEnum.DataForm.User));
        }else{
            DataTab<User> userDataTab = new DataTab<>(DataEnum.DataForm.User,userService.findAll());

            tabPane.getTabs().add(userDataTab);
            tabPane.getSelectionModel().select(userDataTab);

            tabMap.put(DataEnum.DataForm.User,userDataTab);
            userDataTab.setOnClosed(event -> tabMap.remove(DataEnum.DataForm.User));
        }
    }


}
