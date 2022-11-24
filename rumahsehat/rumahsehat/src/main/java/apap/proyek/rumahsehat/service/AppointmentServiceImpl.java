package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Appointment;
import apap.proyek.rumahsehat.repository.AppointmentDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService{
    @Autowired
    AppointmentDb appointmentDb;


    @Override
    public void save(Appointment appointment) {
        appointmentDb.save(appointment);
    }

    @Override
    public Appointment findAppointmentById(String id) {
        Optional<Appointment> appointment = appointmentDb.findAppointmentById(id);
        if (appointment.isPresent()) {
            return appointment.get();
        } else
            return null;
    }
}
