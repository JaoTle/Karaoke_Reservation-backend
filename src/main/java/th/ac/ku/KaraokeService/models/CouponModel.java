package th.ac.ku.KaraokeService.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CouponModel {
    private String couponId;
    private String couponName;
    private String detail;
    private  int hours;
    private boolean isTotalHours;
    private double discount;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss",timezone="Asia/Bangkok")
    private Date couponExpire = new Date();

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public boolean isTotalHours() {
        return isTotalHours;
    }

    public void setTotalHours(boolean totalHours) {
        isTotalHours = totalHours;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getCouponExpire() {
        return couponExpire;
    }

    public void setCouponExpire(Date couponExpire) {
        this.couponExpire = couponExpire;
    }
}
