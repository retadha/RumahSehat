package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Dokter;
import apap.proyek.rumahsehat.model.UserModel;
import apap.proyek.rumahsehat.repository.DokterDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DokterServiceImpl implements DokterService {

    @Autowired
    private DokterDb dokterDb;

    @Override
    public Dokter addDokter(Dokter dokter, UserModel user, Integer tarif) {
        dokter.setUser(user);
        dokter.setTarif(tarif);
        return dokterDb.save(dokter);
    }

    @Override
    public List<Dokter> findAll() {
        return dokterDb.findAll();
    }
}
