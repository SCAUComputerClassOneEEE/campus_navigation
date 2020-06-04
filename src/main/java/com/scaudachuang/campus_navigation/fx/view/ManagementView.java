package com.scaudachuang.campus_navigation.fx.view;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

/*
* @Author:hzj
* @Date:2020/6/5
* */

public class ManagementView {

    @Getter
    public Scene scene;
    private BorderPane borderPane = new BorderPane();
    private VBox vBox = new VBox();
    private Accordion accordion = new Accordion();
    private Button admin = new Button("admin");
    private Button users = new Button("users");
    private Button buildings = new Button("buildings");
    private Button comments = new Button("comments");
    private TabPane tabPane = new TabPane();
    private Tab tab1 = new Tab();
    private Tab tab2 = new Tab();
    private AnchorPane anchorPane= new AnchorPane();
    private TableView tableView = new TableView();
    private TableColumn C1 = new TableColumn("C1");
    private TableColumn C2 = new TableColumn("C2");
    private Button b1 = new Button("增删查改");
    private Button b2 = new Button("xxxx");


    public ManagementView(){


        scene = new Scene(borderPane);
        borderPane.setPrefSize(843,586);
        borderPane.setLeft(vBox);
        borderPane.setStyle("-fx-background-color: white");

        vBox.setPrefHeight(642);
        vBox.setPrefWidth(212);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setStyle("-fx-background-color: lightblue");
        vBox.getChildren().addAll(admin,users,buildings,comments);
        admin.setPrefHeight(30);
        admin.setPrefWidth(212);
        users.setPrefHeight(30);
        users.setPrefWidth(212);
        buildings.setPrefHeight(30);
        buildings.setPrefWidth(212);
        comments.setPrefHeight(30);
        comments.setPrefWidth(212);


        borderPane.setCenter(tabPane);
        tabPane.setPrefHeight(200);
        tabPane.setPrefWidth(200);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabPane.getTabs().addAll(tab1,tab2);
        tab1.setText("Untitled1");
        tab2.setText("Untitled2");
        accordion.setPrefHeight(200);
        accordion.setPrefWidth(200);
        tab2.setContent(anchorPane);

        anchorPane.getChildren().addAll(tableView,b1,b2);
        tableView.setLayoutY(23);
        tableView.setLayoutX(26);
        tableView.setPrefHeight(356);
        tableView.setPrefWidth(548);
        tableView.getColumns().addAll(C1,C2);

        b1.setLayoutX(423);
        b1.setLayoutY(508);
        b2.setLayoutX(521);
        b2.setLayoutY(508);





    }


}
