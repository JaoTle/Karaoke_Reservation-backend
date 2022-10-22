package th.ac.ku.KaraokeService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.KaraokeService.models.CouponModel;
import th.ac.ku.KaraokeService.models.UserCouponsModel;
import th.ac.ku.KaraokeService.services.CouponServices;
import th.ac.ku.KaraokeService.services.UserCouponServices;

import java.util.List;
import java.util.concurrent.ExecutionException;
@RestController
public class CouponController {

    @Autowired
    private CouponServices couponServices;
    @Autowired
    private UserCouponServices userCouponServices;

    @PostMapping("/coupons")
    public String addCoupon(@RequestBody CouponModel coupon)
            throws ExecutionException, InterruptedException {
        return couponServices.addCoupon(coupon);
    }

    @GetMapping("/coupons")
    public List<CouponModel> getCoupons() throws ExecutionException, InterruptedException {
        return couponServices.getAllCoupon();
    }

    @DeleteMapping("/coupons/expire")
    public String expireCoupon() throws ExecutionException, InterruptedException {
        return couponServices.deleteCoupon();
    }

    @PostMapping("/user/coupons")
    public String collectCoupon(@RequestBody UserCouponsModel collectCoupon){
        return userCouponServices.collectCoupon(collectCoupon);
    }

    @GetMapping("/user/coupons")
    public List<UserCouponsModel> getUserCoupon(
            @RequestParam String username)
            throws ExecutionException, InterruptedException {
        return userCouponServices.getUserCoupons(username);
    }
    @GetMapping("/coupon")
    public CouponModel getCouponById(@RequestParam String couponId)
            throws ExecutionException, InterruptedException {
        return couponServices.getCouponById(couponId);
    }
    @PutMapping("/user/coupons")
    public String updateCoupon (
            @RequestParam String couponId,
            @RequestParam String username){
        return userCouponServices.updateCoupon(couponId,username);
    }
}
