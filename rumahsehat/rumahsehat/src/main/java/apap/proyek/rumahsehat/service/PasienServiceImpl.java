package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Apoteker;
import apap.proyek.rumahsehat.model.Pasien;
import apap.proyek.rumahsehat.model.Tagihan;
import apap.proyek.rumahsehat.model.UserModel;
import apap.proyek.rumahsehat.repository.PasienDb;
import apap.proyek.rumahsehat.repository.TagihanDb;
import apap.proyek.rumahsehat.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PasienServiceImpl implements PasienService {

    @Autowired
    private UserDb userDb;

    @Autowired
    private PasienDb pasienDb;

    @Autowired
    private TagihanDb tagihanDb;

    @Override
    public void deletePasien(Pasien pasien) {
        UserModel user = userDb.findByUuid(pasien.getUuid());
        pasienDb.delete(pasien);
        userDb.delete(user);
    }

    @Override
    public Pasien getPasienById(String id) {
        return pasienDb.findByUuid(id);
    }

    @Override
    public Map getPasienByTagihan(String kode) {
        Optional<Tagihan> tagihanOptional = tagihanDb.findById(kode);
        Tagihan tagihan = null;
        if (tagihanOptional.isPresent()){
            tagihan = tagihanOptional.get();
        } else {
            throw new NoSuchElementException();
        }
        Pasien pasien = tagihan.getKodeAppointment().getPasien();
        Map<String, Object> map = new HashMap<>();
        map.put("id", pasien.getUuid());
        map.put("username", pasien.getUser().getUsername());
        map.put("email", pasien.getUser().getEmail());
        map.put("nama", pasien.getUser().getNama());
        map.put("saldo", pasien.getSaldo());
        map.put("umur", pasien.getUmur());
        return map;
    }

    @Override
    public List<Pasien> findAll() {
        return pasienDb.findAll();
    }
}
