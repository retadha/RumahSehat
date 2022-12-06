package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Appointment;
import apap.proyek.rumahsehat.repository.AppointmentDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class AppointmentRestServiceImpl implements AppointmentRestService {
    @Autowired
    private AppointmentDb appointmentDb;

    @Override
    public Map getAppointmentById(String id) {
        Optional<Appointment> appointment = appointmentDb.findById(id);
        Appointment appointmentPilihan = null;
        if (appointment.isPresent()) {
            appointmentPilihan = appointment.get();
        } else {
            throw new NoSuchElementException();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", appointmentPilihan.getId());
        map.put("waktuAwal", appointmentPilihan.getWaktuAwal());
        map.put("status", appointmentPilihan.getIsDone());
        map.put("dokter", appointmentPilihan.getDokter().getUser().getNama());
        map.put("pasien", appointmentPilihan.getPasien().getUser().getNama());
        if (appointmentPilihan.getResep() == null) {
            map.put("resep", null);
        } else {
            map.put("resep", appointmentPilihan.getResep());
        }
        return map;
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentDb.save(appointment);
    }
}
