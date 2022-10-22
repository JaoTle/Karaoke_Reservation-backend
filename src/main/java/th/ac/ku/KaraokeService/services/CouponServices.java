package th.ac.ku.KaraokeService.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import th.ac.ku.KaraokeService.models.CouponModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
@Service
public class CouponServices {



    public String addCoupon(CouponModel coupon) {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> addCouponResult = dbFireStore.collection("coupons").document(coupon.getCouponId()).set(coupon);
        if(addCouponResult.isCancelled()) return "Failed to add coupon";
        return "Add Coupon Success";
    }

    public List<CouponModel> getAllCoupon() throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        List<CouponModel> allCoupon =  new ArrayList<>();
        List<QueryDocumentSnapshot> allCouponDoc = dbFireStore.collection("coupons").get().get().getDocuments();
        for(DocumentSnapshot couponDoc : allCouponDoc){
            allCoupon.add(couponDoc.toObject(CouponModel.class));
        }
        return allCoupon;
    }

    public String deleteCoupon() throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        Date dateTimeNow = new Date();
        List<QueryDocumentSnapshot> expireCouponDoc = dbFireStore.collection("coupons")
                .whereLessThan("couponExpire",dateTimeNow).get().get().getDocuments();
        for(DocumentSnapshot expireDoc : expireCouponDoc){
            CouponModel expireCoupon = expireDoc.toObject(CouponModel.class);
            assert expireCoupon != null;
            dbFireStore.collection("coupons").document(expireCoupon.getCouponId()).delete();
        }
        return "Clear coupons";
    }
    public CouponModel getCouponById(String couponId) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentSnapshot couponDoc = dbFireStore.collection("coupons").document(couponId).get().get();
        CouponModel coupon = couponDoc.toObject(CouponModel.class);
        return coupon;
    }
}
