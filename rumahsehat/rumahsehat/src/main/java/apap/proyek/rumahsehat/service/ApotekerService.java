package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Apoteker;
import apap.proyek.rumahsehat.model.Dokter;
import apap.proyek.rumahsehat.model.UserModel;

import java.util.List;

public interface ApotekerService {

    Apoteker addApoteker(Apoteker apoteker, UserModel user);

    void deleteApoteker(Apoteker apoteker);

    Apoteker getApotekerById(String id);

    List<Apoteker> findAll();
}
