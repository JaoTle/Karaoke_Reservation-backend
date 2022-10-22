package th.ac.ku.KaraokeService.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BookingModel {
    private String bookingID;
    private String customerName;
    private String branchId;
    private String roomId;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss",timezone="Asia/Bangkok")
    private Date startTime = new Date();
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss",timezone="Asia/Bangkok")
    private Date endTime = new Date();
    private double price;
    private String status;

    private String type;
    private String responseEmp;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseEmp() {
        return responseEmp;
    }

    public void setResponseEmp(String responseEmp) {
        this.responseEmp = responseEmp;
    }
}
