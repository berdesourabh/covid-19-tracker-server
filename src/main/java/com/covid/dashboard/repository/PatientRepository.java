package com.covid.dashboard.repository;

import com.covid.dashboard.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Patient findByUser_email(String email);

    List<Patient> findByUser_Country(String country);

    List<Patient> findByUser_State(String state);

    List<Patient> findByUser_City(String state);

    List<Patient> findByUser_CountryAndUser_State(String country, String state);

    List<Patient> findByUser_CountryAndUser_StateAndUser_City(String country, String state, String city);


}
