package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Dokter;
import apap.proyek.rumahsehat.model.Pasien;

import java.util.List;

public interface PasienService {

    List<Pasien> findAll();
}
