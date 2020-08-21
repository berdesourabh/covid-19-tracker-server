package com.covid.dashboard.repository;

import com.covid.dashboard.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Integer> {

    PatientEntity findByUser_uidNumber(String uidNumber);

    List<PatientEntity> findByUser_Country(String country);

    List<PatientEntity> findByUser_State(String state);

    List<PatientEntity> findByUser_City(String state);

    List<PatientEntity> findByUser_CountryAndUser_State(String country, String state);

    List<PatientEntity> findByUser_CountryAndUser_StateAndUser_City(String country, String state, String city);


}
