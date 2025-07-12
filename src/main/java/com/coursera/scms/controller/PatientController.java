package com.coursera.scms.controller;

import com.coursera.scms.model.Doctor;
import com.coursera.scms.service.AppointmentService;
import com.coursera.scms.service.DoctorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/patient/dashboard")
    public String patientDashboard(@RequestParam(value = "keyword", required = false) String keyword,
                                   Model model,
                                   HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (!"patient".equals(role)) {
            return "redirect:/login-patient";
        }

        List<Doctor> doctors;

        if (keyword != null && !keyword.isBlank()) {
            doctors = doctorService.searchDoctorsByName(keyword);
        } else {
            doctors = doctorService.findAllDoctors();  // ← единообразно
        }

        model.addAttribute("doctors", doctors);
        model.addAttribute("keyword", keyword);

        return "patient-dashboard";
    }

    @GetMapping("/patient/book")
    public String showBookingForm(Model model, HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (!"patient".equals(role)) {
            return "redirect:/login-patient";
        }

        model.addAttribute("doctors", doctorService.findAllDoctors());
        return "book-appointment";
    }

    @PostMapping("/patient/book")
    public String bookAppointment(@RequestParam Long doctorId,
                                  @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        String role = (String) session.getAttribute("userRole");
        Long patientId = (Long) session.getAttribute("patientId");

        if (!"patient".equals(role) || patientId == null) {
            return "redirect:/login-patient";
        }

        try {
            appointmentService.createAppointment(doctorId, patientId, dateTime);
            redirectAttributes.addFlashAttribute("success", "Appointment booked successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to book appointment: " + e.getMessage());
        }

        return "redirect:/patient/book";
    }
}
