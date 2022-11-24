package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Obat;
import apap.proyek.rumahsehat.model.Pasien;
import apap.proyek.rumahsehat.repository.ObatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ObatServiceImpl implements ObatService {
    @Autowired
    ObatDb obatDb;

    @Override
    public List<Obat> getListObat() {
        return obatDb.findAll();
    }
    @Override
    public Obat updateStok(Obat obat) {
        obatDb.save(obat);
        return obat;
    }
    @Override
    public Obat getObatByIdObat(String id) {
        Optional<Obat> obat = obatDb.findByIdObat(id);
        if (obat.isPresent()) {
            return obat.get();
        } else
            return null;
    }
}
