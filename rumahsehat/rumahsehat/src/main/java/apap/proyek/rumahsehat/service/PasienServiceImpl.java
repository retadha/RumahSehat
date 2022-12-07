package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Apoteker;
import apap.proyek.rumahsehat.model.Pasien;
import apap.proyek.rumahsehat.model.UserModel;
import apap.proyek.rumahsehat.repository.PasienDb;
import apap.proyek.rumahsehat.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasienServiceImpl implements PasienService {

    @Autowired
    private UserDb userDb;

    @Autowired
    private PasienDb pasienDb;

    @Override
    public void deletePasien(Pasien pasien) {
        UserModel user = userDb.findByUuid(pasien.getUuid());
        pasienDb.delete(pasien);
        userDb.delete(user);
    }

    @Override
    public Pasien addPasien(Pasien pasien, UserModel user, Integer saldo, Integer umur) {
        pasien.setUser(user);
        pasien.setSaldo(saldo);
        pasien.setUmur(umur);
        return pasienDb.save(pasien);
    }

    @Override
    public Pasien getPasienById(String id) {
        return pasienDb.findByUuid(id);
    }

    @Override
    public List<Pasien> findAll() {
        return pasienDb.findAll();
    }
}
