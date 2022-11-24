package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Obat;
import apap.proyek.rumahsehat.model.Pasien;
import apap.proyek.rumahsehat.repository.ObatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObatServiceImpl implements ObatService{
    @Autowired
    private ObatDb obatDb;

    @Override
    public List<Obat> findAll() {
        return obatDb.findAll();
    }

    @Override
    public Optional<Obat> findByIdObat(String idObat) {
        return obatDb.findByIdObat(idObat);
    }
}
