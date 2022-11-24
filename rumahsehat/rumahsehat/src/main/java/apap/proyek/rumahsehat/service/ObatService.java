package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Obat;

import java.util.List;


public interface ObatService {
    List<Obat> getListObat();

    Obat updateStok(Obat obat);
    Obat getObatByIdObat(String id);
}
