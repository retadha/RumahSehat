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
}