package th.ac.ku.KaraokeService.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import th.ac.ku.KaraokeService.models.PriceRoomModel;

import java.util.List;
import java.util.concurrent.ExecutionException;
@Service
public class PriceServices {



    public String addPriceDetail(List<PriceRoomModel> listPriceRoomModel,String branchId){
        Firestore dbFireStore = FirestoreClient.getFirestore();
        for (PriceRoomModel priceRoomModel : listPriceRoomModel){
            priceRoomModel.setSize(priceRoomModel.getSize().toUpperCase());
            priceRoomModel.setTitleDetail(branchId+"-"+priceRoomModel.getSize());
            ApiFuture<WriteResult> addDetail = dbFireStore.collection("Priceperroom")
                    .document(priceRoomModel.getTitleDetail()).set(priceRoomModel);
            if(addDetail.isCancelled()) return "Failed";
        }
        return "Success";
    }

    public PriceRoomModel getPriceDetail(String branchId,String size) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentSnapshot priceDetailDoc = dbFireStore.collection("Priceperroom")
                .document(branchId+"-"+size.toUpperCase()).get().get();

        if (priceDetailDoc.exists()){
            String titleDetail = (String) priceDetailDoc.get("titleDetail");
            String branch_Id = (String) priceDetailDoc.get("branchId");
            String size_detail = (String) priceDetailDoc.get("size");
            double oneHour = Double.parseDouble(String.valueOf(priceDetailDoc.get("oneHourPrice")));
            double twoHours = Double.parseDouble(String.valueOf(priceDetailDoc.get("twoHoursPrice")));
            double threeHours = Double.parseDouble(String.valueOf(priceDetailDoc.get("threeHoursPrice")));
            return new PriceRoomModel(titleDetail,branch_Id,size_detail,oneHour,twoHours,threeHours);
        }
        else return null;
    }
}
