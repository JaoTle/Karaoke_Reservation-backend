package th.ac.ku.KaraokeService.models;

public class PriceRoomModel {
    private String titleDetail;
    private String branchId;
    private String size;
    private double oneHourPrice;
    private double twoHoursPrice;
    private double threeHoursPrice;
    public PriceRoomModel(String titleDetail, String branchId, String size, double oneHourPrice, double twoHoursPrice, double threeHoursPrice) {
        this.titleDetail = titleDetail;
        this.branchId = branchId;
        this.size = size;
        this.oneHourPrice = oneHourPrice;
        this.twoHoursPrice = twoHoursPrice;
        this.threeHoursPrice = threeHoursPrice;
    }

    public String getTitleDetail() {
        return titleDetail;
    }

    public void setTitleDetail(String titleDetail) {
        this.titleDetail = titleDetail;
    }


    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getOneHourPrice() {
        return oneHourPrice;
    }

    public void setOneHourPrice(double oneHourPrice) {
        this.oneHourPrice = oneHourPrice;
    }

    public double getTwoHoursPrice() {
        return twoHoursPrice;
    }

    public void setTwoHoursPrice(double twoHoursPrice) {
        this.twoHoursPrice = twoHoursPrice;
    }

    public double getThreeHoursPrice() {
        return threeHoursPrice;
    }

    public void setThreeHoursPrice(double threeHoursPrice) {
        this.threeHoursPrice = threeHoursPrice;
    }
}
