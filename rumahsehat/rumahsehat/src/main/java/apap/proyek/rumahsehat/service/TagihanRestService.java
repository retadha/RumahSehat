package apap.proyek.rumahsehat.service;

import java.util.Map;

public interface TagihanRestService {
    Map getListTagihan(String uuid);
    Map getDetailTagihan(String kode);
    Map bayarTagihan(String kode);
}
