package controller.reader;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import controller.Main;
import controller.common.BookDetailController;
import emp.EmpBookLook;
import entity.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReaderController implements Initializable {

    @FXML
    private Label accountName;

    @FXML
    private Label accountId;

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXTabPane jfxTabPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private Tab popular;

    @FXML
    private Tab lib_search;

    @FXML
    private Tab returnBook;

    @FXML
    private JFXButton returnToLoginBtn;

    @FXML
    private AnchorPane librarySearchPane;

    @FXML
    private MenuItem MI1;

    @FXML
    private MenuItem MI2;

    @FXML
    private MenuItem MI3;

    @FXML
    private JFXButton searchId;

    @FXML
    private MenuButton MBbtn;

    @FXML
    private TextField txtf;

    @FXML
    private TableView<EmpBookLook> bookLook;

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
    private TableView<EmpBookLook> needToReturn;

    @FXML
    private TableColumn<EmpBookLook, String> retBookId;

    @FXML
    private TableColumn<EmpBookLook, String> retBookName;

    @FXML
    private TableColumn<EmpBookLook, String> retBookAuthor;

    @FXML
    private TableColumn<EmpBookLook, String> retBookStart;

    @FXML
    private TableColumn<EmpBookLook, String> retBookEnd;

    @FXML
    private StackPane returnStackPane;

    @FXML
    private Label rStatus;

    private Alert alert = new Alert(Alert.AlertType.WARNING);

    public String getrStatus() {
        return rStatus.getText();
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    //热门图书列表
    private PieChart bookPopularChart;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //查询数据封装
    private ObservableList<EmpBookLook> data = FXCollections.observableArrayList();

    //查询条件封装
    private List <Object> queryList = new ArrayList<Object>();

    public String getReaderId() {
        return accountId.getText();
    }

    public TableView<EmpBookLook> getBookLook() {
        return bookLook;
    }

    //tab选项卡（图书查询）
    public void tabClick() {
        String sql = null;
        if(jfxTabPane.getSelectionModel().isSelected(0)) {
            System.out.println("点击热门推荐选项卡");
            initChartData();
        }
        else if(jfxTabPane.getSelectionModel().isSelected(1)) {
            System.out.println("点击图书查询选项卡");
            //清空搜索框中的内容
            txtf.setText("");
            //查询所有书籍
            sql = "SELECT * FROM book, tkind WHERE book.BKind = tkind.TId";
            queryList.clear();
            setData(sql, queryList);
        } else if(jfxTabPane.getSelectionModel().isSelected(2)){
            System.out.println("点击图书归还");
            initReturnData(accountId.getText());
        }
    }

    /**
     * 初始化一些数据
     */
    public void InitData(String userID, String userName, String userStatus) {
        accountId.setText(userID);
        accountName.setText(userName);
        //进入读者界面首先设置默认选择第一个tab选项卡
        txtf.setText("");
        rStatus.setText(userStatus);
        System.out.println("读者状态：" + userStatus);
        //默认选择第一个选项卡作为界面
        jfxTabPane.getSelectionModel().select(popular);
        //初始化图标数据
        initChartData();
    }

    @FXML
    public void returnToLoginBtnClick() {
        System.out.println("返回到登录界面~");
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.setTitle("登录主界面");

        stage.setScene(Main.lcScene);
        //清空数据
        Main.lc.clearContent();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reader/readerModel.fxml"));
            VBox toolbar = (VBox) loader.load();
            drawer.setSidePane(toolbar);
//            ToolbarController controller = loader.getController();
//            controller.setBookReturnCallback(this);
        }
        catch (IOException ex) {
            Logger.getLogger(ReaderController.class.getName()).log(Level.SEVERE, null, ex);
        }

        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            drawer.toggle();
        });
        drawer.setOnDrawerOpening((event) -> {
            task.setRate(task.getRate() * -1);
            task.play();
            drawer.toFront();
        });
        drawer.setOnDrawerClosed((event) -> {
            drawer.toBack();
            task.setRate(task.getRate() * -1);
            task.play();
        });
    }

    @FXML
    public void searchIdClick() {
        System.out.println("点击查询按钮！");
        //去掉首尾空格
        String text = txtf.getText().trim();
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
        List<Book> bookList = template.query(sql, new BeanPropertyRowMapper<Book>(Book.class), code.toArray());
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

    @FXML
    public void M11Click() {
        MBbtn.setText("任意词");
        System.out.println("选择任意词作为查询条件！");
    }

    @FXML
    public void M1Click() {
        MBbtn.setText("书号");
        System.out.println("选择书号作为查询条件！");
    }

    @FXML
    public void M2Click() {
        MBbtn.setText("书名");
        System.out.println("选择书名作为查询条件！");
    }

    @FXML
    public void M3Click() {
        MBbtn.setText("出版社");
        System.out.println("选择出版社作为查询条件！");
    }

    @FXML
    public void M4Click() {
        MBbtn.setText("作者");
        System.out.println("选择作者作为查询条件！");
    }

    @FXML
    public void M44Click() {
        MBbtn.setText("分类名");
        System.out.println("选择分类名作为查询条件！");
    }

    //详细界面
    @FXML
    public void bookDetailClick() {
        EmpBookLook bookDetail = bookLook.getSelectionModel().getSelectedItem();
        System.out.println(bookDetail);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/common/bookDetail.fxml"));
            Parent parent = loader.load();
            BookDetailController bc = loader.getController();
            //填充数据 一个有纯白背景和平台装饰的舞台
            Stage stage = new Stage(StageStyle.DECORATED);
            Stage faStage = (Stage) stackPane.getScene().getWindow();

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


    //点击借阅按钮，响应借阅事件
    @FXML
    public void toBorrowClick() {
        if(Main.rc.rStatus.getText().equals("挂失")) {
            alert.setHeaderText("ERROR");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("当前借阅证为挂失状态，请联系工作人员重新办理借阅证~");
            alert.show();
            return;
        }
        //先去查询是否还能借出，最多200本
        String sql = "SELECT * FROM readers WHERE RId = ?;";
        Map<String, Object> tmpMap = template.queryForMap(sql, accountId.getText());
        Integer rTotal = (Integer) tmpMap.get("RTotal");
        System.out.println(rTotal);
        //不能再书籍了
        if(rTotal == 0) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("当前借阅已达到最大可借数量：200本，不能再借阅~");
            alert.show();
            return;
        }
        EmpBookLook bookItem = bookLook.getSelectionModel().getSelectedItem();
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR");
        alert.setContentText("《" + bookItem.getBName() + "》已被借出，不能借阅~");
        if(bookItem.getBStatus().equals("已借出")) {
            alert.show();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reader/checkout.fxml"));
            Parent parent = loader.load();
            CheckOutController coc = loader.getController();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("借阅信息");
            stage.setScene(new Scene(parent, Main.sonWidth, Main.sonHeight));
            stage.setResizable(false);
            Stage stageF = (Stage) librarySearchPane.getScene().getWindow();
            //通过数据库查询来设置借阅和归还信息，设置模态
            stage.initOwner(stageF);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            // 填充数据，查询数据库
            String rId = Main.rc.getReaderId();
            System.out.println(rId);
            //初始化数据
            coc.initData(accountId.getText(), bookItem.getBId(), LocalDate.now().toString(), 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initChartData() {
        //初始化图表
        bookPopularChart = new PieChart(getPopularBookGraphStatistics());
        bookPopularChart.setTitle("前20本书籍热门借阅排行榜");
        returnStackPane.getChildren().clear();
        returnStackPane.getChildren().add(bookPopularChart);
    }

    public ObservableList<PieChart.Data> getPopularBookGraphStatistics() {
        //图标数据可能实时更新，并不会变，所以每次需要重新初始化
        ObservableList<PieChart.Data> dataChart = FXCollections.observableArrayList();
        String que = "SELECT * FROM book WHERE BCnt > 0 ORDER BY BCnt DESC;";
        List<Map<String, Object>> popularBooks = template.queryForList(que);
        System.out.println(popularBooks.size());
        for(int i = 0, len = popularBooks.size(); i < len; ++i) {
            //最多显示20本热门书籍
            if(i >= 20) break;
            String bpName = (String) popularBooks.get(i).get("BName");
            int bpCnt = (int) popularBooks.get(i).get("BCnt");
            dataChart.add(new PieChart.Data(bpName + "(" + bpCnt + ")", bpCnt));
        }
        return dataChart;
    }

    //初始化归还书籍的表格数据
    public void initReturnData(String readerId) {
        String sql = "SELECT * FROM readers, nborrowing, book WHERE readers.RId = nborrowing.NRId AND book.BId = nborrowing.NBId AND nborrowing.NState = 1 AND nborrowing.NRId = ?;";
        List<Book> bookBorrowList = template.query(sql, new BeanPropertyRowMapper<Book>(Book.class), readerId);
        data.clear();
        System.out.println("查询结果的数量有" + bookBorrowList.size());
        for(int i = 0; i < bookBorrowList.size(); ++i) {
            System.out.println(bookBorrowList.get(i));
            data.add(new EmpBookLook(bookBorrowList.get(i)));
        }
        needToReturn.setItems(data);
        retBookId.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BId"));
        retBookName.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BName"));
        retBookAuthor.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BAuthor"));
        retBookStart.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("NBTime"));
        retBookEnd.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("NBRTime"));
    }

    @FXML
    public void returnBookClick() {
        System.out.println("点击归还书籍");
        if(Main.rc.rStatus.getText().equals("挂失")) {
            alert.setHeaderText("ERROR");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("当前借阅证为挂失状态，请联系工作人员重新办理借阅证~");
            alert.show();
            return;
        }

        EmpBookLook bookLook1 = needToReturn.getSelectionModel().getSelectedItem();
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setHeaderText("INFORMATION");
        alert.setContentText("您确定归还《" + bookLook1.getBName() + "》这本书吗？");
        Optional<ButtonType> buttonType = alert.showAndWait();
        String sql = "UPDATE nborrowing SET NState = 2 WHERE NState = 1 AND NBId = ? AND NRId = ?;";
        if(buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
            System.out.println("点击确定");
            // 需要更新正在借阅表，等待工作人员的审核， 条件是书号+读者，1表示正在借阅
            int update = template.update(sql, bookLook1.getBId(), accountId.getText());
            if(update > 0) {
                System.out.println("更新成功！");
                //弹窗显示等待管理员处理
                alert.setContentText("请等待图书馆工作人员的审核！");
                alert.show();
                data.remove(bookLook1);
            } else {
                System.out.println("更新失败！");
            }
        } else {
            //不做任何处理
            System.out.println("点击×");
        }
    }

    @FXML
    public void reserveClick() {
        System.out.println("点击预约功能");
        //弹出一个界面显示
        String sql = "";
        if(Main.rc.rStatus.getText().equals("挂失")) {
            alert.setHeaderText("ERROR");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("当前借阅证为挂失状态，请联系工作人员重新办理借阅证~");
            alert.show();
            return;
        }
        EmpBookLook bookItem = bookLook.getSelectionModel().getSelectedItem();
        if(bookItem.getBStatus().equals("可借")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("《" + bookItem.getBName() + "》可以直接借阅，请右键点击借阅该书籍~");
            alert.show();
            return;
        }
        //唯一标识：读者id+书籍id
        // 先去查询一下当前读者是否有预约该书籍，有预约则不能重复预约
        String sql1 = "SELECT COUNT(*) FROM qreserve WHERE QState IN(0, 2) AND QBId = ? AND QRId = ?;";
        Long aLong1 = template.queryForObject(sql1, Long.class, bookItem.getBId(), accountId.getText());
        if(aLong1 > 0) {
            System.out.println("重复预约《" + bookItem.getBName() +"》");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("重复预约《" + bookItem.getBName() +"》");
            alert.show();
            return;
        } else {
            System.out.println("可以预约");
        }
        //有借阅也不能预约
        String sql2 = "SELECT COUNT(*) FROM nborrowing WHERE NBId = ? AND NRId = ?;";
        Long aLong2 = template.queryForObject(sql2, Long.class, bookItem.getBId(), accountId.getText());
        if(aLong2 > 0) {
            System.out.println("预约失败，《" +bookItem.getBName() +  "》正在被您借阅中");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("预约失败，《" +bookItem.getBName() +  "》正在被您借阅中");
            alert.show();
            return;
        } else {
            System.out.println("可以预约");
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/reader/reserveCheck.fxml"));
            Parent parent = loader.load();
            ReserveCheckController rcc = loader.getController();
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("预约信息");
            stage.setScene(new Scene(parent, Main.sonWidth, Main.sonHeight));
            stage.setResizable(false);
            Stage stageF = (Stage) librarySearchPane.getScene().getWindow();
            //通过数据库查询来设置借阅和归还信息，设置模态
            stage.initOwner(stageF);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            // 填充数据，查询数据库
            String rId = Main.rc.getReaderId();
            System.out.println(rId);
            //初始化数据
            rcc.initData(accountId.getText(), bookItem.getBId(), LocalDate.now().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
