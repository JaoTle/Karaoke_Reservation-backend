package th.ac.ku.KaraokeService.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import th.ac.ku.KaraokeService.models.UserCouponsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
@Service
public class UserCouponServices {



    public String collectCoupon(UserCouponsModel userCoupon){
        Firestore dbFireStore = FirestoreClient.getFirestore();
        userCoupon.setCouponStatus("Collected");
        ApiFuture<WriteResult> collectCouponResult = dbFireStore.collection("userCoupons").
                document(userCoupon.getCouponId()+"-"+userCoupon.getUsername()).set(userCoupon);
        if(collectCouponResult.isCancelled()) return "Collect Failed";
        else return "Collect Success";
    }

    public List<UserCouponsModel> getUserCoupons(String username) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        List<UserCouponsModel> userCoupons = new ArrayList<>();
        List<QueryDocumentSnapshot> userCouponDocs = dbFireStore.collection("userCoupons")
                .whereEqualTo("username",username).get().get().getDocuments();
        for (DocumentSnapshot couponDoc : userCouponDocs){
            userCoupons.add(couponDoc.toObject(UserCouponsModel.class));
        }
        return userCoupons;
    }

    public String updateCoupon(String couponId,String username){
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> updateResult = dbFireStore.collection("userCoupons")
                .document(couponId+"-"+username).update("couponStatus","Used");
        if(updateResult.isDone()) return "Update Failed";
        else return "Update Success";
    }
}
