package th.ac.ku.KaraokeService.models;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.util.Date;
import java.util.List;

public class UserModel {
    private String username;
    private String password;
    private String name;
    private String email;
    private String role;
    private List<String> topBranches;



    private int totalHours;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss",timezone="Asia/Bangkok")
    private Date recentLogin;

    private String stationedBranchId;

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }
    public List<String> getTopBranches() {
        return topBranches;
    }

    public void setTopBranches(List<String> topBranches) {
        this.topBranches = topBranches;
    }

    public String getStationedBranchId() {
        return stationedBranchId;
    }

    public void setStationedBranchId(String stationedBranchId) {
        this.stationedBranchId = stationedBranchId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getRecentLogin() {
        return recentLogin;
    }

    public void setRecentLogin(Date recentLogin) {
        this.recentLogin = recentLogin;
    }
}
