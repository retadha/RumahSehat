package apap.proyek.rumahsehat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagihanDto {
    String kode;
    String tanggalDibuat;
    String tanggalBayar;
    boolean status;
    int jumlahTagihan;
    String appointment;
}
