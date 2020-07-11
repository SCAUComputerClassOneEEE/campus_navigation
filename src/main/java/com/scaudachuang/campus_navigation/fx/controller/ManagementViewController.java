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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private void Admin(){
        List<Admin> adminList = adminService.findAll();
        ObservableList<Admin> adminObservableList = FXCollections.observableArrayList(adminList);
        Tab admin = new Tab("admin");
        tabPane.getTabs().add(admin);
        TableView<Admin> tableView = new TableView<>(adminObservableList);
        admin.setContent(tableView);
        //按列数据加载
        //账号
        TableColumn<Admin,String> tc_admin_name = new TableColumn<>("admin_name");
               tableView.getColumns().add(tc_admin_name);
               tc_admin_name.setCellValueFactory(param -> {
                    SimpleStringProperty admin_name = new SimpleStringProperty(param.getValue().getAdminName());
                   return  admin_name;
               });

        //密码
        TableColumn<Admin,String> tc_password = new TableColumn<>("password");
        tableView.getColumns().add(tc_password);
        tc_password.setCellValueFactory(param -> {
            SimpleStringProperty password = new SimpleStringProperty(param.getValue().getPassword());
            return  password;
        });

        //日期
        TableColumn<Admin,String> tc_date = new TableColumn<>("date");
        tableView.getColumns().add(tc_date);
        tc_date.setCellValueFactory(param -> {
            SimpleStringProperty date = new SimpleStringProperty(param.getValue().getRegTime().toString());
            return date;
        });



    }
    @FXML
    private void Building(){
       List<Building> buildingList = buildingService.finAll();
       ObservableList<Building> buildingObservableList =  FXCollections.observableArrayList(buildingList);
        Tab building = new Tab("building");
        tabPane.getTabs().add(building);
        TableView<Building> tableView = new TableView<Building>(buildingObservableList);
        building.setContent(tableView);
        //加载数据
        //建筑名字name
        TableColumn<Building,String> tc_name = new TableColumn<Building,String>("name");
        tableView.getColumns().add(tc_name);
        tc_name.setCellValueFactory(param -> {
            SimpleStringProperty name =  new SimpleStringProperty(param.getValue().getName());
            return name;
        });

        //建筑位置position
        TableColumn<Building, String> tc_position= new TableColumn<Building,String>("position");
        tableView.getColumns().add(tc_position);
        tc_position.setCellValueFactory(param -> {
            SimpleStringProperty position = new SimpleStringProperty(param.getValue().getPosition().toString());
            return position;
        });

        //简介
        TableColumn<Building,String> tc_introduction = new TableColumn<>("brief_introduction");
        tableView.getColumns().add(tc_introduction);
        tc_introduction.setCellValueFactory(param -> {
            SimpleStringProperty brief_introduction = new SimpleStringProperty(param.getValue().getBriefIntroduction());
            return brief_introduction;
        });

        //浏览次数
        TableColumn<Building,Number> tc_browse = new TableColumn<>("number_of_browse");
        tableView.getColumns().add(tc_browse);
        tc_browse.setCellValueFactory(param -> {
            SimpleIntegerProperty number_of_browse = new SimpleIntegerProperty(param.getValue().getNumberOfBrowse());
            return number_of_browse;
        });

        //评论条数
        TableColumn<Building,Number> tc_commentNumber = new TableColumn<>("number_of_comment");
        tableView.getColumns().add(tc_commentNumber);
        tc_commentNumber.setCellValueFactory(param -> {
            SimpleIntegerProperty number_of_comment = new SimpleIntegerProperty(param.getValue().getNumberOfComment());
            return number_of_comment;
        });

        //相片编码
        TableColumn<Building,String> tc_img = new TableColumn<>("img");
        tableView.getColumns().add(tc_img);
        tc_img.setCellValueFactory(param -> {
            SimpleStringProperty img = new SimpleStringProperty(param.getValue().getImg());
            return img;
        });
    }
    @FXML
    private void Comment(){

        List<Comment> commentList = commentService.findAll();
        ObservableList<Comment> commentObservableList = FXCollections.observableArrayList(commentList);
        Tab comment = new Tab("comment");
        tabPane.getTabs().add(comment);
        TableView<Comment> tableView = new TableView<>(commentObservableList);
        comment.setContent(tableView);

        //b_id
        TableColumn<Comment,Number> tc_bid = new TableColumn<>("b_id");
        tableView.getColumns().add(tc_bid);
        tc_bid.setCellValueFactory(param -> {
            SimpleIntegerProperty b_id = new SimpleIntegerProperty(param.getValue().getBid());
            return b_id;
        });

        //u_id
        TableColumn<Comment,Number> tc_uid = new TableColumn<>("u_id");
        tableView.getColumns().add(tc_uid);
        tc_uid.setCellValueFactory(param -> {
            SimpleIntegerProperty u_id = new SimpleIntegerProperty(param.getValue().getUid());
            return u_id;
        });

        //message
        TableColumn<Comment,String> tc_message = new TableColumn<>("message");
        tableView.getColumns().add(tc_message);
        tc_message.setCellValueFactory(param -> {
            SimpleStringProperty message = new SimpleStringProperty(param.getValue().getMessage());
            return message;
        });

        //time_of_commentary
        TableColumn<Comment,String> tc_time_of_commentary = new TableColumn<>("time_of_commentary");
        tableView.getColumns().add(tc_time_of_commentary);
        tc_time_of_commentary.setCellValueFactory(param -> {
            SimpleStringProperty time_of_commentary = new SimpleStringProperty(param.getValue().getTimeOfCommentary().toString());
            return time_of_commentary;
        });

        //number_of_praise
        TableColumn<Comment,Number> tc_number_of_praise = new TableColumn<>("number_of_praise");
        tableView.getColumns().add(tc_number_of_praise);
        tc_number_of_praise.setCellValueFactory(param -> {
            SimpleIntegerProperty number_of_praise = new SimpleIntegerProperty(param.getValue().getNumberOfPraise());
            return number_of_praise;
        });


    }
    @FXML
    private void User(){

        List<User> userList = userService.findAll();
        ObservableList<User> userObservableList = FXCollections.observableArrayList(userList);
        Tab user = new Tab("user");
        tabPane.getTabs().add(user);
        TableView<User> tableView = new TableView<>(userObservableList);
        user.setContent(tableView);

        //数据加载
        //open_id
        TableColumn<User,String> tc_open_id = new TableColumn<>("open_id");
        tableView.getColumns().add(tc_open_id);
        tc_open_id.setCellValueFactory(param -> {
            SimpleStringProperty open_id = new SimpleStringProperty(param.getValue().getOpenId());
            return open_id;
        });

        //session_key
        TableColumn<User,String> tc_session_key = new TableColumn<>("session_key");
        tableView.getColumns().add(tc_session_key);
        tc_session_key.setCellValueFactory(param -> {
            SimpleStringProperty session_key = new SimpleStringProperty(param.getValue().getSessionKey());
            return session_key;
        });

        //user_name
        TableColumn<User,String> tc_user_name = new TableColumn<>("user_name");
        tableView.getColumns().add(tc_user_name);
        tc_user_name.setCellValueFactory(param -> {
            SimpleStringProperty user_name = new SimpleStringProperty(param.getValue().getUserName());
            return user_name;
        });

        //user_info
        TableColumn<User,String> tc_user_info = new TableColumn<>("user_info");
        tableView.getColumns().add(tc_user_info);
        tc_user_info.setCellValueFactory(param -> {
            SimpleStringProperty user_info = new SimpleStringProperty(param.getValue().getUserInfo());
            return user_info;
        });

        //curr_log_time
        TableColumn<User,String> tc_curr_log_time = new TableColumn<>("curr_log_time");
        tableView.getColumns().add(tc_curr_log_time);
        tc_curr_log_time.setCellValueFactory(param -> {
            SimpleStringProperty curr_log_time = new SimpleStringProperty(param.getValue().getCurrLogTime().toString());
            return curr_log_time;
        });

        //reg_time
        TableColumn<User,String> tc_reg_time = new TableColumn<>("reg_time");
        tableView.getColumns().add(tc_reg_time);
        tc_reg_time.setCellValueFactory(param -> {
            SimpleStringProperty reg_time = new SimpleStringProperty(param.getValue().getRegTime().toString());
            return reg_time;
        });
    }
}
