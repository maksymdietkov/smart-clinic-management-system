package com.coursera.scms.controller;

import com.coursera.scms.model.Appointment;
import com.coursera.scms.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class DoctorController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/doctor/dashboard")
    public String doctorDashboard(Model model, Principal principal) {
        List<Appointment> appointments = appointmentService.getAppointmentsForDoctor(principal.getName());
        model.addAttribute("appointments", appointments);
        return "doctor-dashboard";
    }
}
