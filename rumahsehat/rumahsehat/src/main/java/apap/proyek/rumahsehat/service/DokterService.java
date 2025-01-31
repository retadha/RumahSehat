package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Dokter;
import apap.proyek.rumahsehat.model.UserModel;

import java.util.List;

public interface DokterService {

    Dokter addDokter(Dokter dokter, UserModel user, Integer tarif);

    void deleteDokter(Dokter dokter);

    Dokter getDokterById(String id);

    List<Dokter> findAll();
}
