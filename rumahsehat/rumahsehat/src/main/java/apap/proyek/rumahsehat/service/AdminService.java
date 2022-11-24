package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Admin;
import apap.proyek.rumahsehat.model.Apoteker;
import apap.proyek.rumahsehat.model.UserModel;

import java.util.List;

public interface AdminService {

    Admin addAdmin(Admin admin, UserModel user);

    List<Admin> findAll();
}
