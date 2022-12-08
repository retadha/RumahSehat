package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Apoteker;
import apap.proyek.rumahsehat.model.Dokter;
import apap.proyek.rumahsehat.model.Pasien;
import apap.proyek.rumahsehat.model.UserModel;

import java.util.List;

public interface PasienService {

    void deletePasien(Pasien pasien);

    Pasien getPasienById(String id);

    List<Pasien> findAll();

    Pasien addPasien(Pasien pasien, UserModel user, Integer saldo, Integer umur);
}
