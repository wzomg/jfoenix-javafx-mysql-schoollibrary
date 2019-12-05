package controller.reader;

import controller.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.io.IOException;
import java.util.Map;

public class ReaderModelController {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @FXML
    private VBox vbox;

    @FXML
    public void loadBreakMsg() throws Exception {
        System.out.println("加载违章记录界面！");
        try {
            FXMLLoader voLoader = new FXMLLoader(getClass().getResource("/fxml/reader/violation.fxml"));
            Parent bhparent = voLoader.load();
            ViolationController vc = (ViolationController) voLoader.getController();
            Stage stage = new Stage();
            stage.setTitle("违章记录");
            stage.setScene(new Scene(bhparent, Main.sonWidth, Main.sonHeight));
            stage.setResizable(false);
            Stage stageF = (Stage) vbox.getScene().getWindow();
            //通过数据库查询来设置借阅和归还信息，设置模态
            stage.initOwner(stageF);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            vc.initUncomplyData(Main.rc.getReaderId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadChangPwd() throws Exception {
        switchToPage("/fxml/reader/updateRPwd.fxml", "修改密码");
        //不用写
        System.out.println("加载修改密码界面！");
    }

    @FXML
    public void loadHistoryBorrowMsg() throws Exception {
        try {
            FXMLLoader bhLoader = new FXMLLoader(getClass().getResource("/fxml/reader/borrowHistory.fxml"));
            Parent bhparent = bhLoader.load();
            BorrowHistoryController nbc = (BorrowHistoryController) bhLoader.getController();
            Stage stage = new Stage();
            stage.setTitle("借阅历史");
            stage.setScene(new Scene(bhparent, Main.sonWidth, Main.sonHeight));
            stage.setResizable(false);
            //填充数据
            nbc.initHistoryData(Main.rc.getReaderId());
            Stage stageF = (Stage) vbox.getScene().getWindow();
            //通过数据库查询来设置借阅和归还信息，设置模态
            stage.initOwner(stageF);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("加载历史借阅界面！");
    }

    @FXML
    public void loadNowBorrowMsg() throws Exception {
        try {
            FXMLLoader nbLoader = new FXMLLoader(getClass().getResource("/fxml/reader/nowBorrow.fxml"));
            Parent nbparent = nbLoader.load();
            NowBorrowController nbc = (NowBorrowController) nbLoader.getController();
            Stage stage = new Stage();
            stage.setTitle("正在借阅表");
            stage.setScene(new Scene(nbparent, Main.sonWidth, Main.sonHeight));
            stage.setResizable(false);
            //填充数据
            nbc.initData(Main.rc.getReaderId());
            Stage stageF = (Stage) vbox.getScene().getWindow();
            //通过数据库查询来设置借阅和归还信息，设置模态
            stage.initOwner(stageF);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("加载正在借阅界面！");
    }

    @FXML
    public void loadOrderMsg() throws Exception {
        try {
            FXMLLoader rbbLoader = new FXMLLoader(getClass().getResource("/fxml/reader/reserveBook.fxml"));
            Parent rbbparent = rbbLoader.load();
            ReserveBookController rbc = (ReserveBookController) rbbLoader.getController();
            Stage stage = new Stage();
            stage.setTitle("预约信息");
            stage.setScene(new Scene(rbbparent, Main.sonWidth, Main.sonHeight));
            stage.setResizable(false);
            //填充数据
            rbc.initReserveData(Main.rc.getReaderId());
            Stage stageF = (Stage) vbox.getScene().getWindow();
            //通过数据库查询来设置借阅和归还信息，设置模态
            stage.initOwner(stageF);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("加载预约信息界面！");
    }

    @FXML
    public void loadPersonMsg() {
        try {
            FXMLLoader rLoader = new FXMLLoader(getClass().getResource("/fxml/reader/readerPmsg.fxml"));
            Parent rparent = rLoader.load();
            ReaderPmsgController rpc = (ReaderPmsgController) rLoader.getController();
            Stage stage = new Stage();
            stage.setTitle("个人信息");
            stage.setScene(new Scene(rparent, Main.width, Main.height));
            stage.setResizable(false);
            // 查询数据库
            String rId = Main.rc.getReaderId();
            String sql = "SELECT * FROM readers WHERE Rid = ?";
            Map<String, Object> map = template.queryForMap(sql, rId);
            rpc.initData(map);
            Stage stageF = (Stage) vbox.getScene().getWindow();
            //通过数据库查询来设置借阅和归还信息，设置模态
            stage.initOwner(stageF);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("加载个人信息界面！");
    }


    private void switchToPage(String loc, String title) throws Exception{
        FXMLLoader Loader = new FXMLLoader(getClass().getResource(loc));
        Parent parent = Loader.load();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(parent, Main.sonWidth, Main.sonHeight));
        stage.setResizable(false);
        Stage stageF = (Stage) vbox.getScene().getWindow();
        //通过数据库查询来设置借阅和归还信息，设置模态
        stage.initOwner(stageF);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
