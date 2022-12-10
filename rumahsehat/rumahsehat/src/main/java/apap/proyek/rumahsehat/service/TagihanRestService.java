package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.TagihanDto;

import java.util.List;
import java.util.Map;

public interface TagihanRestService {
    Map<String, List<TagihanDto>> getListTagihan(String uuid);
    TagihanDto getDetailTagihan(String kode);
    Map bayarTagihan(String kode);
}
