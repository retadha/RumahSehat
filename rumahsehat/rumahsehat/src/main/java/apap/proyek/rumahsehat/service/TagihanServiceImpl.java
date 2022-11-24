package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Tagihan;
import apap.proyek.rumahsehat.repository.TagihanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagihanServiceImpl implements TagihanService{
    @Autowired
    TagihanDb tagihanDb;

    @Override
    public void saveTagihan(Tagihan tagihan) {
        tagihanDb.save(tagihan);
    }
}
