package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Appointment;
import apap.proyek.rumahsehat.model.AppointmentDto;

import java.util.List;
import java.util.Map;

public interface AppointmentRestService {
    Map<String, List<AppointmentDto>> getListAppointment(String uuid);
    AppointmentDto getAppointmentById(String id);
    Appointment createAppointment(Appointment appointment);
}
