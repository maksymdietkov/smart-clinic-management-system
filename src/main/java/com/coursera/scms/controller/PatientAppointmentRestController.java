package com.coursera.scms.controller;

import com.coursera.scms.model.Appointment;
import com.coursera.scms.service.AppointmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientAppointmentRestController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/appointments")
    public List<Appointment> getAppointmentsForLoggedInPatient(HttpSession session) {
        Long patientId = (Long) session.getAttribute("patientId");
        if (patientId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Patient not logged in");
        }
        return appointmentService.getAppointmentsForPatientId(patientId);
    }
}
