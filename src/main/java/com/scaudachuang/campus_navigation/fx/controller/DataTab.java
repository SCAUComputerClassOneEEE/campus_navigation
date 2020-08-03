package com.scaudachuang.campus_navigation.fx.controller;

import com.scaudachuang.campus_navigation.entity.Admin;
import com.scaudachuang.campus_navigation.fx.model.DataEnum;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    private static class DataContextMenu<T> extends ContextMenu {
        private MenuItem add = new MenuItem("添加");
        private MenuItem lookup = new MenuItem("查看");
        private MenuItem modify = new MenuItem("修改");
        private MenuItem delete = new MenuItem("删除");
        private DataTab<T> dataTab;

        public DataContextMenu(DataTab<T> dataTab){
            this.dataTab = dataTab;
            delete.setOnAction(event -> deleteElement());
            add.setOnAction(event -> {
                try {
                    addElement();
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            });
            lookup.setOnAction(event -> {
                try {
                    checkElement();
                }catch (IllegalAccessException|NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
            modify.setOnAction(event -> {
                try {
                    updateElement();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            });
            super.getItems().addAll(add,delete,lookup,modify);
        }
        /*
        对eObservableList的增删改查操作
         */
        //添加新项
        public void addElement() throws IllegalAccessException, InstantiationException {
            //基础窗体
            GridPane gridPane = new GridPane();
            Stage stage = new Stage();
            Scene scene = new Scene(gridPane);
            stage.setScene(scene);
            stage.setTitle("添加行");
            stage.show();

            //添加数据标签与数据输入域
            for (int i = 0; i < dataTab.getFields().length; i++){
                Field field = dataTab.getFields()[i];
                Label label = new Label(field.getName()+":");
                gridPane.add(label,0,i);
                Node input = new TextField();
                if(field.getType().getSimpleName().equals("Date"))
                    input = new DatePicker();
                gridPane.add(input,1,i);
            }
            Button add = new Button("添加");
            Button cancel = new Button("取消");

            add.setOnAction(event -> {
                try {
                    Class<?> dataClass = dataTab.getEType();
                    Object o = dataClass.newInstance();//对应的数据对象
                    for (int i = 0; i < dataTab.getFields().length; i++){
                        //数据域
                        Field field = dataClass.getDeclaredFields()[i];
                        String name = field.getName();
                        String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);

                        //方法名
                        Method method = dataClass.getMethod(methodName,field.getType());

                        System.out.print(field.getType().getSimpleName()+":");
                        if ("int".equals(field.getType().getSimpleName())){
                            int data = Integer.parseInt(((TextField)getNodeByRowColumnIndex(i,1,gridPane)).getText());
                            System.out.println(data);
                            method.invoke(o,data);
                        }
                        else if("String".equals(field.getType().getSimpleName())){
                            String data = ((TextField)getNodeByRowColumnIndex(i,1,gridPane)).getText();
                            System.out.println(data);
                            method.invoke(o,data);
                        }
                        else {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            //((DatePicker)getNodeByRowColumnIndex(i,1,gridPane)).getValue()
                            Date data = new Date();
                            System.out.println(data);

                            method.invoke(o,data);
                        }
                    }
                    //两个数据更新，view和database
                    dataTab.getEObservableList().add((T)o);


                 } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                     e.printStackTrace();
                 }
            });

            cancel.setOnAction(event -> {
                stage.close();
            });
            gridPane.add(add,2,8);
            gridPane.add(cancel,3,8);
        }

        //删除选中项
        public void deleteElement(){
            dataTab.eObservableList.remove(dataTab.tableView.getSelectionModel().getSelectedItem());
        }

        //查看数据项
        public void checkElement() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            GridPane gridPane = new GridPane();
            gridPane.setPrefSize(400,400);
            Stage stage = new Stage();
            Scene scene = new Scene(gridPane);
            stage.setScene(scene);
            stage.setTitle("查看");
            stage.show();
            //添加属性框
            for (int i = 0; i < dataTab.getFields().length; i++){
                Field field = dataTab.getFields()[i];
                Label label = new Label(field.getName()+":");
                gridPane.add(label,0,i);
            }
            Class<?> dataClass = dataTab.getEType();
            Object o = dataTab.tableView.getSelectionModel().getSelectedItem();
            //添加数据域
            for (int i = 0; i < dataTab.getFields().length; i++){
                Field field = dataClass.getDeclaredFields()[i];
                String name = field.getName();
                String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method = dataClass.getMethod(methodName);

                if("Date".equals(field.getType().getSimpleName())){
                    DatePicker datePicker = new DatePicker();
                    datePicker.setDisable(true);
                    Date date = (Date)method.invoke(o);

                    String[] strNow1 = new SimpleDateFormat("yyyy-MM-dd").format(date).split("-");

                    datePicker.setValue(LocalDate.of(Integer.parseInt(strNow1[0]),
                            Integer.parseInt(strNow1[1]),
                            Integer.parseInt(strNow1[2])));
                    gridPane.add(datePicker,1,i);
                }else {
                    TextField textField = new TextField();
                    textField.setText(method.invoke(o).toString());
                    textField.setEditable(false);
                    gridPane.add(textField,1,i);
                }

            }

            Button button = new Button("确定");
            button.setOnAction(event -> {
                stage.close();
            });
            gridPane.add(button,2,8);
        }

        //修改选中项
        public void updateElement() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
            //基础窗体
            GridPane gridPane = new GridPane();
            Stage stage = new Stage();
            Scene scene = new Scene(gridPane);
            stage.setScene(scene);
            stage.setTitle("修改");
            stage.show();

            for (int i = 0; i < dataTab.getFields().length; i++){
                Field field = dataTab.getFields()[i];
                Label label = new Label(field.getName()+":");
                gridPane.add(label,0,i);
            }
            Class<?> dataClass = dataTab.getEType();
            Object o = dataTab.tableView.getSelectionModel().getSelectedItem();
            //添加数据域
            for (int i = 0; i < dataTab.getFields().length; i++){
                Field field = dataClass.getDeclaredFields()[i];
                String name = field.getName();
                String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method = dataClass.getMethod(methodName);

                if("Date".equals(field.getType().getSimpleName())){
                    DatePicker datePicker = new DatePicker();
                    Date date = (Date) method.invoke(o);
                    String[] strNow1 = new SimpleDateFormat("yyyy-MM-dd").format(date).split("-");

                    datePicker.setValue(LocalDate.of(Integer.parseInt(strNow1[0]),
                            Integer.parseInt(strNow1[1]),
                            Integer.parseInt(strNow1[2])));
                    gridPane.add(datePicker,1,i);
                }else {
                    TextField textField = new TextField();
                    textField.setText(method.invoke(o).toString());
                    gridPane.add(textField,1,i);
                }

            }

            Button yes = new Button("确定");
            Button cancel = new Button("取消");

            yes.setOnAction(event -> {
                try {
                    for (int i = 0; i < dataTab.getFields().length; i++){
                        //数据域
                        Field field = dataClass.getDeclaredFields()[i];
                        String name = field.getName();
                        String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);

                        //方法名
                        Method method = dataClass.getMethod(methodName,field.getType());


                        System.out.print(field.getType().getSimpleName()+":");
                        if ("int".equals(field.getType().getSimpleName())){
                            int data = Integer.parseInt(((TextField)getNodeByRowColumnIndex(i,1,gridPane)).getText());
                            System.out.println(data);
                            method.invoke(o,data);
                        }
                        else if("String".equals(field.getType().getSimpleName())){
                            String data = ((TextField)getNodeByRowColumnIndex(i,1,gridPane)).getText();
                            System.out.println(data);
                            method.invoke(o,data);
                        }
                        else {
                            Date data = new Date(((DatePicker)getNodeByRowColumnIndex(i,1,gridPane)).getValue().toEpochDay());
                            System.out.println(data);
                            method.invoke(o,data);
                        }
                        //两个数据更新，view和database
                    }
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });

            cancel.setOnAction(event -> {
                stage.close();
            });

            gridPane.add(yes,2,8);
            gridPane.add(cancel,3,8);
        }

        /**
         *
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
