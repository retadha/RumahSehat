package apap.proyek.rumahsehat.service;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import apap.proyek.rumahsehat.model.Pasien;
import apap.proyek.rumahsehat.model.Tagihan;
import apap.proyek.rumahsehat.repository.PasienDb;
import apap.proyek.rumahsehat.repository.TagihanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import java.time.format.DateTimeFormatter;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class TagihanRestServiceImpl implements TagihanRestService{
    @Autowired
    TagihanDb tagihanDb;

    @Autowired
    PasienDb pasienDb;
    @Autowired
    private JumlahService jumlahService;



    @Override
    public Map getListTagihan(String uuid) {
        List<Tagihan> listTagihan = tagihanDb.findByUuid(uuid);
        Map<String, Object> map = new HashMap<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

        List<Object> list = new ArrayList<>();
        for(Tagihan tagihan: listTagihan){
            Map<String, Object> map2 = new HashMap<>();
            map2.put("kode", tagihan.getKode());
            map2.put("tanggalDibuat", tagihan.getTanggalTerbuat().format(dateTimeFormatter));
            map2.put("status", tagihan.getIsPaid());
            if (tagihan.getTanggalBayar()!=null){
                map2.put("tanggalBayar", tagihan.getTanggalBayar().format(dateTimeFormatter));
            } else {
                map2.put("tanggalBayar", tagihan.getTanggalBayar());

            }
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
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        Map<String, Object> map = new HashMap<>();
        map.put("kode", tagihan.getKode());
        map.put("tanggalDibuat", tagihan.getTanggalTerbuat().format(dateTimeFormatter));
        map.put("status", tagihan.getIsPaid());
        if (tagihan.getTanggalBayar()!=null){
            map.put("tanggalBayar", tagihan.getTanggalBayar().format(dateTimeFormatter));
        } else {
            map.put("tanggalBayar", tagihan.getTanggalBayar());
        }
        map.put("jumlahTagihan", tagihan.getJumlahTagihan());
        map.put("appointment", tagihan.getKodeAppointment().getId());
        return map;
    }

    @Override
    public Map bayarTagihan(String kode) {
        Optional<Tagihan> tagihanOptional = tagihanDb.findById(kode);
        Tagihan tagihan = null;
        if (tagihanOptional.isPresent()){
            tagihan = tagihanOptional.get();
        } else {
            throw new NoSuchElementException();
        }
        Map<String, Object> map = new HashMap<>();
        Pasien pasien = tagihan.getKodeAppointment().getPasien();
        boolean stokCukup = jumlahService.checkStok(tagihan.getKodeAppointment().getResep().getId());
        if (pasien.getSaldo()< tagihan.getJumlahTagihan()){
            map.put("statusSaldo", "kurang");
        }else if (!stokCukup) {
            map.put("statusStok", "kurang");
        } else if (tagihan.getIsPaid()){
            map.put("statusTagihan", "lunas");
        }else {
            tagihan.setIsPaid(true);
            tagihan.setTanggalBayar(LocalDateTime.now());
            pasien.setSaldo(pasien.getSaldo()-tagihan.getJumlahTagihan());
            jumlahService.minusStock(tagihan.getKodeAppointment().getResep().getId());
            tagihanDb.save(tagihan);
            pasienDb.save(pasien);
            map.put("status", "berhasil");
        }
        return map;
    }
}
