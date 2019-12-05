package controller.worker;

import controller.Main;
import emp.EmpBookLook;
import entity.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReviewReturnController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<EmpBookLook> checkTable;

    @FXML
    private TableColumn<EmpBookLook, String> rRid;

    @FXML
    private TableColumn<EmpBookLook, String> rBid;

    @FXML
    private TableColumn<EmpBookLook, String> rBtime;

    @FXML
    private TableColumn<EmpBookLook, String> rRtime;

    @FXML
    private TableColumn<EmpBookLook, String> rState;

    @FXML
    private MenuItem verify;

    @FXML
    private MenuItem breakContract;

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    private ObservableList<EmpBookLook> data = FXCollections.observableArrayList();

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    //展示数据
    public void initData() {
        data.clear();
        String sql = "SELECT * FROM nborrowing, readers, book WHERE book.BId = nborrowing.NBId AND readers.RId = nborrowing.NRId AND (nborrowing.NState = 0 OR nborrowing.NState = 2)";
        List<Book> checkBList = template.query(sql, new BeanPropertyRowMapper<>(Book.class));
        System.out.println("查询结果的数量有" + checkBList.size());
        for(int i = 0; i < checkBList.size(); ++i) {
            System.out.println(checkBList.get(i));
            data.add(new EmpBookLook(checkBList.get(i)));
        }
        checkTable.setItems(data);
        rBid.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("BId"));
        rRid.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("RId"));
        rBtime.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("NBTime"));
        rRtime.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("NBRTime"));
        rState.setCellValueFactory(new PropertyValueFactory<EmpBookLook, String>("NState"));
    }

    //添加违约
    @FXML
    public void breakContractClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/worker/checkReason.fxml"));
            Parent parent = loader.load();
            CheckReasonController crc = loader.getController();
            //填充数据 一个有纯白背景和平台装饰的舞台
            Stage stage = new Stage(StageStyle.DECORATED);
            Stage faStage = (Stage) rootPane.getScene().getWindow();
            EmpBookLook bookCheck = checkTable.getSelectionModel().getSelectedItem();
            crc.initReasonData(bookCheck.getRId(), bookCheck.getBId(), bookCheck.getNBTime(), bookCheck.getNBRTime());
            stage.setTitle("违章处理");
            stage.setScene(new Scene(parent, Main.sonWidth, Main.sonHeight));
            stage.setResizable(false);
            //注意要设置模态窗口
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(faStage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //验证通过
    @FXML
    public void verifyClick() {
        System.out.println("点击审核验证");
        EmpBookLook bookItem = checkTable.getSelectionModel().getSelectedItem();
        String startTime = bookItem.getNBTime();
        String endTime = bookItem.getNBRTime();
        LocalDate sTime = LocalDate.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate eTime = LocalDate.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(bookItem);
        String sql = null, sql1 = null, sql2 = null, sql3 = null, sql4 = null ;
        int update = 0, update1 = 0, update2 = 0, update3 = 0, update4 = 0;
        alert.setHeaderText("审核状态");
        if(bookItem.getNState().equals("申请借阅")) {
            //申请借阅只需将NState改为1
            sql = "UPDATE nborrowing SET NState = 1 WHERE NState = 0 AND NBId = ? AND NRId = ? AND NBTime = ? AND NBRTime = ?;";
            update = template.update(sql, bookItem.getBId(), bookItem.getRId(), sTime, eTime);
            if(update > 0) {
                System.out.println("审核通过");
                alert.setContentText("审核通过");
                //刷新表格
                data.remove(bookItem);
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("审核失败");
                System.out.println("审核失败");
            }
        } else {
            // 审核归还，也就是把该记录添加到历史借阅表中，然后将该记录从正在借阅表中删除，接着更改该书籍为可借状态，最后要更新该读者在借册数-1，最多可借册数+1！！！(四部曲)
            sql1 = "INSERT INTO hreturn(HBId, HRId, HBTime, HBRTime) VALUES(?, ?, ?, ?);";
            update1 = template.update(sql1, bookItem.getBId(), bookItem.getRId(), sTime, eTime);
            sql2 = "DELETE FROM nborrowing WHERE NState = 2 AND NBId = ? AND NRId = ? AND NBTime = ? AND NBRTime = ?;";
            update2 = template.update(sql2, bookItem.getBId(), bookItem.getRId(), sTime, eTime);
            sql3 = "UPDATE book SET BStatus = 1 WHERE BId = ?;";
            update3 = template.update(sql3, bookItem.getBId());
            sql4 = "UPDATE readers SET Rnum = Rnum - 1, RTotal = RTotal + 1 WHERE RId = ?;";
            update4 = template.update(sql4, bookItem.getRId());
            System.out.println("当前选中的读者id为：" + bookItem.getRId());
            //以下是控制台输出提示信息
            if(update1 > 0) {
                System.out.println("成功添加到历史借阅表");
            } else {
                System.out.println("添加失败");
            }
            if(update2 > 0) {
                System.out.println("删除成功~");
            } else {
                System.out.println("删除失败~");
            }
            if(update3 > 0) {
                System.out.println("更改图书状态成功！");
            } else {
                System.out.println("更改图书状态失败！");
            }
            if(update4 > 0) {
                System.out.println("更新读者在借册数 - 1 和最多可再借 + 1");
            } else {
                System.out.println("更新在借册数失败~~~~~~~~~~~~~");
            }
            if(update1 > 0 && update2 > 0 && update3 > 0 && update4 > 0) {
                System.out.println("审核通过");
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("审核通过");
                //记得刷新表单
                data.remove(bookItem);
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("审核失败");
            }
        }
        alert.show();
    }
}
