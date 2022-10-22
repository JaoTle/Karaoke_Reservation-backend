package th.ac.ku.KaraokeService.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import th.ac.ku.KaraokeService.form.AddBranchForm;
import th.ac.ku.KaraokeService.form.BranchName;
import th.ac.ku.KaraokeService.models.BranchModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
@Service
public class BranchServices {

    @Autowired
    private PriceServices priceServices;
    @Autowired
    private RoomServices roomServices;
    public String addBranch(AddBranchForm addBranchForm) throws ExecutionException, InterruptedException {
        String branchName = addBranchForm.getBranch().getBranchName();
        int amountRoomS = addBranchForm.getBranch().getAmountRoomS();
        int amountRoomM = addBranchForm.getBranch().getAmountRoomM();
        int amountRoomL = addBranchForm.getBranch().getAmountRoomL();
        BranchModel branch = new BranchModel("branchId",branchName,amountRoomS,amountRoomM,amountRoomL);
        Firestore dbFireStore = FirestoreClient.getFirestore();
        QuerySnapshot branchDoc =  dbFireStore.collection("branches")
                .whereEqualTo("branchName",branch.getBranchName()).get().get();
        if(!branchDoc.isEmpty()){
            return "Branch Duplicate.";
        }
        int numOfBranch = getAllBranches("").size() + 1;
        branch.setBranchId(String.format("BCH%03d",numOfBranch));
        ApiFuture<WriteResult> collectionBranch = dbFireStore.collection("branches")
                .document(branch.getBranchId()).set(branch);
        String addPriceDetails = priceServices.addPriceDetail(addBranchForm.getPriceDetailsList(),
                String.format("BCH%03d",numOfBranch));
        String addRooms = roomServices.addRooms(branch);
        if(!addPriceDetails.equalsIgnoreCase("Success")
           || !addRooms.equalsIgnoreCase("Success")) return "Add Branch Failed";
        return "Add Branch Success";
    }

    public List<BranchModel> getAllBranches(String query) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        List<BranchModel> branches = new ArrayList<>();
        List<QueryDocumentSnapshot> branchesDoc = dbFireStore.collection("branches").get().get().getDocuments();
        for(DocumentSnapshot branchDoc : branchesDoc){
            String branchId = (String) branchDoc.get("branchId");
            String branchName = (String) branchDoc.get("branchName");
            int amountRoomS = Integer.parseInt(String.valueOf(branchDoc.get("amountRoomS")));
            int amountRoomM = Integer.parseInt(String.valueOf(branchDoc.get("amountRoomM")));
            int amountRoomL = Integer.parseInt(String.valueOf(branchDoc.get("amountRoomL")));
            BranchModel branch = new BranchModel(branchId,branchName,amountRoomS,amountRoomM,amountRoomL);
            if(query.isEmpty()) branches.add(branch);
            else {
                assert branchName != null;
                if(branchName.toLowerCase().contains(query.toLowerCase())) branches.add(branch);
            }

        }
        return branches;
    }

    public List<BranchModel> getTopBranch(List<String> topBranch) throws ExecutionException, InterruptedException {
        List<BranchModel> topBranchList = new ArrayList<>();
        Firestore dbFireStore = FirestoreClient.getFirestore();
        for(String branch : topBranch){
            DocumentSnapshot docBranch = dbFireStore.collection("branches").document(branch).get().get();
            if(docBranch.exists()){
                String branchId = (String) docBranch.get("branchId");
                String branchName = (String) docBranch.get("branchName");
                int amountRoomS = Integer.parseInt(String.valueOf(docBranch.get("amountRoomS")));
                int amountRoomM = Integer.parseInt(String.valueOf(docBranch.get("amountRoomM")));
                int amountRoomL = Integer.parseInt(String.valueOf(docBranch.get("amountRoomL")));
                BranchModel branchAdd = new BranchModel(branchId,branchName,amountRoomS,amountRoomM,amountRoomL);
                topBranchList.add(branchAdd);
            }
        }
        return topBranchList;
    }

    public List<BranchName> getNameBranch() throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        List<QueryDocumentSnapshot> branchDocs = dbFireStore.collection("branches").get().get().getDocuments();
        List<BranchName> keyName = new ArrayList<>();
        for(DocumentSnapshot doc : branchDocs){
            String branchId = (String) doc.get("branchId");
            String branchName = (String) doc.get("branchName");
            BranchName nameKey = new BranchName(branchId,branchName);
            keyName.add(nameKey);
        }
        return keyName;
    }
}
