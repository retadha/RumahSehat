package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Jumlah;
import apap.proyek.rumahsehat.model.ObatDto;
import apap.proyek.rumahsehat.model.Resep;
import apap.proyek.rumahsehat.model.ResepDto;
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
    public ResepDto getResepById(Long idResep) {
        Optional<Resep> resep = resepDb.findById(idResep);
        Resep selectedResep = null;
        if (resep.isPresent()){
            selectedResep = resep.get();
        } else {
            throw new NoSuchElementException();
        }

        ResepDto resepDto = new ResepDto();
        resepDto.setId(selectedResep.getId());
        resepDto.setDokter(selectedResep.getKodeAppointment().getDokter().getUser().getNama());
        resepDto.setPasien(selectedResep.getKodeAppointment().getPasien().getUser().getNama());
        resepDto.setStatus(selectedResep.getIsDone());
        if(selectedResep.getConfirmerUuid()== null){
            resepDto.setApoteker(null);
        } else {
            resepDto.setApoteker(selectedResep.getConfirmerUuid().getUser().getNama());
        }

        List<Jumlah> jumlah = jumlahService.findByResep(idResep);

        ArrayList<ObatDto> listObat = new ArrayList<>();
        for(Jumlah j: jumlah){
            ObatDto obatDto = new ObatDto();
            obatDto.setNamaObat(j.getObat().getNamaObat());
            obatDto.setKuantitas(j.getKuantitas());
            listObat.add(obatDto);
        }

        resepDto.setListObat(listObat);
        return resepDto;
    }
}
