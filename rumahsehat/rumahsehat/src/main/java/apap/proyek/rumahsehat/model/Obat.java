package apap.proyek.rumahsehat.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "obat")
public class Obat {
    @Id
    @Size(max = 255)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_obat", nullable = false)
    private String id;

    @Size(max = 255)
    @Column(name = "nama_obat")
    private String namaObat;

    @Column(name = "stok")
    private Integer stok;

    @Column(name = "harga")
    private Integer harga;



}