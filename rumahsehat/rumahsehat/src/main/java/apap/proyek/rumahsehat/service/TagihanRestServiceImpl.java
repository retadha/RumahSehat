package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Tagihan;
import apap.proyek.rumahsehat.repository.TagihanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            map2.put("tanggal", tagihan.getTanggalTerbuat());
            map2.put("status", tagihan.getIsPaid());
            list.add(map2);
        }
        map.put("tagihan", list);
        return map;
    }
}
