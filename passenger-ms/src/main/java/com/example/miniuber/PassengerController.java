package com.example.miniuber;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/passengers")
public class PassengerController {
    private PassengerService passengerService;
    private DriverService driverService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping("/login")
    public void loginPassenger(@RequestBody AddPassengerRequest addPassengerRequest) {
        passengerService.loginPassenger(addPassengerRequest);
    }

    @PostMapping("/register")
    public void registerPassenger(@RequestBody AddPassengerRequest addPassengerRequest) {
        passengerService.registerPassenger(addPassengerRequest);
    }

    @GetMapping("/{id}")
    public Optional<Passenger> getPassenger(@PathVariable int id) {
        Optional<Passenger> isPassengerReal = getPassenger(id);
        if (isPassengerReal.isPresent()) {
            throw new RuntimeException("Passenger not found");
        }
        return passengerService.getPassenger(id);
    }

    @GetMapping("{id}")
    public boolean isPassengerTaken(@PathVariable int id) {
        return passengerService.isPassengerTaken(id);
    }

    @PostMapping("/{id}/request-driver")
    public boolean requestDriver(@PathVariable int passengerId, @RequestBody AddDriverRequest addDriverRequest) {
        Optional<Passenger> isPassengerReal = getPassenger(passengerId);
        if (isPassengerReal.isPresent()) {
            throw new RuntimeException("Passenger not found");
        }
        if (addDriverRequest.getDriverId() == null || addDriverRequest.getDriverId().isEmpty()) {
            throw new RuntimeException("Driver id is required");
        }
        boolean success = passengerService.requestDriver(passengerId, addDriverRequest);
        if(!success) {
            throw new RuntimeException("Driver is unavailable");
            return success;
        }
        return success;
    }

    @PostMapping("/{id}/geolocation/{geolocation}")
    public void postLastGeolocation(@PathVariable int id, @PathVariable String geolocation) {
        Optional<Passenger> isPassengerReal = getPassenger(id);
        if (isPassengerReal.isPresent()) {
            throw new RuntimeException("Passenger not found");
        }
        passengerService.postLastGeolocation(id, geolocation);
    }
}
