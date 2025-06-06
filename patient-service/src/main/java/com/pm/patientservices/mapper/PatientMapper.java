package com.pm.patientservices.mapper;

import java.time.LocalDate;

import com.pm.patientservices.dto.PatientRepuestDTO;
import com.pm.patientservices.dto.PatientResponseDTO;
import com.pm.patientservices.model.Patient;

public class PatientMapper {
	
	public static PatientResponseDTO toDTO(Patient patient) {
		PatientResponseDTO patientDTO = new PatientResponseDTO();
		patientDTO.setId(patient.getId().toString());
		patientDTO.setName(patient.getName());
		patientDTO.setAddress(patient.getAddress());
		patientDTO.setEmail(patient.getEmail());
		patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());
		return patientDTO;
	}
	
	public static Patient toModal(PatientRepuestDTO patientRepuestDTO) {
		Patient patient = new Patient();
		patient.setName(patientRepuestDTO.getName());
		patient.setAddress(patientRepuestDTO.getAddress());
		patient.setEmail(patientRepuestDTO.getEmail());
		patient.setDateOfBirth(LocalDate.parse(patientRepuestDTO.getDateOfBirth()));
		patient.setRegisteredDate(LocalDate.parse(patientRepuestDTO.getDateOfBirth()));
		return patient;
	}

}
