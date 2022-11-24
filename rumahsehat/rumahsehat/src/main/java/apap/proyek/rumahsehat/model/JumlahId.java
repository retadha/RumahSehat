package apap.proyek.rumahsehat.model;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JumlahId implements Serializable {
    private static final long serialVersionUID = -6716394384553641255L;

    @Size(max = 255)
    @NotNull
    @Column(name = "obat", nullable = false)
    private String obat;

    @NotNull
    @Column(name = "resep", nullable = false)
    private Long resep;

    public String getObat() {
        return obat;
    }

    public void setObat(String obat) {
        this.obat = obat;
    }

    public Long getResep() {
        return resep;
    }

    public void setResep(Long resep) {
        this.resep = resep;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        JumlahId entity = (JumlahId) o;
//        return Objects.equals(this.obat, entity.obat) &&
//                Objects.equals(this.resep, entity.resep);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(obat, resep);
//    }

}