package apap.proyek.rumahsehat.dto.chart;

import apap.proyek.rumahsehat.model.Appointment;
import apap.proyek.rumahsehat.model.Dokter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Pendapatan {
    String nama;
    Long[] amount;

    LocalDateTime start;

    @JsonIgnore
    Dokter dokter;
    @JsonIgnore
    List<Appointment> appointments;

    public Pendapatan(Dokter dokter, List<Appointment> appointments) {
        this.dokter = dokter;
        this.nama = dokter.getUser().getNama();
        this.appointments = appointments;
    }

    protected void filterAppointmentByStatus() {
        List<Appointment> newAppointment = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getIsDone()) {
                newAppointment.add(appointment);
            }
        }
        this.appointments = newAppointment;
    }

    protected void filterAppointmentByLocalDate(LocalDateTime start, LocalDateTime end) {
        filterAppointmentByStatus();
        List<Appointment> newAppointment = new ArrayList<>();
        for (Appointment appointment : appointments) {
            LocalDateTime appointmentDate = appointment.getWaktuAwal();
            if (appointmentDate.isBefore(end) && appointmentDate.isAfter(start)) {
                newAppointment.add(appointment);
            }
        }
        this.appointments = newAppointment;
    }
}
