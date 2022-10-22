package th.ac.ku.KaraokeService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.KaraokeService.form.AddBranchForm;
import th.ac.ku.KaraokeService.form.BranchName;
import th.ac.ku.KaraokeService.models.BranchModel;
import th.ac.ku.KaraokeService.services.BranchServices;
import th.ac.ku.KaraokeService.services.PriceServices;
import th.ac.ku.KaraokeService.services.RoomServices;

import java.util.List;
import java.util.concurrent.ExecutionException;
@RestController
public class BranchController {

    @Autowired
    private BranchServices branchServices;

    @PostMapping("/branch")

    public String addBranch(@RequestBody AddBranchForm addBranchForm)
            throws ExecutionException, InterruptedException {
        return branchServices.addBranch(addBranchForm);
    }

    @GetMapping("/branch")

    public List<BranchModel> getBranches(
            @RequestParam(name = "query",required = false,defaultValue = "")String query)
            throws ExecutionException, InterruptedException {
        return  branchServices.getAllBranches(query);
    }

    @GetMapping("/branch/key")

    public List<BranchName> getBranchName() throws ExecutionException, InterruptedException {
        return branchServices.getNameBranch();
    }
}
