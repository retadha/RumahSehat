package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Pasien;
import apap.proyek.rumahsehat.repository.PasienDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasienServiceImpl implements PasienService {

    @Autowired
    private PasienDb pasienDb;

    @Override
    public List<Pasien> findAll() {
        return pasienDb.findAll();
    }
}
