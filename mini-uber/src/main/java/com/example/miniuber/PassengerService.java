package com.example.miniuber;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PassengerService {
    private PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Optional<Passenger> loginPassenger(AddPassengerRequest addPassengerRequest) {
        Passenger passenger = new Passenger();

        passenger.setUsername(addPassengerRequest.getUsername());
        passenger.setPassword(addPassengerRequest.getPassword());
        passenger.setLastGeolocation("");
        passenger.setPassengerTaken(false);
        return passengerRepository.loginPassenger(passenger);
    }

    public void registerPassenger(AddPassengerRequest addPassengerRequest) {
        Passenger passenger = new Passenger();

        passenger.setUsername(addPassengerRequest.getUsername());
        passenger.setPassword(addPassengerRequest.getPassword());
        passenger.setLastGeolocation("");
        passenger.setPassengerTaken(false);
        passengerRepository.registerPassenger(passenger);
    }

    public Optional<Passenger> getPassenger(int id) {
        return passengerRepository.findById(id);
    }

    public boolean isPassengerTaken(int id) {
        return passengerRepository.isPassengerTaken(id);
    }

    public void requestDriver(int passengerId, AddDriverRequest addDriverRequest) {
        passengerRepository.addDriverRequest(addDriverRequest.getDriver());
    }
    public void postLastGeolocation(int id, String geolocation) {
        Optional<Passenger> isPassengerReal = getPassenger(id);
        if (isPassengerReal.isPresent()) {
            passengerRepository.postLastGeolocation(id, geolocation);
        }
    }
}
