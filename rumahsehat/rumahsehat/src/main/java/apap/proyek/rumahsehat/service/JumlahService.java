package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Jumlah;

import java.util.List;
import java.util.Optional;

public interface JumlahService {
    List<Jumlah> findByResep(Long idResep);
    boolean checkStok(Long idResep);
    int calculatePrice(Long idResep);
    Jumlah addJumlah(Jumlah jumlah);
    void minusStock(Long idResep);
}
