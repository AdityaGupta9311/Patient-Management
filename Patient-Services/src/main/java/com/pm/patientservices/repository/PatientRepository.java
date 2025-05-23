package com.pm.patientservices.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pm.patientservices.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {

	
}
