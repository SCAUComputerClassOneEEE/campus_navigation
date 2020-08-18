package com.scaudachuang.campus_navigation.fx.controller;

import com.scaudachuang.campus_navigation.fx.model.DataEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class DataTab<E> extends Tab {
    //泛型类
    @Getter
    @Setter
    private Class<?> EType;
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

    public DataTab(DataEnum.DataForm dataForm,List<E> eList) throws ClassNotFoundException {
        //初始化TableView
        this.eObservableList = FXCollections.observableArrayList(eList);
        setEType(Class.forName(DataEnum.toUrl(dataForm)));
        super.setText(String.valueOf(dataForm));
        tableView = new TableView<>(eObservableList);
        super.setContent(tableView);
        initTableColumns();
        //初始化ContextMenu
        contextMenu = new DataContextMenu<>();
        contextMenu.init(this);
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
}
