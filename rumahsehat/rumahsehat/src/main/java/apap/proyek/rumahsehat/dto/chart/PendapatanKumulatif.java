package apap.proyek.rumahsehat.dto.chart;

import apap.proyek.rumahsehat.model.Appointment;
import apap.proyek.rumahsehat.model.Dokter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties({ "amount", "start" })
public class PendapatanKumulatif extends Pendapatan {
    Long totalAmount = 0L;
    Long totalAppointment = 0L;

    public PendapatanKumulatif(Dokter dokter, List<Appointment> appointments) {
        super(dokter, appointments);
        filterAppointmentByStatus();
    }

    public PendapatanKumulatif calculate() {
        for (Appointment appointment : appointments) {
            this.totalAppointment++;
            this.totalAmount += dokter.getTarif();
        }
        return this;
    }

}
