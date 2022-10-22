package th.ac.ku.KaraokeService.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import th.ac.ku.KaraokeService.form.ChangePasswordForm;
import th.ac.ku.KaraokeService.form.LoginForm;
import th.ac.ku.KaraokeService.models.BookingModel;
import th.ac.ku.KaraokeService.models.UserModel;
import th.ac.ku.KaraokeService.tools.CalculateForUser;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;
@Service
public class UserServices {

    public String createAccount(UserModel account,String role)
            throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        if(role.equalsIgnoreCase("customer")){
            account.setRole("Customer");
            account.setStationedBranchId(null);
        }
        else if(role.equalsIgnoreCase("employee")) account.setRole("Employee");
        else account.setRole("Manager");
        String password = Base64.getEncoder().encodeToString(account.getPassword().getBytes());
        account.setPassword(password);
        DocumentSnapshot checkUser = dbFireStore.collection("accounts")
                .document(account.getUsername()).get().get();
        if(checkUser.toObject(UserModel.class) == null){
            ApiFuture<WriteResult> collectionUser = dbFireStore.collection("accounts")
                    .document(account.getUsername()).set(account);
            if(collectionUser.isCancelled()) return "Create Account Failed";
        }
        else return "Duplicated Account";
        return "Create Account Success";
    }

    public UserModel getAccount(LoginForm login,String type) throws ExecutionException, InterruptedException, ParseException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference docAccount = dbFireStore.collection("accounts").document(login.getUsername());
        ApiFuture<DocumentSnapshot> docs = docAccount.get();

        DocumentSnapshot accountDoc = docs.get();
        UserModel account;


        if(accountDoc.exists()){
            account = accountDoc.toObject(UserModel.class);
            assert account != null;
            String checkPwd = new String(Base64.getDecoder().decode(account.getPassword()));
            if (login.getPassword().equals(checkPwd)){
                account.setRecentLogin(new Date(System.currentTimeMillis()));
                dbFireStore.collection("accounts").document(account.getUsername()).set(account);
                if(account.getRole().equals("Customer")){
                    BookingServices bookingServices = new BookingServices();
                    List<BookingModel> allHistory = bookingServices.getHistory(login.getUsername());
                    if(!allHistory.isEmpty()){
                        CalculateForUser calculateForUserTool = new CalculateForUser();
                        List<String> topBranch = calculateForUserTool.getTopBranch(allHistory);
                        int totalHours = calculateForUserTool.calTime(allHistory);
                        account.setTopBranches(topBranch);
                        account.setTotalHours(totalHours);
                        dbFireStore.collection("accounts").document(login.getUsername()).update("topBranches",topBranch);
                        dbFireStore.collection("accounts").document(login.getUsername()).update("totalHours",totalHours);
                    }
                }
                if(type.equals("customer") && account.getRole().equalsIgnoreCase("customer")) return account;
                else if (type.equals("service") && (account.getRole().equalsIgnoreCase("employee")
                        || account.getRole().equalsIgnoreCase("manager"))) return account;
                return null;
            }
            else return null;
        }
        else return null;
    }
    public String deleteAccount(String username) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> deleteUser = dbFireStore.collection("accounts").document(username).delete();
        return "Delete Success" + deleteUser.get().getUpdateTime();
    }

    public List<UserModel> getEmployees(String query) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        List<UserModel> employees = new ArrayList<>();
        ApiFuture<QuerySnapshot> allEmployees;
        allEmployees = dbFireStore.collection("accounts").whereEqualTo("role","Employee").get();
        List<QueryDocumentSnapshot> employeesDoc = allEmployees.get().getDocuments();
        for(DocumentSnapshot docs : employeesDoc){
            UserModel employee = docs.toObject(UserModel.class);
            assert employee != null;
            if(query.isEmpty()) employees.add(employee);
            else {
                if(employee.getName().toLowerCase().contains(query.toLowerCase())){
                    employees.add(employee);
                }
            }
        }
        return employees;
    }

    public String changePassword(ChangePasswordForm chgFrom) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference docAccount = dbFireStore.collection("accounts").document(chgFrom.getUsername());

        DocumentSnapshot accountDoc = docAccount.get().get();
        UserModel account;

        if(accountDoc.exists()){
            account = accountDoc.toObject(UserModel.class);
            assert account != null;
            String checkPwd = new String(Base64.getDecoder().decode(account.getPassword()));
            if (chgFrom.getOldPassword().equals(checkPwd)){
                String newPassword = Base64.getEncoder().encodeToString(chgFrom.getNewPassword().getBytes());
                dbFireStore.collection("accounts").document(chgFrom.getUsername()).update("password",newPassword);
                return "Change Password Success";
            }
            else return "Wrong Old Password";
        }
        return "Can't find this account.";
    }
}
