package th.ac.ku.KaraokeService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import th.ac.ku.KaraokeService.models.RoomModel;
import th.ac.ku.KaraokeService.services.RoomServices;

import java.util.List;
import java.util.concurrent.ExecutionException;
@RestController
public class RoomController {

    @Autowired
    private RoomServices roomServices;

    @GetMapping("/rooms")
    public List<RoomModel> getAllRoomInBranch(@RequestParam String branchId)
            throws ExecutionException, InterruptedException {
        return roomServices.getRooms(branchId);
    }
}
