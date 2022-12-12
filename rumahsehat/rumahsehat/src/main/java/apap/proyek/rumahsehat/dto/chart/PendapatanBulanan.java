package apap.proyek.rumahsehat.dto.chart;

import apap.proyek.rumahsehat.model.Appointment;
import apap.proyek.rumahsehat.model.Dokter;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.List;

public class PendapatanBulanan extends Pendapatan {
    public PendapatanBulanan(Dokter dokter, List<Appointment> appointments) {
        super(dokter, appointments);
    }

    public PendapatanBulanan selectAppointmentByMonth(int year, int month) {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0, 0);
        this.start = start;
        filterAppointmentByLocalDate(start, start.plusMonths(1));
        return this;
    }

    public PendapatanBulanan calculate() {
        Month current_month = start.getMonth();
        int total_day = current_month.length(Year.of(start.getYear()).isLeap());
        Long[] amount = new Long[total_day];
        if (appointments.size() != 0) {
            for (int i = 0; i < total_day; i++) {
                long dailySalary = getDailySalary(i);
                amount[i] = dailySalary;
            }
        }
        this.amount = amount;
        return this;
    }

    private long getDailySalary(int day) {
        day++;
        long dailyAmount = 0L;
        for (Appointment appointment : appointments) {
            if (appointment.getWaktuAwal().getDayOfMonth() != day)
                continue;
            dailyAmount += dokter.getTarif();
        }
        return dailyAmount;
    }
}
