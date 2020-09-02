package com.covid.dashboard.repository;

import com.covid.dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<User,String> {

    @Query("Select distinct country from User ")
    List<String> getAllCountries();



    @Query("Select distinct state from User user where user.country= :country ")
    List<String> getStatesByCountry(String country);

    @Query("Select distinct city from User user where user.country= :country and user.state = :state ")
    List<String> getCitiesByCountryAndState(String country,String state);

}
