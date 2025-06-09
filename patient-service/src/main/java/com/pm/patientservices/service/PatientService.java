package com.pm.patientservices.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.pm.patientservices.dto.PatientRepuestDTO;
import com.pm.patientservices.dto.PatientResponseDTO;
import com.pm.patientservices.exception.EmailAlreadyExistsException;
import com.pm.patientservices.exception.PatientNotFoundException;
import com.pm.patientservices.grpc.BillingServiceGrpcClient;
import com.pm.patientservices.kafka.KafkaProducer;
import com.pm.patientservices.mapper.PatientMapper;
import com.pm.patientservices.model.Patient;
import com.pm.patientservices.repository.PatientRepository;

@Service
public class PatientService {

	private final PatientRepository patientRepository;
	private final BillingServiceGrpcClient billingServiceGrpcClient;
	private final KafkaProducer kafkaProducer;

	public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient,
			KafkaProducer kafkaProducer) {
		this.patientRepository = patientRepository;
		this.billingServiceGrpcClient = billingServiceGrpcClient;
		this.kafkaProducer = kafkaProducer;
	}

	public List<PatientResponseDTO> getPatients() {
		List<Patient> patients = patientRepository.findAll();
		List<PatientResponseDTO> patientResponseDTOs = patients.stream().map(patient -> PatientMapper.toDTO(patient))
				.toList();
		return patientResponseDTOs;
	}

	public PatientResponseDTO createPatient(PatientRepuestDTO patientRepuestDTO) {
		if (patientRepository.existsByEmail(patientRepuestDTO.getEmail())) {
			throw new EmailAlreadyExistsException(
					"A Patient with this Email Already Exits " + patientRepuestDTO.getEmail());
		}
		Patient newPatient = patientRepository.save(PatientMapper.toModal(patientRepuestDTO));
		billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(), newPatient.getName(),
				newPatient.getEmail());
		kafkaProducer.sendEvent(newPatient);
		return PatientMapper.toDTO(newPatient);
	}

	public PatientResponseDTO updatePatient(UUID id, PatientRepuestDTO patientRepuestDTO) {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("Patient Not Found with this ID: " + id));

		if (patientRepository.existsByEmailAndIdNot(patientRepuestDTO.getEmail(), id)) {
			throw new EmailAlreadyExistsException(
					"A Patient with this Email Already Exits " + patientRepuestDTO.getEmail());
		}

		patient.setName(patientRepuestDTO.getName());
		patient.setAddress(patientRepuestDTO.getAddress());
		patient.setEmail(patientRepuestDTO.getEmail());
		patient.setDateOfBirth(LocalDate.parse(patientRepuestDTO.getDateOfBirth()));

		Patient updatePatient = patientRepository.save(patient);
		return PatientMapper.toDTO(updatePatient);
	}

	public void deletePatient(UUID id) {
		patientRepository.deleteById(id);
	}
}
