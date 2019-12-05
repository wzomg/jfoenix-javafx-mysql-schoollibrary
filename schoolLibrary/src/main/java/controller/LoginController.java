package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.List;
import java.util.Map;

public class LoginController {

    @FXML
    private AnchorPane anChorPane;

    @FXML
    private JFXTextField userID;

    @FXML
    private JFXPasswordField userPwd;

    @FXML
    private Label loginMsg;

    @FXML
    private ToggleGroup idGroup;

    @FXML
    private JFXRadioButton superAdmin;

    @FXML
    private JFXRadioButton libStaff;

    @FXML
    private JFXRadioButton reader;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXButton exitBtn;

/*    @FXML
    private JFXButton turnToBtn;*/

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    //转场测试
    public void turnToBtnClick() {
        System.out.println("进入读者界面~");
        Stage stage = (Stage) anChorPane.getScene().getWindow();
        stage.setTitle("读者页面");
        stage.setScene(Main.rcScene);
    }

    /**
     * 清空登录界面中表单的内容
     */
    public void clearContent() {
        //清空一些登录消息
        loginMsg.setText("登录信息失效，请重新登录！");
        //同时清空用户的密码
        userPwd.setText("");
        //idGroup.selectToggle(superAdmin);
    }

    @FXML
    public void exitBtnClick() {
        System.exit(0);
    }

    /**
     * 登录按钮
     */
    @FXML
    public void loginBtnClick() {
        //去除首尾空字符
        String Id = userID.getText().trim(), pwd = userPwd.getText();
        if(Id.isEmpty()) {
            loginMsg.setText("用户名不能为空！");
            System.out.println("用户名不能为空！");
            return;
        }
        if(pwd.isEmpty()) {
            loginMsg.setText("密码不能为空！");
            System.out.println("密码不能为空！");
            return;
        }
        //单独判断读者
        if(reader.isSelected()) {
            String strRegex = "^[\\d]{11}$";
            boolean isNumId = Id.matches(strRegex);
            if (!isNumId) {
                loginMsg.setText("用户名格式有误，请重新输入11位有效数字！");
                return;
            }
        }

        //单选按钮的判断
        int flag = 0;
        String PwdName = null;
        String sql = null;
        if(idGroup.getSelectedToggle() == superAdmin) {
            flag = 1;
            PwdName = "SPasswd";
            sql = "SELECT * FROM supermanager WHERE Sid = ?";
        }
        else if(idGroup.getSelectedToggle() == reader) {
            flag = 2;
            PwdName = "RPasswd";
            sql = "SELECT * FROM readers WHERE RId = ?";
        }else {
            flag = 3;
            PwdName = "WPasswd";
            sql = "SELECT * FROM workers WHERE WId = ?";
        }
        List<Map<String, Object>> list = template.queryForList(sql, Id);

        System.out.println(sql);
        if(list.size() == 0) {
            loginMsg.setText("用户名不存在！");
            System.out.println("查询不到数据！");
        } else {
            System.out.println(list.get(0));
            String NowPasswd = (String) list.get(0).get(PwdName);
            System.out.println("密码：" + NowPasswd);
            if(!NowPasswd.equals(pwd)) {
                loginMsg.setText("密码错误！");
                System.out.println("密码错误！");
            } else {
                //loginMsg.setText("登录成功！");
                Stage stage = (Stage) anChorPane.getScene().getWindow();
                if(flag == 1) {
                    //超管
                    stage.setTitle("超管页面");
                    stage.setScene(Main.saScene);
                    Main.sac.initData();

                } else if(flag == 2) {
                    //读者
                    Integer rStatus = (Integer) list.get(0).get("RState");
                    if(rStatus == 2) {
                        loginMsg.setText("当前账号已被注销，请联系超管~");
                        return;
                    }
                    System.out.println("准备进入读者界面！");
                    stage.setTitle("读者页面");
                    stage.setScene(Main.rcScene);
                    //设置隐藏属性的值
                    Main.rc.InitData(Id, (String) list.get(0).get("RName"), ((Integer) list.get(0).get("RState")) == 1 ? "挂失" : "正常");
                    loginMsg.setText("登录成功~");
                } else {
                    //工作人员
                    stage.setTitle("工作人员页面");
                    stage.setScene(Main.wcScene);
                    Main.wc.initData(Id, (String) list.get(0).get("WName"));
                }
            }
        }
    }
}
