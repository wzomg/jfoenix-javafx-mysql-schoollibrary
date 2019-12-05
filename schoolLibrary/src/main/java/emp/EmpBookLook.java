package emp;

import entity.Book;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmpBookLook {

    private SimpleStringProperty BId = new SimpleStringProperty();
    private SimpleStringProperty BName = new SimpleStringProperty();
    private SimpleStringProperty BAuthor = new SimpleStringProperty();
    private SimpleDoubleProperty BPrice = new SimpleDoubleProperty();
    private SimpleStringProperty BPress = new SimpleStringProperty();
    private SimpleStringProperty BSummary = new SimpleStringProperty();
    private SimpleStringProperty BStatus = new SimpleStringProperty();
    private SimpleStringProperty TName = new SimpleStringProperty();
    private SimpleIntegerProperty BCnt = new SimpleIntegerProperty();
    private SimpleStringProperty NBTime = new SimpleStringProperty();
    private SimpleStringProperty NBRTime = new SimpleStringProperty();
    private SimpleStringProperty NState = new SimpleStringProperty();
    private SimpleStringProperty RId = new SimpleStringProperty();
    private SimpleStringProperty HBTime = new SimpleStringProperty();
    private SimpleStringProperty HBRTime = new SimpleStringProperty();
    private SimpleStringProperty QSDate = new SimpleStringProperty();
    private SimpleStringProperty QEDate = new SimpleStringProperty();
    private SimpleStringProperty QState = new SimpleStringProperty();
    private SimpleIntegerProperty UId = new SimpleIntegerProperty();
    private SimpleStringProperty USDate = new SimpleStringProperty();
    private SimpleStringProperty UEDate = new SimpleStringProperty();
    private SimpleStringProperty UReason = new SimpleStringProperty();


    public EmpBookLook(Book book) {
        this.BId.set(book.getBId());
        this.BName.set(book.getBName());
        this.BAuthor.set(book.getBAuthor());
        this.BPress.set(book.getBPress());
        this.BSummary.set(book.getBSummary());
        this.BPrice.set(book.getBPrice());
        this.BStatus.set(book.getBStatus() == 1 ? "可借" : "已借出");
        this.TName.set(book.getTName());
        this.BCnt.set(book.getBCnt());
        this.NBTime.set(String.valueOf(book.getNBTime()));
        this.NBRTime.set(String.valueOf(book.getNBRTime()));
        Integer ns = book.getNState();
        if(ns != null) {
            if(ns == 0) {
                this.NState.set("申请借阅");
            } else if(ns == 2) {
                this.NState.set("申请归还");
            }
        }
        this.RId.set(book.getRId());
        this.HBTime.set(String.valueOf(book.getHBTime()));
        this.HBRTime.set(String.valueOf(book.getHBRTime()));
        this.QSDate.set(String.valueOf(book.getQSDate()));
        this.QEDate.set(String.valueOf(book.getQEDate()));
        Integer qs = book.getQState();
        if(qs != null) { // 状态值为2表示可以借阅
            this.QState.set(qs == 0 ? "等待处理" : (qs == 1 ? "预约成功" : "可以借阅"));
        }
        if(book.getUId() != null) this.UId.set(book.getUId());
        this.USDate.set(book.getUSDate());
        this.UEDate.set(book.getUEDate());
        this.UReason.set(book.getUReason());
    }


    public String getUSDate() {
        return USDate.get();
    }

    public SimpleStringProperty USDateProperty() {
        return USDate;
    }

    public void setUSDate(String USDate) {
        this.USDate.set(USDate);
    }

    public String getUEDate() {
        return UEDate.get();
    }

    public SimpleStringProperty UEDateProperty() {
        return UEDate;
    }

    public void setUEDate(String UEDate) {
        this.UEDate.set(UEDate);
    }

    public String getUReason() {
        return UReason.get();
    }

    public SimpleStringProperty UReasonProperty() {
        return UReason;
    }

    public void setUReason(String UReason) {
        this.UReason.set(UReason);
    }

    public int getUId() {
        return UId.get();
    }

    public SimpleIntegerProperty UIdProperty() {
        return UId;
    }

    public void setUId(int UId) {
        this.UId.set(UId);
    }

    public String getQState() {
        return QState.get();
    }

    public SimpleStringProperty QStateProperty() {
        return QState;
    }

    public void setQState(String QState) {
        this.QState.set(QState);
    }

    public String getQSDate() {
        return QSDate.get();
    }

    public SimpleStringProperty QSDateProperty() {
        return QSDate;
    }

    public void setQSDate(String QSDate) {
        this.QSDate.set(QSDate);
    }

    public String getQEDate() {
        return QEDate.get();
    }

    public SimpleStringProperty QEDateProperty() {
        return QEDate;
    }

    public void setQEDate(String QEDate) {
        this.QEDate.set(QEDate);
    }

    public String getHBTime() {
        return HBTime.get();
    }

    public SimpleStringProperty HBTimeProperty() {
        return HBTime;
    }

    public void setHBTime(String HBTime) {
        this.HBTime.set(HBTime);
    }

    public String getHBRTime() {
        return HBRTime.get();
    }

    public SimpleStringProperty HBRTimeProperty() {
        return HBRTime;
    }

    public void setHBRTime(String HBRTime) {
        this.HBRTime.set(HBRTime);
    }

    public String getRId() {
        return RId.get();
    }

    public SimpleStringProperty RIdProperty() {
        return RId;
    }

    public void setRId(String RId) {
        this.RId.set(RId);
    }

    public String getNState() {
        return NState.get();
    }

    public SimpleStringProperty NStateProperty() {
        return NState;
    }

    public void setNState(String NState) {
        this.NState.set(NState);
    }

    public String getNBRTime() {
        return NBRTime.get();
    }

    public SimpleStringProperty NBRTimeProperty() {
        return NBRTime;
    }

    public void setNBRTime(String NBRTime) {
        this.NBRTime.set(NBRTime);
    }

    public String getNBTime() {
        return NBTime.get();
    }

    public SimpleStringProperty NBTimeProperty() {
        return NBTime;
    }

    public void setNBTime(String NBTime) {
        this.NBTime.set(NBTime);
    }

    public int getBCnt() {
        return BCnt.get();
    }

    public SimpleIntegerProperty BCntProperty() {
        return BCnt;
    }

    public void setBCnt(int BCnt) {
        this.BCnt.set(BCnt);
    }

    public String getTName() {
        return TName.get();
    }

    public SimpleStringProperty TNameProperty() {
        return TName;
    }

    public void setTName(String TName) {
        this.TName.set(TName);
    }

    public String getBStatus() {
        return BStatus.get();
    }

    public SimpleStringProperty BStatusProperty() {
        return BStatus;
    }

    public void setBStatus(String BStatus) {
        this.BStatus.set(BStatus);
    }

    public String getBId() {
        return BId.get();
    }

    public SimpleStringProperty BIdProperty() {
        return BId;
    }

    public void setBId(String BId) {
        this.BId.set(BId);
    }

    public String getBName() {
        return BName.get();
    }

    public SimpleStringProperty BNameProperty() {
        return BName;
    }

    public void setBName(String BName) {
        this.BName.set(BName);
    }

    public String getBAuthor() {
        return BAuthor.get();
    }

    public SimpleStringProperty BAuthorProperty() {
        return BAuthor;
    }

    public void setBAuthor(String BAuthor) {
        this.BAuthor.set(BAuthor);
    }

    public double getBPrice() {
        return BPrice.get();
    }

    public SimpleDoubleProperty BPriceProperty() {
        return BPrice;
    }

    public void setBPrice(double BPrice) {
        this.BPrice.set(BPrice);
    }

    public String getBPress() {
        return BPress.get();
    }

    public SimpleStringProperty BPressProperty() {
        return BPress;
    }

    public void setBPress(String BPress) {
        this.BPress.set(BPress);
    }

    public String getBSummary() {
        return BSummary.get();
    }

    public SimpleStringProperty BSummaryProperty() {
        return BSummary;
    }

    public void setBSummary(String BSummary) {
        this.BSummary.set(BSummary);
    }

    @Override
    public String toString() {
        return "EmpBookLook{" +
                "BId=" + BId +
                ", BName=" + BName +
                ", BAuthor=" + BAuthor +
                ", BPrice=" + BPrice +
                ", BPress=" + BPress +
                ", BSummary=" + BSummary +
                ", BStatus=" + BStatus +
                ", TName=" + TName +
                ", BCnt=" + BCnt +
                ", NBTime=" + NBTime +
                ", NBRTime=" + NBRTime +
                ", NState=" + NState +
                ", RId=" + RId +
                ", HBTime=" + HBTime +
                ", HBRTime=" + HBRTime +
                ", QSDate=" + QSDate +
                ", QEDate=" + QEDate +
                ", QState=" + QState +
                '}';
    }
}
