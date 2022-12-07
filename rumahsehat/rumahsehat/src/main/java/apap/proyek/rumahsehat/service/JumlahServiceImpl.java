package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Jumlah;
<<<<<<< HEAD
import apap.proyek.rumahsehat.model.Resep;
import apap.proyek.rumahsehat.repository.JumlahDb;
import apap.proyek.rumahsehat.repository.ResepDb;
=======
import apap.proyek.rumahsehat.model.Obat;
import apap.proyek.rumahsehat.repository.JumlahDb;
import apap.proyek.rumahsehat.repository.ObatDb;
>>>>>>> 97e64667e59d1c234ed99cb4e63d0517810a2f4c
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JumlahServiceImpl implements JumlahService{
    @Autowired
    JumlahDb jumlahDb;

<<<<<<< HEAD
=======
    @Autowired
    ObatDb obatDb;

>>>>>>> 97e64667e59d1c234ed99cb4e63d0517810a2f4c
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
<<<<<<< HEAD
    public int calculatePrice(Long idResep) {
        List<Jumlah> listObatResep = findByResep(idResep);
        int total = 0;
        for (Jumlah jumlah: listObatResep){
            total += jumlah.getKuantitas() * jumlah.getObat().getHarga();
        }
        return total;
    }

    @Override
    public Jumlah addJumlah(Jumlah jumlah) {
        return jumlahDb.save(jumlah);
    }
=======
    public void minusStock(Long idResep) {
        List<Jumlah> jumlahList = jumlahDb.findJumlahByResep(idResep);
        for (Jumlah jumlah: jumlahList){
            Obat obat = jumlah.getObat();
           obat.setStok(obat.getStok()-jumlah.getKuantitas());
           obatDb.save(obat);
        }
    }

>>>>>>> 97e64667e59d1c234ed99cb4e63d0517810a2f4c
}
