package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Apoteker;
import apap.proyek.rumahsehat.model.Dokter;
import apap.proyek.rumahsehat.repository.ApotekerDb;
import apap.proyek.rumahsehat.repository.DokterDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApotekerServiceImpl implements ApotekerService {

    @Autowired
    private ApotekerDb apotekerDb;

    @Override
    public List<Apoteker> findAll() {
        return apotekerDb.findAll();
    }
}
