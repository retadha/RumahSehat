package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Jumlah;
import apap.proyek.rumahsehat.model.Obat;
import apap.proyek.rumahsehat.model.Pasien;

import java.util.List;
import java.util.Optional;

public interface ObatService {
    List<Obat> findAll();
    Optional<Obat> findByIdObat(String idObat);
}
