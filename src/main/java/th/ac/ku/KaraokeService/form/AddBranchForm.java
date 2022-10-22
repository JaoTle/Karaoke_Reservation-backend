package th.ac.ku.KaraokeService.form;

import th.ac.ku.KaraokeService.models.BranchModel;
import th.ac.ku.KaraokeService.models.PriceRoomModel;

import java.util.List;

public class AddBranchForm {
    private BranchModel branch;
    private List<PriceRoomModel> priceDetailsList;

    public BranchModel getBranch() {
        return branch;
    }

    public void setBranch(BranchModel branch) {
        this.branch = branch;
    }

    public List<PriceRoomModel> getPriceDetailsList() {
        return priceDetailsList;
    }

    public void setPriceDetailsList(List<PriceRoomModel> priceDetailsList) {
        this.priceDetailsList = priceDetailsList;
    }
}
