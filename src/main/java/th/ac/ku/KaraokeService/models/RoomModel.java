package th.ac.ku.KaraokeService.models;

public class RoomModel {

    private String roomId;
    private String branchId;

    private String roomStatus;

    private String size;
    public RoomModel(String roomId, String branchId, String size, String roomStatus) {
        this.roomId = roomId;
        this.branchId = branchId;
        this.roomStatus = roomStatus;
        this.size = size;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
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


}
