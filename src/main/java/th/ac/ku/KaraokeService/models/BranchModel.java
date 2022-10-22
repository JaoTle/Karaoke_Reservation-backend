package th.ac.ku.KaraokeService.models;

public class BranchModel {
    private String branchId;
    private String branchName;

    private int amountRoomS;
    private int amountRoomM;
    private int amountRoomL;
    public BranchModel(String branchId, String branchName, int amountRoomS, int amountRoomM, int amountRoomL) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.amountRoomS = amountRoomS;
        this.amountRoomM = amountRoomM;
        this.amountRoomL = amountRoomL;
    }
    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public int getAmountRoomS() {
        return amountRoomS;
    }

    public void setAmountRoomS(int amountRoomS) {
        this.amountRoomS = amountRoomS;
    }

    public int getAmountRoomM() {
        return amountRoomM;
    }

    public void setAmountRoomM(int amountRoomM) {
        this.amountRoomM = amountRoomM;
    }

    public int getAmountRoomL() {
        return amountRoomL;
    }

    public void setAmountRoomL(int amoutRoomL) {
        this.amountRoomL = amoutRoomL;
    }
}
