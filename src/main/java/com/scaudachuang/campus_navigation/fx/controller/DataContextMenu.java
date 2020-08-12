package com.scaudachuang.campus_navigation.fx.controller;

import com.scaudachuang.campus_navigation.controller.AffairController;
import com.scaudachuang.campus_navigation.entity.Admin;
import com.scaudachuang.campus_navigation.entity.Comment;
import com.scaudachuang.campus_navigation.entity.User;
import com.scaudachuang.campus_navigation.service.AdminService;
import com.scaudachuang.campus_navigation.service.BuildingService;
import com.scaudachuang.campus_navigation.service.CommentService;
import com.scaudachuang.campus_navigation.service.UserService;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Stack;

@Getter
@Setter
public class DataContextMenu<T> extends ContextMenu {

    @Resource
    private CommentService commentService;
    @Resource
    private BuildingService buildingService;
    @Resource
    private UserService userService;
    @Resource
    private AdminService adminService;

    //容量为3的删除桶,可以撤销操作，撤销最近的操作
    private final Stack<Object> deleteBucket = new Stack<Object>(){
        private final int BUCKET_MAX_SIZE = 3;

        @Override
        public Object push(Object item) {
            if (this.size() >= BUCKET_MAX_SIZE){
                deletedListOFTable(this.toArray(),dataTab.getEType());
                this.clear();
            }else{
                super.push(item);
            }
            return item;
        }
    };

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

    public void deletedListOFTable(Object[] listDeleted, Class<?> EType){
        switch (EType.getName()){
            case "Admin":{
                adminService.deleteAdmins(Arrays.asList((Admin[])listDeleted));
                break;
            }
            case "Comment":{
                commentService.deleteComments(Arrays.asList((Comment[])listDeleted));
                break;
            }
            case "User":{
                userService.deleteUsers(Arrays.asList((User[])listDeleted));
                break;
            }
            case "Building":{
                System.out.println("无法通过此删除");
            }
        }
    }
    public static void updatedListedOfTable(){

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
        T ob = dataTab.getTableView().getSelectionModel().getSelectedItem();
        dataTab.getEObservableList().remove(ob);
        deleteBucket.add(ob);
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
        Object selectedObject = dataTab.getTableView().getSelectionModel().getSelectedItem();
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
            object = dataTab.getTableView().getSelectionModel().getSelectedItem();
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
