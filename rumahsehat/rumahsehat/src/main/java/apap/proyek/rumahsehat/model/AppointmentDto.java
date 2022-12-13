package apap.proyek.rumahsehat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {
    String id;
    String waktuAwal;
    boolean status;
    String pasien;
    String dokter;
    String tagihan;
    Long resep;
}
