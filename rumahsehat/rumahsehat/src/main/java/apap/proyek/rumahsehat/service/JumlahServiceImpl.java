package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Jumlah;
import apap.proyek.rumahsehat.model.Resep;
import apap.proyek.rumahsehat.repository.JumlahDb;
import apap.proyek.rumahsehat.repository.ResepDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JumlahServiceImpl implements JumlahService{
    @Autowired
    JumlahDb jumlahDb;

    @Override
    public List<Jumlah> findByResep(Long idResep) {
        List<Jumlah> jumlah = jumlahDb.findJumlahByResep(idResep);
        return jumlah;
    }

    @Override
    public boolean checkStok(Long idResep) {
        List<Jumlah> jumlahList = jumlahDb.findJumlahByResep(idResep);
        for (Jumlah jumlah: jumlahList){
            if (jumlah.getObat().getStok() < jumlah.getKuantitas()){
                return false;
            }
        }
        return true;
    }
}
