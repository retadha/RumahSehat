package apap.proyek.rumahsehat.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "apoteker")
public class Apoteker {
    @Id
    @Column(name = "uuid")
    private String uuid;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uuid", nullable = false)
    private UserModel user;

    @OneToMany(mappedBy = "confirmerUuid")
    private List<Resep> listResep;



}