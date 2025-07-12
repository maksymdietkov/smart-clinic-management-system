package com.coursera.scms.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if ("admin".equals(role)) {
            return "admin-dashboard";  // admin-dashboard.html
        } else {
            return "redirect:/login-admin";
        }
    }

    // Другие админские страницы и методы сюда
}
