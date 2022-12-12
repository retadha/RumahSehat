package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Resep;
import apap.proyek.rumahsehat.repository.ResepDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ResepServiceImpl implements ResepService {
    @Autowired
    ResepDb resepDb;

    @Override
    public Resep getResepById(Long idResep) {
        Optional<Resep> resep = resepDb.findById(idResep);
        if (resep.isPresent()){
            return resep.get();
        }
        else {
            throw new NoSuchElementException();
        }
    }

//    @Override
//    public void saveResep(Resep resep) {
//        resepDb.save(resep);
//    }

    @Override
    public Resep saveResep(Resep resep) {
        return resepDb.save(resep);
    }

    @Override
    public List<Resep> findAllResep() {
        return resepDb.findAll();
    }
}
