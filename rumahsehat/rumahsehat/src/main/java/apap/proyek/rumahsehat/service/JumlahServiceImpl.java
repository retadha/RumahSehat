package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Jumlah;
import apap.proyek.rumahsehat.model.Obat;
import apap.proyek.rumahsehat.repository.JumlahDb;
import apap.proyek.rumahsehat.repository.ObatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JumlahServiceImpl implements JumlahService{
    @Autowired
    JumlahDb jumlahDb;

    @Autowired
    ObatDb obatDb;

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

    @Override
    public void minusStock(Long idResep) {
        List<Jumlah> jumlahList = jumlahDb.findJumlahByResep(idResep);
        for (Jumlah jumlah: jumlahList){
            Obat obat = jumlah.getObat();
           obat.setStok(obat.getStok()-jumlah.getKuantitas());
           obatDb.save(obat);
        }
    }

}
