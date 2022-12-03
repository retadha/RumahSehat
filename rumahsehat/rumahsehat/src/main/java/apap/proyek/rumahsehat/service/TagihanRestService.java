package apap.proyek.rumahsehat.service;

import java.util.Map;

public interface TagihanRestService {
    Map getListTagihan(String username);
    Map getDetailTagihan(String kode);
}
