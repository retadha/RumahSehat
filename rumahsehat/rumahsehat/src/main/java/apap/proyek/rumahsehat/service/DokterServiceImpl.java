package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Dokter;
import apap.proyek.rumahsehat.model.UserModel;
import apap.proyek.rumahsehat.repository.DokterDb;
import apap.proyek.rumahsehat.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DokterServiceImpl implements DokterService {

    @Autowired
    private UserDb userDb;

    @Autowired
    private DokterDb dokterDb;


    @Override
    public Dokter addDokter(Dokter dokter, UserModel user, Integer tarif) {
        dokter.setUser(user);
        dokter.setTarif(tarif);
        return dokterDb.save(dokter);
    }

    @Override
    public void deleteDokter(Dokter dokter) {
        UserModel user = userDb.findByUuid(dokter.getUuid());
        dokterDb.delete(dokter);
        userDb.delete(user);
    }

    @Override
    public Dokter getDokterById(String id) {
        return dokterDb.findByUuid(id);
    }


    @Override
    public List<Dokter> findAll() {
        return dokterDb.findAll();
    }
}
