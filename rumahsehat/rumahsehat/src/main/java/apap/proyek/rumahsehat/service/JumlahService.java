package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Jumlah;

import java.util.List;
import java.util.Optional;

public interface JumlahService {
    List<Jumlah> findByResep(Long idResep);
    boolean checkStok(Long idResep);
    void minusStock(Long idResep);
}
