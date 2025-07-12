package com.coursera.scms.controller;

import com.coursera.scms.model.Appointment;
import com.coursera.scms.model.Doctor;
import com.coursera.scms.service.AppointmentService;
import com.coursera.scms.service.DoctorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class DoctorController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/doctor/dashboard")
    public String doctorDashboard(Model model, HttpSession session) {
        // Получаем email доктора из сессии
        String email = (String) session.getAttribute("doctorEmail");

        // Если в сессии нет email — перенаправляем на страницу логина
        if (email == null) {
            return "redirect:/login-doctor";
        }

        Optional<Doctor> doctorOpt = doctorService.findByEmail(email);

        if (doctorOpt.isPresent()) {
            Long doctorId = doctorOpt.get().getId();
            List<Appointment> appointments = appointmentService.getAppointmentsForDoctorId(doctorId);
            model.addAttribute("appointments", appointments);
            return "doctor-dashboard"; // имя Thymeleaf шаблона doctor-dashboard.html
        } else {
            model.addAttribute("error", "Doctor not found");
            return "login-doctor"; // можно сделать страницу ошибки, либо редирект
        }
    }
}
