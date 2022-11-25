package apap.proyek.rumahsehat.service;

import apap.proyek.rumahsehat.model.Admin;
import apap.proyek.rumahsehat.model.Apoteker;
import apap.proyek.rumahsehat.model.UserModel;
import apap.proyek.rumahsehat.repository.AdminDb;
import apap.proyek.rumahsehat.repository.ApotekerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDb adminDb;

    @Override
    public Admin addAdmin(Admin admin, UserModel user) {
        admin.setUser(user);
        return adminDb.save(admin);
    }

    @Override
    public List<Admin> findAll() {
        return adminDb.findAll();
    }
}
