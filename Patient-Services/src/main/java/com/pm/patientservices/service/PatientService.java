package com.pm.patientservices.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.patientservices.dto.PatientResponseDTO;
import com.pm.patientservices.model.Patient;
import com.pm.patientservices.repository.PatientRepository;


@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;

	public List<PatientResponseDTO> getPatients() {
		List<Patient> patients = patientRepository.findAll();
	}
}
