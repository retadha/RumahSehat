package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Obat;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ObatDb extends JpaRepository<Obat, String> {
    Optional<Obat> findByIdObat(String idObat);
}
=======

import java.util.List;
import java.util.Optional;

public interface ObatDb extends JpaRepository<Obat, String> {
    @Override
    List<Obat> findAll();

    Optional<Obat> findByIdObat(String idObat);
}
>>>>>>> ad8aae416ca2465b83a8c85608ba3dd9a1ac4682
