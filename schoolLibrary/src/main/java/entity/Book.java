package entity;

import java.time.LocalDate;

public class Book {
    private String BId;
    private String BName;
    private String BAuthor;
    private String BKind;
    private LocalDate BDate;
    private Double BPrice;
    private String BPress;
    private String BSummary;
    private Integer BCnt;
    private Integer BStatus;
    private String TName;
    private LocalDate NBTime;
    private LocalDate NBRTime;
    private Integer NState;
    private String RId;
    private LocalDate HBTime;
    private LocalDate HBRTime;
    private LocalDate QSDate;
    private LocalDate QEDate;
    private Integer QState;
    private Integer UId;
    private String USDate;
    private String UEDate;
    private String UReason;

    public String getUSDate() {
        return USDate;
    }

    public void setUSDate(String USDate) {
        this.USDate = USDate;
    }

    public String getUEDate() {
        return UEDate;
    }

    public void setUEDate(String UEDate) {
        this.UEDate = UEDate;
    }

    public String getUReason() {
        return UReason;
    }

    public void setUReason(String UReason) {
        this.UReason = UReason;
    }

    public Integer getUId() {
        return UId;
    }

    public void setUId(Integer UId) {
        this.UId = UId;
    }

    public Integer getQState() {
        return QState;
    }

    public void setQState(Integer QState) {
        this.QState = QState;
    }

    public LocalDate getQSDate() {
        return QSDate;
    }

    public void setQSDate(LocalDate QSDate) {
        this.QSDate = QSDate;
    }

    public LocalDate getQEDate() {
        return QEDate;
    }

    public void setQEDate(LocalDate QEDate) {
        this.QEDate = QEDate;
    }

    public LocalDate getHBTime() {
        return HBTime;
    }

    public void setHBTime(LocalDate HBTime) {
        this.HBTime = HBTime;
    }

    public LocalDate getHBRTime() {
        return HBRTime;
    }

    public void setHBRTime(LocalDate HBRTime) {
        this.HBRTime = HBRTime;
    }

    public String getRId() {
        return RId;
    }

    public void setRId(String RId) {
        this.RId = RId;
    }

    public Integer getNState() {
        return NState;
    }

    public void setNState(Integer NState) {
        this.NState = NState;
    }

    public LocalDate getNBTime() {
        return NBTime;
    }

    public void setNBTime(LocalDate NBTime) {
        this.NBTime = NBTime;
    }

    public LocalDate getNBRTime() {
        return NBRTime;
    }

    public void setNBRTime(LocalDate NBRTime) {
        this.NBRTime = NBRTime;
    }

    public String getTName() {
        return TName;
    }

    public void setTName(String TName) {
        this.TName = TName;
    }

    public Integer getBStatus() {
        return BStatus;
    }

    public void setBStatus(Integer BStatus) {
        this.BStatus = BStatus;
    }

    public String getBId() {
        return BId;
    }

    public void setBId(String BId) {
        this.BId = BId;
    }

    public String getBName() {
        return BName;
    }

    public void setBName(String BName) {
        this.BName = BName;
    }

    public String getBAuthor() {
        return BAuthor;
    }

    public void setBAuthor(String BAuthor) {
        this.BAuthor = BAuthor;
    }

    public String getBKind() {
        return BKind;
    }

    public void setBKind(String BKind) {
        this.BKind = BKind;
    }

    public LocalDate getBDate() {
        return BDate;
    }

    public void setBDate(LocalDate BDate) {
        this.BDate = BDate;
    }

    public Double getBPrice() {
        return BPrice;
    }

    public void setBPrice(Double BPrice) {
        this.BPrice = BPrice;
    }

    public String getBPress() {
        return BPress;
    }

    public void setBPress(String BPress) {
        this.BPress = BPress;
    }

    public String getBSummary() {
        return BSummary;
    }

    public void setBSummary(String BSummary) {
        this.BSummary = BSummary;
    }

    public Integer getBCnt() {
        return BCnt;
    }

    public void setBCnt(Integer BCnt) {
        this.BCnt = BCnt;
    }

    @Override
    public String toString() {
        return "Book{" +
                "BId='" + BId + '\'' +
                ", BName='" + BName + '\'' +
                ", BAuthor='" + BAuthor + '\'' +
                ", BKind='" + BKind + '\'' +
                ", BDate=" + BDate +
                ", BPrice=" + BPrice +
                ", BPress='" + BPress + '\'' +
                ", BSummary='" + BSummary + '\'' +
                ", BCnt=" + BCnt +
                ", BStatus=" + BStatus +
                ", TName='" + TName + '\'' +
                ", NBTime=" + NBTime +
                ", NBRTime=" + NBRTime +
                ", NState=" + NState +
                ", RId='" + RId + '\'' +
                ", HBTime=" + HBTime +
                ", HBRTime=" + HBRTime +
                ", QSDate=" + QSDate +
                ", QEDate=" + QEDate +
                ", QState=" + QState +
                ", UId=" + UId +
                ", USDate='" + USDate + '\'' +
                ", UEDate='" + UEDate + '\'' +
                ", UReason='" + UReason + '\'' +
                '}';
    }
}
