package com.scaudachuang.campus_navigation.fx.controller;

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
import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;


public class DataTab<E> extends Tab {
    //泛型类
    @Getter
    private final Class<?> EType;
    //TableView动态数据

    @Getter
    private final ObservableList<E> eObservableList;
    //TableView
    private final TableView<E> tableView;
    //菜单栏
    private DataContextMenu<E> contextMenu;

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
            contextMenu.add.setDisable(true);
        }

        //评论表不可修改
        if(this.getEType().getSimpleName().equals("Comment")){
            contextMenu.modify.setDisable(true);
        }

        //为TableView增加监听器
        tableView.setOnContextMenuRequested(event -> {
            //此处用于判断是否有数据项被选中，并修改对应按钮的可用性
            if(tableView.getSelectionModel().getSelectedItem()==null){
                contextMenu.delete.setDisable(true);
                contextMenu.lookup.setDisable(true);
                contextMenu.modify.setDisable(true);
            }
            else {
                contextMenu.delete.setDisable(false);
                contextMenu.lookup.setDisable(false);
                if(!this.getEType().getSimpleName().equals("Comment")){
                    contextMenu.modify.setDisable(false);
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
    private static class DataContextMenu<T> extends ContextMenu {

        //菜单按钮
        private final MenuItem add = new MenuItem("添加");
        private final MenuItem delete = new MenuItem("删除");
        private final MenuItem modify = new MenuItem("修改");
        private final MenuItem lookup = new MenuItem("查看");

        //重要参数
        private final DataTab<T> dataTab;

        //窗体
        private final GridPane gridPane = new GridPane();
        private final Stage stage = new Stage();

        public DataContextMenu(DataTab<T> dataTab){
            //参数传递
            this.dataTab = dataTab;

            //给菜单添加功能
            addFunction();

            super.getItems().addAll(add,delete,modify,lookup);
            Scene scene = new Scene(gridPane);
            gridPane.setPrefSize(450,550);
            stage.setScene(scene);
        }

        //给菜单各个按钮添加功能
        private void addFunction(){
            //增
            add.setOnAction(event -> {
                addElement();
            });

            //删
            delete.setOnAction(event -> deleteElement());

            //改
            modify.setOnAction(event -> {
                try {
                    updateElement();
                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            });

            //查
            lookup.setOnAction(event -> {
                try {
                    checkElement();
                }catch (IllegalAccessException|NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        }


        //添加新项
        public void addElement() {
            stage.setTitle("添加行");
            stage.show();

            loadArea(1,gridPane);

            Button add = new Button("添加");
            Button cancel = new Button("取消");

            add.setOnAction(event -> {
                try {
                    addOrModify(1);
                 } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                     e.printStackTrace();
                 }
                stage.close();
            });

            cancel.setOnAction(event -> {
                stage.close();
            });
            gridPane.add(add,2,dataTab.getFields().length);
            gridPane.add(cancel,3,dataTab.getFields().length);
        }

        //删除选中项
        public void deleteElement(){
            dataTab.eObservableList.remove(dataTab.tableView.getSelectionModel().getSelectedItem());
            /*
            数据库操作
             */
        }

        //修改选中项
        public void updateElement() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
            //基础窗体
            stage.setTitle("修改");
            stage.show();

            loadArea(2,gridPane);
            loadData();

            Button yes = new Button("确定");
            Button cancel = new Button("取消");

            yes.setOnAction(event -> {
                try {
                  addOrModify(2);
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                }
            });

            cancel.setOnAction(event -> {
                stage.close();
            });

            gridPane.add(yes,2,dataTab.getFields().length);
            gridPane.add(cancel,3,dataTab.getFields().length);
        }

        //查看数据项
        public void checkElement() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

            stage.setTitle("查看");
            stage.show();

            loadArea(3,gridPane);
            loadData();

            Button button = new Button("确定");
            button.setOnAction(event -> {
                stage.close();
            });
            gridPane.add(button,2,dataTab.getFields().length);
        }

        //以下为辅助函数
        /**
         * 加载视图
         * @param type 类型 1是添加，2是修改，3是查看
         * @param gridPane
         */
        private void loadArea(int type,GridPane gridPane){
            gridPane.getChildren().clear();
            //添加数据描述与数据显示框 分两种情况 add时不需要id与Date，修改与查看时都需要
            int index = 0;
            for(int i=0;i<dataTab.getFields().length;i++){
                Field field = dataTab.getFields()[i];
                if(!(type==1&&(field.getName().equals("id")||field.getType().getSimpleName().equals("Date")))){
                    //添加数据描述
                    Label label = new Label(field.getName()+":");
                    //label.setPadding(new Insets(5,5,5,5));
                    gridPane.add(label,0,index);
                    //添加数据显示框
                    Node dataArea;
                    if (field.getType().getSimpleName().equals("Date")){
                        dataArea = new DatePicker();
                        dataArea.setDisable(false);
                        ((DatePicker)dataArea).setPadding(new Insets(5,5,5,5));
                        if (type==3 | type == 2) dataArea.setDisable(true);
                    }else {
                        dataArea = new TextField();
                        ((TextField)dataArea).setEditable(true);
                        //((TextField)dataArea).setPadding(new Insets(5,5,5,5));
                        if(type==3|(type == 2 && field.getName().equals("id")))((TextField)dataArea).setEditable(false);
                    }
                    gridPane.add(dataArea,1,index);
                    index++;
                }
            }
        }

        /**
         * 加载数据
         * @throws NoSuchMethodException
         * @throws InvocationTargetException
         * @throws IllegalAccessException
         */
        private void loadData() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Class<?> dataClass = dataTab.getEType();
            Object selectedObject = dataTab.tableView.getSelectionModel().getSelectedItem();
            //添加数据域
            int index = 0;
            for (int i = 0; i < dataTab.getFields().length; i++){
                Field field = dataClass.getDeclaredFields()[i];
                String name = field.getName();
                String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method = dataClass.getMethod(methodName);

                if("Date".equals(field.getType().getSimpleName())){
                    DatePicker datePicker = (DatePicker) getNodeByRowColumnIndex(index,1,gridPane);
                    Date date = (Date)method.invoke(selectedObject);

                    String[] strNow1 = new SimpleDateFormat("yyyy-MM-dd").format(date).split("-");

                    datePicker.setValue(LocalDate.of(Integer.parseInt(strNow1[0]),
                            Integer.parseInt(strNow1[1]),
                            Integer.parseInt(strNow1[2])));
                }else {
                    TextField textField = (TextField) getNodeByRowColumnIndex(index,1,gridPane);
                    textField.setText(method.invoke(selectedObject).toString());
                }
                index++;
            }
        }

        /**
         * 添加或者修改功能时，按下确定键后的操作
         * @param type
         * @throws IllegalAccessException
         * @throws InstantiationException
         * @throws InvocationTargetException
         * @throws NoSuchMethodException
         */
        private void addOrModify(int type) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
            Class<?> dataClass = dataTab.getEType();
            Object object;
            if (type==1){
                object = dataClass.newInstance();//对应的数据对象
            }
            else {
                object = dataTab.tableView.getSelectionModel().getSelectedItem();
            }

            int index = 0;
            for (int i = 0; i < dataTab.getFields().length; i++){
                //数据域
                Field field = dataClass.getDeclaredFields()[i];

                String name = field.getName();
                String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);

                //方法名
                Method method = dataClass.getMethod(methodName,field.getType());

                System.out.print("Type:"+field.getType().getSimpleName()+" value:");
                //解决添加的项为id或日期的项
                if(type==1&&field.getName().equals("id")){
                    //获取当前库的最新id
                    //分配id
                    int data = 1;//这个需要yx弄
                    System.out.println(data);
                    method.invoke(object,data);
                }
                else if (type==1&&field.getType().getSimpleName().equals("Date")){
                    Date data = new Date();
                    System.out.println(data);
                    method.invoke(object,data);
                }
                else {
                    switch (field.getType().getSimpleName()) {
                        case "int": {
                            int data = Integer.parseInt(((TextField) getNodeByRowColumnIndex(index, 1, gridPane)).getText());
                            System.out.println(data);
                            method.invoke(object, data);
                            break;
                        }
                        case "String": {
                            String data = ((TextField) getNodeByRowColumnIndex(index++, 1, gridPane)).getText();
                            System.out.println(data);
                            method.invoke(object, data);
                            break;
                        }
                        case "BigDecimal": {
                            BigDecimal data = new BigDecimal(((TextField) getNodeByRowColumnIndex(index++, 1, gridPane)).getText());
                            System.out.println(data);
                            method.invoke(object, data);
                            break;
                        }
                    }
                }
            }

            if (type==1) dataTab.getEObservableList().add((T)object);
            //1 -- add; other modify

            /*
            数据库操作
            先数据库操作，再视图更新
             */
        }

        /**
         *根据行和列获取 ggbird中的节点
         * @param row 行
         * @param column 列
         * @param gridPane gugubird
         * @return 节点
         */
        public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
            Node result = null;
            ObservableList<Node> childrens = gridPane.getChildren();

            for (Node node : childrens) {
                if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                    result = node;
                    break;
                }
            }

            return result;
        }
    }
}
