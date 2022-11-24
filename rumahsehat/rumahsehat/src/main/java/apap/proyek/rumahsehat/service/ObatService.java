package apap.proyek.rumahsehat.service;

<<<<<<< HEAD
import apap.proyek.rumahsehat.model.Obat;

import java.util.List;


public interface ObatService {
    List<Obat> getListObat();

    Obat updateStok(Obat obat);
    Obat getObatByIdObat(String id);
=======
import apap.proyek.rumahsehat.model.Jumlah;
import apap.proyek.rumahsehat.model.Obat;
import apap.proyek.rumahsehat.model.Pasien;

import java.util.List;
import java.util.Optional;

public interface ObatService {
    List<Obat> findAll();
    Optional<Obat> findByIdObat(String idObat);
>>>>>>> ad8aae416ca2465b83a8c85608ba3dd9a1ac4682
}
