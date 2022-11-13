package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Apoteker;
import apap.proyek.rumahsehat.model.Dokter;
import apap.proyek.rumahsehat.model.UserModel;
import apap.proyek.rumahsehat.repository.ApotekerDb;
import apap.proyek.rumahsehat.repository.DokterDb;
import apap.proyek.rumahsehat.repository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApotekerServiceImpl implements ApotekerService {

    @Autowired
    private UserDb userDb;

    @Autowired
    private ApotekerDb apotekerDb;

    @Override
    public Apoteker addApoteker(Apoteker apoteker, UserModel user) {
        apoteker.setUser(user);
        return apotekerDb.save(apoteker);
    }

    @Override
    public void deleteApoteker(Apoteker apoteker) {
        UserModel user = userDb.findByUuid(apoteker.getUuid());
        apotekerDb.delete(apoteker);
        userDb.delete(user);
    }

    @Override
    public Apoteker getApotekerById(String id) {
        return apotekerDb.findByUuid(id);
    }

    @Override
    public List<Apoteker> findAll() {
        return apotekerDb.findAll();
    }
}
