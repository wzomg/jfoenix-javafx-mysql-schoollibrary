package controller.worker;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;
import java.util.Map;

public class BorrowingCardController {

    @FXML
    private Button query;

    @FXML
    private TextField readNumInput;

    @FXML
    private JFXTextField readNum;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField identify;

    @FXML
    private JFXTextField state;

    @FXML
    private Button confirm;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //初始化数据
    public void clearData() {
        System.out.println("清空数据");
        readNum.setText("");
        name.setText("");
        identify.setText("");
        state.setText("");
    }

    @FXML
    public void cSureClick() {
        String  rid = readNum.getText();
        String  rstate = state.getText();
        String getName = name.getText();
        System.out.println(getName);
        if(getName == null || getName.equals("")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("请先查询某个读者卡号");
            alert.show();
            return;
        }
        System.out.println(rstate);
        int flag = 0;
        if(rstate.equals("挂失")){
            flag = 1;
        }else if(rstate.equals("正常")){
            flag = 0;
        } else if(rstate.equals("已注销")) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("操作禁止，您没有权限执行修改~");
            alert.show();
            return;
        }else {
            //弹窗显示输入错误信息
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("挂失状态只能填写\"正常\"或者\"挂失\"");
            alert.show();
            return;
        }
        String sql = "UPDATE readers SET RState = ? where RId = ?;";
        int cnt = template.update(sql,flag,rid);
        if(cnt > 0) {
            System.out.println("修改成功");
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("成功修改借阅证状态");
            alert.show();
        } else {
            System.out.println("输入有误！");
        }
    }

    /**
     * 点击查询按钮
     */
    @FXML
    public void rSelect() {
        System.out.println("点击查询按钮");
        //要清空两边的空格
        String readerId = readNumInput.getText().trim();
        System.out.println("输入的书号为" + readerId);
        if(readerId.equals("")) {
            return;
        }
        String sql = "SELECT * FROM readers where Rid = ?;";
        List<Map<String, Object>> readerlen = template.queryForList(sql, readerId);
        if(readerlen.size() == 0) {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("读者不存在，请重新输入！");
            alert.show();
            clearData();
            return;
        }
        readNum.setText((String) readerlen.get(0).get("RId"));
        name.setText((String) readerlen.get(0).get("RName"));
        identify.setText((String) readerlen.get(0).get("RUnit"));
        Integer rSt = (Integer) readerlen.get(0).get("RState");
        state.setText(rSt == 0 ? "正常" : (rSt == 1 ? "挂失" : "已注销"));
        if(rSt == 2) {
            //设置不可编辑
            state.setEditable(false);
        } else {
            state.setEditable(true);
        }
    }
}
