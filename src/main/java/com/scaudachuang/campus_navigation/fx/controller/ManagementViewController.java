package com.scaudachuang.campus_navigation.fx.controller;

import com.scaudachuang.campus_navigation.fx.model.DataEnum;
import com.scaudachuang.campus_navigation.service.AdminService;
import com.scaudachuang.campus_navigation.service.BuildingService;
import com.scaudachuang.campus_navigation.service.CommentService;
import com.scaudachuang.campus_navigation.service.UserService;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.swing.border.EmptyBorder;
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
    private VBox vBox1;
    @FXML
    private Pane pane;
    @FXML
    private VBox vBox2;
    @FXML
    private Button edit;
    @FXML
    private Button sent;


    /**
     * 或者用监听器监听Map的改变
     */
    private final Map<DataEnum.DataForm, DataTab> tabMap = new HashMap<>();
    private DataTab dataTab;
    private TextArea textArea;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);  //选项卡可关闭
        vBox1.setPadding(new Insets(0));
        vBox2.setPadding(new Insets(0));
        textArea = new TextArea();
    }

    @FXML
    private void Admin() throws ClassNotFoundException { displayTab(DataEnum.DataForm.Admin); }
    @FXML
    private void Building() throws ClassNotFoundException { displayTab(DataEnum.DataForm.Building); }
    @FXML
    private void Comment() throws ClassNotFoundException { displayTab(DataEnum.DataForm.Comment); }
    @FXML
    private void User() throws ClassNotFoundException { displayTab(DataEnum.DataForm.User); }
    @FXML
    private void Announcement() { displayNoticement(); }
    @FXML
    private void edit(){//点击编辑按钮，将textArea设置成可编辑状态

    }
    @FXML
    private void sent(){//点击发布，保存文本内容，并将textArea设置为不可编辑状态

    }


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


    private void displayNoticement(){

        textArea.setPrefHeight(300);
        textArea.setPrefWidth(600);
        textArea.setLayoutX(100);
        textArea.setLayoutY(100);
        textArea.setEditable(true);
        edit.setVisible(true);
        sent.setVisible(true);
        pane.getChildren().add(textArea);


    }
}
