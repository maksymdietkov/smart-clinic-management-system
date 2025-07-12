package com.coursera.scms.controller;

import com.coursera.scms.model.Appointment;
import com.coursera.scms.model.Doctor;
import com.coursera.scms.model.Patient;
import com.coursera.scms.service.AppointmentService;
import com.coursera.scms.service.DoctorService;
import com.coursera.scms.service.PatientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class PatientController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

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
            doctors = doctorService.findAllDoctors();
        }

        model.addAttribute("doctors", doctors);
        model.addAttribute("keyword", keyword);

        return "patient-dashboard";
    }

    // --- Новый метод: показать форму записи к выбранному доктору ---
    @GetMapping("/patient/appointments/new")
    public String showAppointmentForm(@RequestParam Long doctorId, Model model, HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (!"patient".equals(role)) {
            return "redirect:/login-patient";
        }

        Optional<Doctor> doctorOpt = doctorService.findById(doctorId);
        if (doctorOpt.isEmpty()) {
            model.addAttribute("error", "Doctor not found");
            return "patient-dashboard";
        }

        model.addAttribute("doctor", doctorOpt.get());
        return "patient-appointment-form"; // шаблон с формой записи
    }

    // --- Новый метод: обработка записи на приём ---
    @PostMapping("/patient/appointments")
    public String createAppointment(@RequestParam Long doctorId,
                                    @RequestParam("appointmentTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime appointmentTime,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        String role = (String) session.getAttribute("userRole");
        if (!"patient".equals(role)) {
            return "redirect:/login-patient";
        }

        String patientEmail = (String) session.getAttribute("userEmail");
        if (patientEmail == null) {
            return "redirect:/login-patient";
        }

        Optional<Patient> patientOpt = patientService.findByEmail(patientEmail);
        if (patientOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Patient not found");
            return "redirect:/login-patient";
        }

        try {
            appointmentService.createAppointment(doctorId, patientOpt.get().getId(), appointmentTime);
            redirectAttributes.addFlashAttribute("success", "Appointment booked successfully!");
            return "redirect:/patient/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to book appointment: " + e.getMessage());
            return "redirect:/patient/appointments/new?doctorId=" + doctorId;
        }
    }
    @GetMapping("/api/patient/appointments/{patientId}")
    @ResponseBody
    public List<Appointment> getAppointmentsByPatientId(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsForPatientId(patientId);
    }

}
