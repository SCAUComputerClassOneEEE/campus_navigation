package com.scaudachuang.campus_navigation.fx.controller;

import com.scaudachuang.campus_navigation.fx.model.DataEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class DataTab<E> extends Tab {
    private final Class<?> EType;
    private final ObservableList<E> eObservableList;
    private final List<TableColumn<E,String>> tableColumns = new ArrayList<>();

    public DataTab(DataEnum.DataForm dataForm, List<E> eList) throws ClassNotFoundException {
        eObservableList = FXCollections.observableArrayList(eList);
        TableView<E> tableView = new TableView<>(eObservableList);
        super.setText(String.valueOf(dataForm));
        super.setContent(tableView);
        EType = Class.forName(DataEnum.toUrl(dataForm));
        initTableColumns();
        tableView.getColumns().addAll(tableColumns);
    }

    private void initTableColumns(){
        Field[] fields = EType.getDeclaredFields();
        System.out.println(EType.getName());
        for (Field field : fields){
            String columnName = field.getName();
            TableColumn<E,String> eTableColumn = new TableColumn<>(columnName);
            tableColumns.add(eTableColumn);
            String methodName = "get" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
            eTableColumn.setCellValueFactory(param -> {
                try {
                    Object data = param.getValue();
                    Method method = param.getValue().getClass().getMethod(methodName);
                    return new SimpleStringProperty(method.invoke(data).toString());
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            });
        }
    }

    /*
    对eObservableList的增删改查操作
     */
    public void addElement(){

    }
    public void deleteElement(){
        eObservableList.clear();
    }
    public void checkElement(){

    }
    public void updateElement(){

    }
}
