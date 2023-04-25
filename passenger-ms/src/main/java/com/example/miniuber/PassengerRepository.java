package com.example.miniuber;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@EnableKafka
@Repository
public class PassengerRepository {
    private final KafkaTemplate<String, String> kafkaTemplate
    final JdbcTemplate jdbcTemplate;
    private String serverId = "";

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public PassengerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PassengerRepository(KafkaTemplate<String, String> kafkaTemplate, JdbcTemplate jdbcTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Passenger> loginPassenger(Passenger passenger) {
        String sql = "SELECT * FROM passengers WHERE username = ? AND password = ?";
        Object[] params = {passenger.getUsername(), passenger.getPassword()};
        try {
            Passenger user = jdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Passenger.class));
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void registerPassenger(Passenger passenger) {
        String sql = "INSERT INTO passengers (username, password, last_location, is_taken) VALUES (?, ?, ?, ?, ?)";
        Object[] params = {passenger.getUsername(), passenger.getPassword(), passenger.getLastGeolocation(), passenger.isPassengerTaken()};
        jdbcTemplate.update(sql, params);
    }

    public Optional<Passenger> findById(int id) {
        String sql = "SELECT * FROM passengers WHERE id = ?";
        Object[] params = {id};
        try {
            Passenger passenger = jdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Passenger.class));
            return Optional.ofNullable(passenger);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public boolean isPassengerTaken(int id) {
        String sql = "SELECT * FROM passengers WHERE id = ?";
        Object[] params = {id};
        try {
            Passenger passenger = jdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Passenger.class));
            return passenger.isPassengerTaken();
        } catch (EmptyResultDataAccessException e) {
            return true;
        }
    }

    public boolean requestDriver(int passengerId, AddDriverRequest addDriverRequest) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8081/drivers/driver/" + addDriverRequest.getDriverId();
        HttpEntity httpEntity = new HttpEntity<>(addDriverRequest);
        String response = restTemplate.postForObject(url, httpEntity, String.class);
        if (response.isEmpty()) {
            return false;
        }
        return true;
    }

    public void postLastGeolocation(int id, String location) {
        kafkaTemplate.send("passenger_geolocation", location);
    }
}
