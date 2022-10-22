package th.ac.ku.KaraokeService.tools;

import th.ac.ku.KaraokeService.models.BookingModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalculateForUser {

    public List<String> getTopBranch(List<BookingModel> histories){
        Map<String,Integer> branchMap =new HashMap<>();
        for(BookingModel history:histories){
            String branchId = history.getBranchId();
            if(branchMap.isEmpty()){
                branchMap.put(branchId,1);
            } else if (!branchMap.containsKey(branchId)) {
                branchMap.put(branchId,1);
            }
            else{
                branchMap.replace(branchId,branchMap.get(branchId)+1);
            }
        }
        List<Map.Entry<String, Integer>> sortedBranch = new ArrayList<>(branchMap.entrySet());
        sortedBranch.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        List<String> topBranch = new ArrayList<>();
        int  checkBranch = sortedBranch.size();
        if(checkBranch >3){
            for(int i=0;i<=3;i++){
                topBranch.add(sortedBranch.get(i).getKey());
            }
        }
        else {
            for (int i=0;i < checkBranch;i++){
                topBranch.add(sortedBranch.get(i).getKey());
            }
        }

        return topBranch;
    }

    public int calTime(List<BookingModel> histories) throws ParseException {
        int totalHours = 0;
        for (BookingModel history:histories){
            Date start = history.getStartTime();
            Date end = history.getEndTime();
            long diff = end.getTime() - start.getTime();
            totalHours += (int) (diff / (60 * 60 * 1000));
        }
        return totalHours;
    }
}
