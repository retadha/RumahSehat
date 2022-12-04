package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Tagihan;

import java.util.List;

public interface TagihanService {
    List<Tagihan> getListTagihan();
    void saveTagihan(Tagihan tagihan);
}
