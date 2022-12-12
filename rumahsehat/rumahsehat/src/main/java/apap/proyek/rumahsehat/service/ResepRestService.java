package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Resep;
import apap.proyek.rumahsehat.model.ResepDto;

import java.util.Map;

public interface ResepRestService {
    ResepDto getResepById(Long idResep);
}
