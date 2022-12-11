package apap.proyek.rumahsehat.dto.chart;

import apap.proyek.rumahsehat.model.Appointment;
import apap.proyek.rumahsehat.model.Dokter;

import java.time.LocalDateTime;
import java.util.List;

public class PendapatanTahunan extends Pendapatan {
    public PendapatanTahunan(Dokter dokter, List<Appointment> appointments) {
        super(dokter, appointments);
    }

    public PendapatanTahunan selectAppointmentByYear(int year) {
        LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0, 0);
        this.start = start;
        filterAppointmentByLocalDate(start, start.plusYears(1));
        return this;
    }

    public PendapatanTahunan calculate() {
        this.nama = dokter.getUser().getNama();
        LocalDateTime startOfMonth = this.start;
        Long[] amount = new Long[12];
        if (appointments.size() != 0) {
            for (int i = 0; i < 12; i++) {
                amount[i] = getMonthySalary(startOfMonth);
                startOfMonth = startOfMonth.plusMonths(1);
            }
        }
        this.amount = amount;
        return this;
    }

    private long getMonthySalary(LocalDateTime startOfMonth) {
        long monthlyAmount = 0L;
        for (Appointment appointment : appointments) {
            LocalDateTime appointmentDate = appointment.getWaktuAwal();
            LocalDateTime startOfNextMonth = startOfMonth.plusMonths(1);
            if (appointmentDate.isBefore(startOfNextMonth) && appointmentDate.isAfter(startOfMonth)) {
                monthlyAmount += dokter.getTarif();
            }
        }
        return monthlyAmount;
    }

}
