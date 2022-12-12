package apap.proyek.rumahsehat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResepDto {
    Long id;
    String pasien;
    String dokter;
    String apoteker;
    boolean status;
    List<ObatDto> listObat;
}
