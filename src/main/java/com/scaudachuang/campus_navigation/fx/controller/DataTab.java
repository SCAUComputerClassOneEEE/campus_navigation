package com.scaudachuang.campus_navigation.fx.controller;

import com.scaudachuang.campus_navigation.controller.AffairController;
import com.scaudachuang.campus_navigation.entity.Admin;
import com.scaudachuang.campus_navigation.fx.model.DataEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import sun.security.krb5.internal.crypto.EType;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Stack;


public class DataTab<E> extends Tab {
    //泛型类
    @Getter
    private final Class<?> EType;
    //TableView动态数据

    @Getter
    private final ObservableList<E> eObservableList;
    //TableView
    @Getter
    private final TableView<E> tableView;
    //菜单栏
    private final DataContextMenu<E> contextMenu;

    @Getter
    private Field[] fields;

    public DataTab(DataEnum.DataForm dataForm, List<E> eList) throws ClassNotFoundException {

        //泛型数据装载
        EType = Class.forName(DataEnum.toUrl(dataForm));
        //Tab标头
        super.setText(String.valueOf(dataForm));

        //初始化TableView
        eObservableList = FXCollections.observableArrayList(eList);
        tableView = new TableView<>(eObservableList);
        super.setContent(tableView);
        initTableColumns();
        //初始化ContextMenu
        contextMenu = new DataContextMenu<>(this);
        initContextMenu();
    }
    
    private void initTableColumns(){
        //获得泛型类的数据域
        fields = EType.getDeclaredFields();
        for (Field field : fields){
            //变量名
            String columnName = field.getName();
            //表属性
            TableColumn<E,String> eTableColumn = new TableColumn<>(columnName);
            tableView.getColumns().add(eTableColumn);
            //方法名，例如：id->getId
            String methodName = "get" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
            eTableColumn.setCellValueFactory(param -> {
                try {
                    //获得泛型类的实例data
                    Object data = param.getValue();
                    //获得实例的get方法
                    Method method = param.getValue().getClass().getMethod(methodName);
                    //反射执行get方法
                    return new SimpleStringProperty(method.invoke(data).toString());
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            });
        }
    }

    //初始化菜单栏
    private void initContextMenu(){

        //User表不可新增
        if(this.getEType().getSimpleName().equals("User")){
            contextMenu.getAdd().setDisable(true);
        }

        //评论表不可修改
        if(this.getEType().getSimpleName().equals("Comment")){
            contextMenu.getModify().setDisable(true);
        }

        //为TableView增加监听器
        tableView.setOnContextMenuRequested(event -> {
            //此处用于判断是否有数据项被选中，并修改对应按钮的可用性
            if(tableView.getSelectionModel().getSelectedItem()==null){
                contextMenu.getDelete().setDisable(true);
                contextMenu.getLookup().setDisable(true);
                contextMenu.getModify().setDisable(true);
            }
            else {
                contextMenu.getDelete().setDisable(false);
                contextMenu.getLookup().setDisable(false);
                if(!this.getEType().getSimpleName().equals("Comment")){
                    contextMenu.getModify().setDisable(false);
                }
            }
            contextMenu.show(tableView, event.getScreenX(), event.getScreenY());
        });

        tableView.setOnMouseClicked(event -> {
            if(event.getClickCount()==2){
                if(tableView.getSelectionModel().getSelectedItem()!=null){
                    try {
                        this.contextMenu.checkElement();
                    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            this.contextMenu.hide();
        });
    }

    //菜单内部类

}
