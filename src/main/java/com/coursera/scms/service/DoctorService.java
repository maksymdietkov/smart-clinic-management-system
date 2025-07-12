package com.coursera.scms.service;

import com.coursera.scms.model.Doctor;
import com.coursera.scms.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Optional<Doctor> findById(Long id) {
        return doctorRepository.findById(id);
    }

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();  // ← этот метод ты используешь в контроллере
    }

    public List<String> getAvailableTimeSlots(Long doctorId, LocalDate date) {
        return Arrays.asList("09:00", "10:00", "11:00", "13:00", "14:00", "15:00");
    }

    public boolean validateLogin(String email, String password) {
        Optional<Doctor> doctorOpt = doctorRepository.findByEmail(email);
        return doctorOpt.map(doctor -> doctor.getPassword().equals(password)).orElse(false);
    }

    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteById(Long id) {
        doctorRepository.deleteById(id);
    }

    public List<Doctor> searchDoctorsByName(String name) {
        return doctorRepository.findByNameContainingIgnoreCase(name);
    }
    public Optional<Doctor> findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

}
