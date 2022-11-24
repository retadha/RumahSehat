package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Obat;
<<<<<<< HEAD
=======
import apap.proyek.rumahsehat.model.Pasien;
>>>>>>> ad8aae416ca2465b83a8c85608ba3dd9a1ac4682
import apap.proyek.rumahsehat.repository.ObatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import javax.transaction.Transactional;
=======
>>>>>>> ad8aae416ca2465b83a8c85608ba3dd9a1ac4682
import java.util.List;
import java.util.Optional;

@Service
<<<<<<< HEAD
@Transactional
public class ObatServiceImpl implements ObatService {

    @Autowired
    ObatDb obatDb;

    @Override
    public List<Obat> getListObat() {
=======
public class ObatServiceImpl implements ObatService{
    @Autowired
    private ObatDb obatDb;

    @Override
    public List<Obat> findAll() {
>>>>>>> ad8aae416ca2465b83a8c85608ba3dd9a1ac4682
        return obatDb.findAll();
    }

    @Override
<<<<<<< HEAD
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
=======
    public Optional<Obat> findByIdObat(String idObat) {
        return obatDb.findByIdObat(idObat);
>>>>>>> ad8aae416ca2465b83a8c85608ba3dd9a1ac4682
    }
}
