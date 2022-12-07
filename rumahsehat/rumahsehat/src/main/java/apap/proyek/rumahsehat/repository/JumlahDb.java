package apap.proyek.rumahsehat.repository;

import apap.proyek.rumahsehat.model.Jumlah;
import apap.proyek.rumahsehat.model.JumlahId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JumlahDb extends JpaRepository<Jumlah, JumlahId> {
    @Query("SELECT DISTINCT j FROM Jumlah j WHERE j.resep.id = :idResep")
    List<Jumlah> findJumlahByResep(@Param("idResep") Long idResep);


}
<<<<<<< HEAD
=======

>>>>>>> 97e64667e59d1c234ed99cb4e63d0517810a2f4c
