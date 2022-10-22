package th.ac.ku.KaraokeService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import th.ac.ku.KaraokeService.models.BookingModel;
import th.ac.ku.KaraokeService.services.BookingServices;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class BookingController {

    @Autowired
    private BookingServices bookingServices;

    @PostMapping("/booking/{type}")
    public String addBooking(
            @RequestBody BookingModel bookDetail,
            @PathVariable String type)
            throws ExecutionException, InterruptedException {
        return bookingServices.createBooking(bookDetail,type);
    }

    @GetMapping("/booking")
    public List<BookingModel> getBookingByRoomID(@RequestParam String roomId)
            throws ExecutionException, InterruptedException {
        return bookingServices.getBookingByRoomId(roomId);
    }

    @GetMapping("/booking/user")
    public List<BookingModel> getBookingOfUser(
            @RequestParam String name,
            @RequestParam boolean isBooking)
            throws ExecutionException, InterruptedException {
        return bookingServices.getBookingForUser(name,isBooking);
    }

    @GetMapping("/booking/user/history")
    public List<BookingModel> getBookingHistory(@RequestParam String username)
            throws ExecutionException, InterruptedException {
        return bookingServices.getHistory(username);
    }

    @GetMapping("/booking/emp")
    public List<BookingModel> getBookingForBranch(
            @RequestParam String branchId,
            @RequestParam(name = "query",required = false,defaultValue = "") String query)
            throws ExecutionException, InterruptedException {
        return bookingServices.getBookingByBranch(branchId,query);
    }

    @GetMapping("/booking/tasks")
    public List<BookingModel> getTasks(
            @RequestParam String branchId,
            @RequestParam(name = "query",required = false,defaultValue = "") String query
            ,@RequestParam boolean isBooking)
            throws ExecutionException, InterruptedException {
        return  bookingServices.searchTasksBooking(branchId, isBooking, query);
    }

    @PutMapping("/booking/user/history/update")
    public String updateToHistory() throws ExecutionException, InterruptedException {
        return bookingServices.updateStatus();
    }

    @PutMapping("/booking/update/status")
    public String updateBookingStatus(
            @RequestParam String bookingId,
            @RequestParam String responseBy)
            throws ExecutionException, InterruptedException {
        return bookingServices.updatePaidStatus(bookingId,responseBy);
    }

    @DeleteMapping("/booking/cancel")
    public String cancelBooking(@RequestParam String bookingID)
            throws ExecutionException, InterruptedException {
        return bookingServices.cancelBooking(bookingID);
    }

    @PostMapping("/booking/checktime")
    public String checkBookingTime(@RequestBody BookingModel booking)
            throws ExecutionException, InterruptedException {
        return  bookingServices.checkBookingTime(booking);
    }
}
