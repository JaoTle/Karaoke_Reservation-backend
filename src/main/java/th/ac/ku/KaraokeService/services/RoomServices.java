package th.ac.ku.KaraokeService.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import th.ac.ku.KaraokeService.models.BranchModel;
import th.ac.ku.KaraokeService.models.RoomModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
@Service
public class RoomServices {


    private BookingServices bookingServices = new BookingServices();
    public String addRooms(BranchModel branchModel){
        int round = 1;
        Firestore dbFireStore = FirestoreClient.getFirestore();
        String branchId = branchModel.getBranchId();
        int roomS = branchModel.getAmountRoomS();
        int roomM = branchModel.getAmountRoomM();
        int roomL = branchModel.getAmountRoomL();
        List<Integer> allRoom = new ArrayList<>();
        allRoom.add(roomS);
        allRoom.add(roomM);
        allRoom.add(roomL);
        for(Integer amount : allRoom){
            for(int i = 1;i <= amount;i++){
                RoomModel room = null;
                System.out.println(round);
                if(round == 1){
                    room = new RoomModel((branchId
                            + String.format("-S%02d",i)),branchId,"S","Available");
                }
                else if(round == 2){
                    room = new RoomModel((branchId
                            + String.format("-M%02d",i)),branchId,"M","Available");
                }
                else if(round == 3) {
                    room = new RoomModel((branchId
                            + String.format("-L%02d", i)), branchId, "L", "Available");
                }
                assert room != null;
                ApiFuture<WriteResult> addRoom = dbFireStore.collection("rooms").document(room.getRoomId()).set(room);
                if(addRoom.isCancelled()) return "Failed";
            }
            round += 1;
        }
        return "Success";
    }

    public List<RoomModel> getRooms(String branchId) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        List<RoomModel> rooms = new ArrayList<>();
        List<QueryDocumentSnapshot> allRoom = dbFireStore.collection("rooms")
                .whereEqualTo("branchId",branchId).get().get().getDocuments();
        for(DocumentSnapshot roomDoc: allRoom){
            String roomId = (String) roomDoc.get("roomId");
            String branch_Id = (String) roomDoc.get("branchId");
            String roomStatus = (String) roomDoc.get("roomStatus");
            String size = (String) roomDoc.get("size");
            RoomModel room = new RoomModel(roomId,branch_Id,size,roomStatus);
            if(bookingServices.getRoomStatus(room.getRoomId())) room.setRoomStatus("Unavailable");
            else room.setRoomStatus("Available");
            rooms.add(room);
        }
        return rooms;
    }
}
