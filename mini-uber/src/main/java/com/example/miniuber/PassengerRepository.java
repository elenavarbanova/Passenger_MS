package com.example.miniuber;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Properties;

@Repository
public class PassengerRepository {

    final JdbcTemplate jdbcTemplate;
    private String serverId = "";

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public PassengerRepository(JdbcTemplate jdbcTemplate) {
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

//    public void requestDriver(int passengerId, AddDriverRequest addDriverRequest) {
//
//    }

    public void postLastGeolocation(int id, String location) {
        String channelName = "geolocation-oassenger" + Integer.toString(id);

        Properties properties = new Properties();
        properties.put("bootstrap.servers", serverId);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        ProducerRecord producerRecord = new ProducerRecord(channelName, "location", location);

        KafkaProducer kafkaProducer = new KafkaProducer(properties);
        kafkaProducer.send(producerRecord);
        kafkaProducer.close();
    }
}
