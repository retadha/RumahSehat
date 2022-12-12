package apap.proyek.rumahsehat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DokterDto {
    String nama;
    int tarif;
}
