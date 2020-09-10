package com.covid.dashboard.repository;

import com.covid.dashboard.entity.Patient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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


    @Query(value = "Select count(*) from Patient where corona_positive='Y'",nativeQuery = true)
    int getAllCoronaPatientCount();

    @Query(value = "Select count(*) from Patient where corona_positive='N'",nativeQuery = true)
    int getAllRecoveredPatientsCount();

    @Query(value = "Select count(*) from Patient where dead=true",nativeQuery = true)
    int getAllDeathPatientsCount();


    long countByCoronaPositiveAndUser_Country(String coronaPositive, String country);
    long countByCoronaPositiveAndUser_CountryAndUser_State(String coronaPositive, String country, String state);
    long countByCoronaPositiveAndUser_CountryAndUser_StateAndUser_City(String coronaPositive, String country, String state,String city);

    long countByRecoveredAndUser_Country(String recovered, String country);
    long countByRecoveredAndUser_CountryAndUser_State(String recovered, String country, String state);
    long countByRecoveredAndUser_CountryAndUser_StateAndUser_city(String recovered, String country, String state, String city);

    long countByDeadAndUser_Country(boolean dead,String country);
    long countByDeadAndUser_CountryAndUser_State(boolean dead,String country,String state);
    long countByDeadAndUser_CountryAndUser_StateAndUser_city(boolean dead,String country,String state,String city);


    List<Patient> findByPhysicianId(String physicianId);






}
