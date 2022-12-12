package apap.proyek.rumahsehat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "jumlah")
public class Jumlah {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private JumlahId id;

    @MapsId("obat")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "obat", nullable = false)
    private Obat obat;

    @MapsId("resep")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resep", nullable = false)
    private Resep resep;

    @Column(name = "kuantitas")
    private Integer kuantitas;
}