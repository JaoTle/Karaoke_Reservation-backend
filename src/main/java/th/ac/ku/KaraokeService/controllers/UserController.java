package th.ac.ku.KaraokeService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.KaraokeService.form.ChangePasswordForm;
import th.ac.ku.KaraokeService.form.LoginForm;
import th.ac.ku.KaraokeService.models.BranchModel;
import th.ac.ku.KaraokeService.models.UserModel;
import th.ac.ku.KaraokeService.services.BranchServices;
import th.ac.ku.KaraokeService.services.UserServices;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;
@RestController
public class UserController {

    @Autowired
    private UserServices userServices;
    @Autowired
    private BranchServices branchServices;

    //Sign up role(Customer,Employee)
    @PostMapping("/user/signup/{role}")
    public ResponseEntity<String> createUser(@RequestBody UserModel account, @PathVariable String role)
            throws ExecutionException, InterruptedException {
        String result = userServices.createAccount(account,role);
        if(result.equals("Duplicated Account") || result.equals("Create Account Failed")){
            return new ResponseEntity<>(result,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/user/signin")
    public ResponseEntity<UserModel> getUser(@RequestBody LoginForm form,@RequestParam String type)
            throws ExecutionException, InterruptedException, ParseException {
        UserModel user = userServices.getAccount(form,type);
        if(user == null) return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/user/employees")
    public ResponseEntity<List<UserModel>> getEmployees(@RequestParam String query)
            throws ExecutionException, InterruptedException {
        List<UserModel> employees = userServices.getEmployees(query);
        if(employees == null) return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(employees,HttpStatus.OK);
    }

    @GetMapping("/user/topBranch")
    public ResponseEntity<List<BranchModel>> getTopBranchName(@RequestBody List<String> listBranch)
            throws ExecutionException, InterruptedException {
        List<BranchModel> topBranch = branchServices.getTopBranch(listBranch);
        if(topBranch == null) return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(topBranch,HttpStatus.OK);
    }
    @DeleteMapping("/user/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String username)
            throws ExecutionException, InterruptedException {
        String result = userServices.deleteAccount(username);
        if(!result.contains("Delete Success")) return new ResponseEntity<>("Can't Delete",HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PutMapping("/user/changePwd")
    public String changePwd(@RequestBody ChangePasswordForm chgForm)
            throws ExecutionException, InterruptedException {
        return userServices.changePassword(chgForm);
    }
}
