package apap.proyek.rumahsehat.service;


import apap.proyek.rumahsehat.model.TagihanDto;
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
    public Map<String, List<TagihanDto>> getListTagihan(String uuid) {
        List<Tagihan> listTagihan = tagihanDb.findByUuid(uuid);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        Map<String, List<TagihanDto>> map= new HashMap<>();
        List<TagihanDto> list = new ArrayList<>();
        for(Tagihan tagihan: listTagihan){
            TagihanDto tagihanDto = new TagihanDto();
            tagihanDto.setKode(tagihan.getKode());
            tagihanDto.setTanggalDibuat(tagihan.getTanggalTerbuat().format(dateTimeFormatter));
            tagihanDto.setStatus(tagihan.getIsPaid());
            if (tagihan.getTanggalBayar()!=null){
                tagihanDto.setTanggalBayar(tagihan.getTanggalBayar().format(dateTimeFormatter));
            } else {
                tagihanDto.setTanggalBayar(null);
            }
            tagihanDto.setJumlahTagihan(tagihan.getJumlahTagihan());
            tagihanDto.setAppointment(tagihan.getKodeAppointment().getId());
            list.add(tagihanDto);
        }
        map.put("tagihan", list);
        return map;
    }

    @Override
    public TagihanDto getDetailTagihan(String kode) {
        Optional<Tagihan> tagihanOptional = tagihanDb.findById(kode);
        Tagihan tagihan = null;
        if (tagihanOptional.isPresent()){
            tagihan = tagihanOptional.get();
        } else {
            throw new NoSuchElementException();
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        TagihanDto tagihanDto = new TagihanDto();
        tagihanDto.setKode(tagihan.getKode());
        tagihanDto.setTanggalDibuat(tagihan.getTanggalTerbuat().format(dateTimeFormatter));
        tagihanDto.setStatus(tagihan.getIsPaid());
        if (tagihan.getTanggalBayar()!=null){
            tagihanDto.setTanggalBayar(tagihan.getTanggalBayar().format(dateTimeFormatter));
        } else {
            tagihanDto.setTanggalBayar(null);
        }
        tagihanDto.setJumlahTagihan(tagihan.getJumlahTagihan());
        tagihanDto.setAppointment(tagihan.getKodeAppointment().getId());
        return tagihanDto;
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
