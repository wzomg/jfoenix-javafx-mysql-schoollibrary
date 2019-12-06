package controller.worker;

import controller.Main;
import controller.common.BookDetailController;
import emp.EmpBookLook;
import entity.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SelectBookController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button rselect;

    @FXML
    private MenuButton MBbtn;

    @FXML
    private MenuItem erveryWord;

    @FXML
    private MenuItem menuBookid;

    @FXML
    private MenuItem menuBookName;

    @FXML
    private MenuItem menuPress;

    @FXML
    private MenuItem menuAuthor;

    @FXML
    private MenuItem menuCata;

    @FXML
    private TextField inputRM;

    @FXML
    private TableColumn<EmpBookLook, String> bookIdLook;

    @FXML
    private TableColumn<EmpBookLook, String> bookNameLook;

    @FXML
    private TableColumn<EmpBookLook, String> bookAuthorLook;

    @FXML
    private TableColumn<EmpBookLook, String> bookPressLook;

    @FXML
    private TableColumn<EmpBookLook, Double> bookPriceLook;

    @FXML
    private TableColumn<EmpBookLook, String> bookSummaryLook;

    @FXML
    private TableColumn<EmpBookLook, String> bookStatusLook;

    @FXML
    private TableView<EmpBookLook> bookLook;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //查询数据封装
    private ObservableList<EmpBookLook> data = FXCollections.observableArrayList();

    //查询条件封装
    private List<Object> queryList = new ArrayList<Object>();


    @FXML
    public void bookDetialClick() {
        EmpBookLook bookDetail = bookLook.getSelectionModel().getSelectedItem();
        System.out.println(bookDetail);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/common/bookDetail.fxml"));
            Parent parent = loader.load();
            BookDetailController bc = loader.getController();
            //填充数据 一个有纯白背景和平台装饰的舞台
            Stage stage = new Stage(StageStyle.DECORATED);
            Stage faStage = (Stage) rootPane.getScene().getWindow();

            stage.setTitle("图书详情");
            stage.setScene(new Scene(parent, Main.sonWidth, Main.sonHeight));
            stage.setResizable(false);
            //注意要设置模态窗口
            stage.initModality(Modality.APPLICATION_MODAL);
            bc.initDetail(bookDetail);
            stage.initOwner(faStage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //删除书籍响应
    @FXML
    public void delBookClick() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("WARNING");
        EmpBookLook bookItem = bookLook.getSelectionModel().getSelectedItem();
        alert.setContentText("确认删除《" + bookItem.getBName() + "》这本书嘛？");
        System.out.println(bookItem);
        Optional<ButtonType> answer = alert.showAndWait();
        String sql = "DELETE FROM book WHERE BId = ?;";
        if(bookItem.getBStatus().equals("可借")) {
            if(answer.isPresent() && answer.get() == ButtonType.OK) {
                int delCnt = template.update(sql, bookItem.getBId());
                if(delCnt > 0) {
                    System.out.println("删除成功！");
                    alert.setTitle("删除成功");
                    alert.setContentText("已成功被删除。");
                    alert.show();
                    data.remove(bookItem);
                } else {
                    alert.setTitle("删除失败");
                    alert.setContentText("《" + bookItem.getBName() + "》无法被删除。");
                    alert.show();
                }
            } else {
                alert.setTitle("取消删除");
                alert.setContentText("删除操作已被取消");
                alert.show();
            }
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            System.out.println("书籍被借出，不能删除该书籍");
            alert.setContentText("《" + bookItem.getBName() + "》已被借出，不能删除它！");
            alert.show();
        }
    }

    @FXML
    public void erveryWordClick() {
        MBbtn.setText("任意词");
    }

    @FXML
    public void menuBookNameClick() {
        MBbtn.setText("书名");
    }

    @FXML
    public void menuBookidClick() {
        MBbtn.setText("书号");
    }

    @FXML
    public void menuCataClick() {
        MBbtn.setText("分类名");
    }

    @FXML
    public void menuPressClick() {
        MBbtn.setText("出版社");
    }

    public void menuAuthorClick() {
        MBbtn.setText("作者");
    }

    public void initData() {
        String sql = "SELECT * FROM book, tkind WHERE book.BKind = tkind.TId";
        queryList.clear();
        setData(sql, queryList);
    }

    @FXML
    public void searchClick() {
        System.out.println("点击查询按钮！");
        //去掉首尾空格
        String text = inputRM.getText().trim();
        System.out.println("查询框中的文本内容为" + text);
        String condition = MBbtn.getText();
        System.out.println(condition);
        String sql = null;
        queryList.clear();
        if(condition.equals("书号")) {
            sql = "SELECT * FROM book, tkind WHERE book.BKind = tkind.TId AND BId = ?;";
            queryList.add(text);
        } else if(condition.equals("书名")) {
            sql = "SELECT * FROM book, tkind WHERE book.BKind = tkind.TId AND BName like ?;";
            queryList.add("%" + text + "%");
        } else if(condition.equals("出版社")) {
            sql = "SELECT * FROM book, tkind WHERE book.BKind = tkind.TId AND BPress like ?;";
            queryList.add("%" + text + "%");
        } else if(condition.equals("作者")) {
            sql = "SELECT * FROM book, tkind WHERE book.BKind = tkind.TId AND BAuthor like ?;";
            queryList.add("%" + text + "%");
        } else if(condition.equals("分类名")) {
            sql = "SELECT * FROM book, tkind WHERE book.BKind = tkind.TId AND BKind in (SELECT TId FROM tkind WHERE TName like ?)";
            queryList.add("%" + text + "%");
        } else {
            System.out.println("这是模糊查询~");
            sql = "SELECT * FROM book, tkind WHERE book.BKind = tkind.TId AND (BId = ? OR BName like ? OR BPress like ? OR BAuthor like ? OR BKind in (SELECT TId FROM tkind WHERE TName like ?));";
            queryList.add(text);
            queryList.add("%" + text + "%");
            queryList.add("%" + text + "%");
            queryList.add("%" + text + "%");
            queryList.add("%" + text + "%");
        }
        setData(sql, queryList);
    }

    //填充表单数据
    private void setData(String sql, List<Object> code) {
        List<Book> bookList = null;
        bookList = template.query(sql, new BeanPropertyRowMapper<Book>(Book.class), code.toArray());
        data.clear();
        System.out.println("查询结果的数量有" + bookList.size());
        for(int i = 0; i < bookList.size(); ++i) {
            System.out.println(bookList.get(i));
            data.add(new EmpBookLook(bookList.get(i)));
        }
        bookLook.setItems(data);
        bookIdLook.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BId"));
        bookNameLook.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BName"));
        bookAuthorLook.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BAuthor"));
        bookPressLook.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BPress"));
        bookPriceLook.setCellValueFactory(new PropertyValueFactory<EmpBookLook, Double>("BPrice"));
        bookSummaryLook.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BSummary"));
        bookStatusLook.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BStatus"));
    }
}
