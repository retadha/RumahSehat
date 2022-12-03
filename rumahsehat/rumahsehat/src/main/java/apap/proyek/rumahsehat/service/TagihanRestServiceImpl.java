package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Tagihan;
import apap.proyek.rumahsehat.repository.TagihanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class TagihanRestServiceImpl implements TagihanRestService{
    @Autowired
    TagihanDb tagihanDb;

    @Override
    public Map getListTagihan(String username) {
        List<Tagihan> listTagihan = tagihanDb.findByUsername(username);
        Map<String, Object> map = new HashMap<>();
        List<Object> list = new ArrayList<>();
        for(Tagihan tagihan: listTagihan){
            Map<String, Object> map2 = new HashMap<>();
            map2.put("kode", tagihan.getKode());
            map2.put("tanggalDibuat", tagihan.getTanggalTerbuat());
            map2.put("status", tagihan.getIsPaid());
            map2.put("tanggalBayar", tagihan.getTanggalBayar());
            map2.put("jumlahTagihan", tagihan.getJumlahTagihan());
            map2.put("appointment", tagihan.getKodeAppointment().getId());
            list.add(map2);
        }
        map.put("tagihan", list);
        return map;
    }

    @Override
    public Map getDetailTagihan(String kode) {
        Optional<Tagihan> tagihanOptional = tagihanDb.findById(kode);
        Tagihan tagihan = null;
        if (tagihanOptional.isPresent()){
            tagihan = tagihanOptional.get();
        } else {
            throw new NoSuchElementException();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("kode", tagihan.getKode());
        map.put("tanggalDibuat", tagihan.getTanggalTerbuat());
        map.put("status", tagihan.getIsPaid());
        map.put("tanggalBayar", tagihan.getTanggalBayar());
        map.put("jumlahTagihan", tagihan.getJumlahTagihan());
        map.put("appointment", tagihan.getKodeAppointment().getId());
        return map;
    }
}
