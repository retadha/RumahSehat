package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Appointment;

import java.util.Map;

public interface AppointmentRestService {
    Map getListAppointment(String uuid);
    Map getAppointmentById(String id);
    Appointment createAppointment(Appointment appointment);
}
