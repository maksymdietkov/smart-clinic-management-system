package com.coursera.scms.service;

import com.coursera.scms.model.Appointment;
import com.coursera.scms.repository.AppointmentRepository;
import com.coursera.scms.repository.DoctorRepository;
import com.coursera.scms.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Appointment> getAppointmentsForDoctor(String doctorEmail) {
        Doctor doctor = doctorRepository.findByEmail(doctorEmail)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
                doctor.getId(),
                java.time.LocalDateTime.MIN,
                java.time.LocalDateTime.MAX
        );
    }
}
