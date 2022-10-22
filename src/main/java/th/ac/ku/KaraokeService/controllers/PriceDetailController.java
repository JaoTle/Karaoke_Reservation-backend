package th.ac.ku.KaraokeService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import th.ac.ku.KaraokeService.models.PriceRoomModel;
import th.ac.ku.KaraokeService.services.PriceServices;

import java.util.concurrent.ExecutionException;
@RestController
public class PriceDetailController {

    @Autowired
    private PriceServices priceServices;

    @GetMapping("/priceDetail")
    public PriceRoomModel getPrice(@RequestParam String branchId,@RequestParam String size)
            throws ExecutionException, InterruptedException {
        return priceServices.getPriceDetail(branchId,size);
    }
}
