package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Resep;

import java.util.List;

public interface ResepService {
    Resep getResepById(Long idResep);
    Resep saveResep(Resep resep);
//    void saveResep(Resep resep);
    List<Resep> findAllResep();
}
