package com.example.ultimatecm;

public class Club {
    private String clubName;
    private String clubDescription;
    private boolean privacy; // True = Public, False = Private

    public Club(String clubName, String clubDescription, boolean privacy) {
        this.clubName = clubName;
        this.clubDescription = clubDescription;
        this.privacy = privacy;
    }

    public Club() {
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public boolean getPrivacy() {
        return privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }
}
