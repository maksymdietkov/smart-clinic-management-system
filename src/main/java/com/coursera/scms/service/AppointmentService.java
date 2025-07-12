package com.coursera.scms.service;

import com.coursera.scms.model.Appointment;
import com.coursera.scms.repository.AppointmentRepository;
import com.coursera.scms.repository.DoctorRepository;
import com.coursera.scms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    // Получить список всех записей к доктору на ближайший месяц
    public List<Appointment> getAppointmentsForDoctorId(Long doctorId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthLater = now.plusMonths(1);
        return appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctorId, now, oneMonthLater);
    }

    // Создать новую запись на прием
    public Appointment createAppointment(Long doctorId, Long patientId, LocalDateTime appointmentTime) throws Exception {
        // Проверка существования доктора
        Optional doctorOpt = doctorRepository.findById(doctorId);
        if (doctorOpt.isEmpty()) {
            throw new Exception("Doctor not found");
        }
        // Проверка существования пациента
        Optional patientOpt = patientRepository.findById(patientId);
        if (patientOpt.isEmpty()) {
            throw new Exception("Patient not found");
        }

        // Создаем запись
        Appointment appointment = new Appointment();
        appointment.setDoctor((com.coursera.scms.model.Doctor) doctorOpt.get());
        appointment.setPatient((com.coursera.scms.model.Patient) patientOpt.get());
        appointment.setAppointmentTime(appointmentTime);

        return appointmentRepository.save(appointment);
    }

    // Другие методы можно добавить при необходимости
}
