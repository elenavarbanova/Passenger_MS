package com.example.miniuber;

public class AddPassengerRequest {
    private String username;
    private String password;
    private String lastGeolocation;
    private boolean isPassengerTaken;

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

    public String getLastGeolocation() {
        return lastGeolocation;
    }

    public void setLastGeolocation(String lastGeolocation) {
        this.lastGeolocation = lastGeolocation;
    }

    public boolean isPassengerTaken() {
        return isPassengerTaken;
    }

    public void setPassengerTaken(boolean passengerTaken) {
        isPassengerTaken = passengerTaken;
    }
}
