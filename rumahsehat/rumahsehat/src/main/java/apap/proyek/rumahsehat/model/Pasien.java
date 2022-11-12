package apap.proyek.rumahsehat.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pasien")
public class Pasien {
    @Id
    @Column(name = "uuid")
    private String uuid;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uuid", nullable = false)
    private User user;

    @Column(name = "saldo")
    private Integer saldo;

    @Column(name = "umur")
    private Integer umur;

    @OneToMany(mappedBy = "pasien")
    private List<Appointment> listAppointment;




}