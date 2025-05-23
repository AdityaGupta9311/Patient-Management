package com.pm.patientservices.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.patientservices.dto.PatientResponseDTO;
import com.pm.patientservices.mapper.PatientMapper;
import com.pm.patientservices.model.Patient;
import com.pm.patientservices.repository.PatientRepository;


@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;

	public PatientService(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}
	
	public List<PatientResponseDTO> getPatients() {
		List<Patient> patients = patientRepository.findAll();
		
		List<PatientResponseDTO> patientResponseDTOs = patients.stream()
				.map(patient -> PatientMapper.toDTO(patient)).toList();
		
		return patientResponseDTOs;
	}
}
