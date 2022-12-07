package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Jumlah;

import java.util.List;
import java.util.Optional;

public interface JumlahService {
    List<Jumlah> findByResep(Long idResep);
    boolean checkStok(Long idResep);
<<<<<<< HEAD
    int calculatePrice(Long idResep);
    Jumlah addJumlah(Jumlah jumlah);
=======
    void minusStock(Long idResep);
>>>>>>> 97e64667e59d1c234ed99cb4e63d0517810a2f4c
}
