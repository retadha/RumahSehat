package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Appointment;

import java.util.List;

public interface AppointmentService {
    List<Appointment> getListAppointment();
    Appointment getAppointmentById(String id);
    void save(Appointment appointment);
}
