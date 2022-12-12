package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AppointmentRestService {
    Map<String, List<AppointmentDto>> getListAppointment(String uuid);
    Map<String, List<DokterDto>> getListDokterTarif();
    AppointmentDto getAppointmentById(String id);
    Appointment createAppointment(Appointment appointment, String id, LocalDateTime waktuAwal, Pasien pasien, Dokter dokter);
}
