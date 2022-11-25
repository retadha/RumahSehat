package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Jumlah;
import apap.proyek.rumahsehat.model.Resep;
import apap.proyek.rumahsehat.repository.ResepDb;
import apap.proyek.rumahsehat.setting.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ResepRestServiceImpl implements ResepRestService{
    @Autowired
    private ResepDb resepDb;

    @Qualifier("jumlahServiceImpl")
    @Autowired
    private JumlahService jumlahService;

    @Override
    public Map getResepById(Long idResep) {
        Optional<Resep> resep = resepDb.findById(idResep);
        Resep selectedResep = null;
        if (resep.isPresent()){
            selectedResep = resep.get();
        } else {
            throw new NoSuchElementException();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", selectedResep.getId());
        map.put("dokter", selectedResep.getKodeAppointment().getDokter().getUser().getNama());
        map.put("pasien", selectedResep.getKodeAppointment().getPasien().getUser().getNama());
        map.put("status", selectedResep.getIsDone());
        if(selectedResep.getConfirmerUuid()== null){
            map.put("apoteker", null);
        } else {
            map.put("apoteker", selectedResep.getConfirmerUuid().getUser().getNama());
        }

        List<Jumlah> jumlah = jumlahService.findByResep(idResep);
        ArrayList<Object> listObat = new ArrayList<>();
        for(Jumlah j: jumlah){
            Map<String, Object> map2 = new HashMap<>();
            map2.put("namaObat", j.getObat().getNamaObat());
            map2.put("kuantitas", j.getKuantitas());
            listObat.add(map2);
        }
        map.put("listObat", listObat);
        return map;
    }
}
