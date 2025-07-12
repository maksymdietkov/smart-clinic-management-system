package com.coursera.scms.repository;

import com.coursera.scms.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Новый метод для отображения всех приёмов врача
    List<Appointment> findByDoctorId(Long doctorId);

    // Уже имеющийся метод для фильтрации по времени
    List<Appointment> findByDoctorIdAndAppointmentTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    List<Appointment> findByDoctorIdOrderByAppointmentTimeAsc(Long doctorId);

}
