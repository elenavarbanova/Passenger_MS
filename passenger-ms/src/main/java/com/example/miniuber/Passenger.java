package com.example.miniuber;

public class Passenger {
    private String username;
    private String password;
    private String lastGeolocation;
    private boolean isPassengerTaken;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLastGeolocation(String lastGeolocation) {
        this.lastGeolocation = lastGeolocation;
    }

    public void setPassengerTaken(boolean passengerTaken) {
        isPassengerTaken = passengerTaken;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLastGeolocation() {
        return lastGeolocation;
    }

    public boolean isPassengerTaken() {
        return isPassengerTaken;
    }
}
