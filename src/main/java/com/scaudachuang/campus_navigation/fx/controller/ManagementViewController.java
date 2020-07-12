package com.scaudachuang.campus_navigation.fx.controller;

import com.scaudachuang.campus_navigation.entity.Admin;
import com.scaudachuang.campus_navigation.entity.Building;
import com.scaudachuang.campus_navigation.entity.Comment;
import com.scaudachuang.campus_navigation.entity.User;
import com.scaudachuang.campus_navigation.service.AdminService;
import com.scaudachuang.campus_navigation.service.BuildingService;
import com.scaudachuang.campus_navigation.service.CommentService;
import com.scaudachuang.campus_navigation.service.UserService;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.bouncycastle.crypto.paddings.TBCPadding;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
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
    private Button Admin ;
    @FXML
    private Button Building ;
    @FXML
    private Button Comment ;
    @FXML
    private Button User ;
    @FXML
    private TabPane tabPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private VBox vBox;
    @FXML
    private BorderPane borderPane;

    //用于解决选项卡重复打开问题
    public boolean adFlag = true;
    public boolean buFlag = true;
    public boolean cmFlag = true;
    public boolean usFlag = true;

    private Tab admin = new Tab("admin");
    private Tab building = new Tab("building");
    private Tab comment = new Tab("comment");
    private Tab user = new Tab("user");
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);  //选项卡可关闭
        vBox.setPadding(new Insets(0));
        //设置tab为可关闭
        admin.setOnClosed(event -> adFlag = true);
        building.setOnClosed(event -> buFlag = true);
        comment.setOnClosed(event -> cmFlag = true);
        user.setOnClosed(event -> usFlag = true);
    }
    @FXML
    private void Admin(){
        if(adFlag){
        adFlag = false;
        List<Admin> adminList = adminService.findAll();
        ObservableList<Admin> adminObservableList = FXCollections.observableArrayList(adminList);
        tabPane.getTabs().add(admin);
        TableView<Admin> tableView = new TableView<>(adminObservableList);
        admin.setContent(tableView);
        //按列数据加载
        //账号
        TableColumn<Admin,String> tc_admin_name = new TableColumn<>("admin_name");
               tableView.getColumns().add(tc_admin_name);
               tc_admin_name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAdminName()));

        //密码
        TableColumn<Admin,String> tc_password = new TableColumn<>("password");
        tableView.getColumns().add(tc_password);
        tc_password.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPassword()));

        //日期
        TableColumn<Admin,String> tc_date = new TableColumn<>("date");
        tableView.getColumns().add(tc_date);
        tc_date.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRegTime().toString()));
        }
        tabPane.getSelectionModel().select(admin);
    }
    @FXML
    private void Building(){
        if(buFlag) {
            buFlag = false;
            List<Building> buildingList = buildingService.finAll();
            ObservableList<Building> buildingObservableList = FXCollections.observableArrayList(buildingList);
            tabPane.getTabs().add(building);
            TableView<Building> tableView = new TableView<>(buildingObservableList);
            building.setContent(tableView);
            //加载数据
            //建筑名字name
            TableColumn<Building, String> tc_name = new TableColumn<>("name");
            tableView.getColumns().add(tc_name);
            tc_name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));

            //建筑位置position
            TableColumn<Building, String> tc_position = new TableColumn<>("position");
            tableView.getColumns().add(tc_position);
            tc_position.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPosition().toString()));

            //简介
            TableColumn<Building, String> tc_introduction = new TableColumn<>("brief_introduction");
            tableView.getColumns().add(tc_introduction);
            tc_introduction.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getBriefIntroduction()));

            //浏览次数
            TableColumn<Building, Number> tc_browse = new TableColumn<>("number_of_browse");
            tableView.getColumns().add(tc_browse);
            tc_browse.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getNumberOfBrowse()));

            //评论条数
            TableColumn<Building, Number> tc_commentNumber = new TableColumn<>("number_of_comment");
            tableView.getColumns().add(tc_commentNumber);
            tc_commentNumber.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getNumberOfComment()));

            //相片编码
            TableColumn<Building, String> tc_img = new TableColumn<>("img");
            tableView.getColumns().add(tc_img);
            tc_img.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getImg()));
        }
        tabPane.getSelectionModel().select(building);
    }
    @FXML
    private void Comment(){
        if(cmFlag) {
            cmFlag = false;
            List<Comment> commentList = commentService.findAll();
            ObservableList<Comment> commentObservableList = FXCollections.observableArrayList(commentList);
            tabPane.getTabs().add(comment);
            TableView<Comment> tableView = new TableView<>(commentObservableList);
            comment.setContent(tableView);

            //b_id
            TableColumn<Comment, Number> tc_bid = new TableColumn<>("b_id");
            tableView.getColumns().add(tc_bid);
            tc_bid.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getBid()));

            //u_id
            TableColumn<Comment, Number> tc_uid = new TableColumn<>("u_id");
            tableView.getColumns().add(tc_uid);
            tc_uid.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getUid()));

            //message
            TableColumn<Comment, String> tc_message = new TableColumn<>("message");
            tableView.getColumns().add(tc_message);
            tc_message.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMessage()));

            //time_of_commentary
            TableColumn<Comment, String> tc_time_of_commentary = new TableColumn<>("time_of_commentary");
            tableView.getColumns().add(tc_time_of_commentary);
            tc_time_of_commentary.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTimeOfCommentary().toString()));

            //number_of_praise
            TableColumn<Comment, Number> tc_number_of_praise = new TableColumn<>("number_of_praise");
            tableView.getColumns().add(tc_number_of_praise);
            tc_number_of_praise.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getNumberOfPraise()));
        }
        tabPane.getSelectionModel().select(comment);
    }
    @FXML
    private void User() {
        if (usFlag) {
            usFlag = false;
            List<User> userList = userService.findAll();
            ObservableList<User> userObservableList = FXCollections.observableArrayList(userList);
            tabPane.getTabs().add(user);
            TableView<User> tableView = new TableView<>(userObservableList);
            user.setContent(tableView);

            //数据加载
            //open_id
            TableColumn<User, String> tc_open_id = new TableColumn<>("open_id");
            tableView.getColumns().add(tc_open_id);
            tc_open_id.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getOpenId()));

            //session_key
            TableColumn<User, String> tc_session_key = new TableColumn<>("session_key");
            tableView.getColumns().add(tc_session_key);
            tc_session_key.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getSessionKey()));

            //user_name
            TableColumn<User, String> tc_user_name = new TableColumn<>("user_name");
            tableView.getColumns().add(tc_user_name);
            tc_user_name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUserName()));

            //user_info
            TableColumn<User, String> tc_user_info = new TableColumn<>("user_info");
            tableView.getColumns().add(tc_user_info);
            tc_user_info.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUserInfo()));

            //curr_log_time
            TableColumn<User, String> tc_curr_log_time = new TableColumn<>("curr_log_time");
            tableView.getColumns().add(tc_curr_log_time);
            tc_curr_log_time.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCurrLogTime().toString()));

            //reg_time
            TableColumn<User, String> tc_reg_time = new TableColumn<>("reg_time");
            tableView.getColumns().add(tc_reg_time);
            tc_reg_time.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getRegTime().toString()));
        }
        tabPane.getSelectionModel().select(user);
    }
}
