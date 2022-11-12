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
@Table(name = "pasien")
public class Pasien {
    @Id
    @Column(name = "uuid")
    private String uuid;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uuid", nullable = false)
    private UserModel user;

    @Column(name = "saldo")
    private Integer saldo;

    @Column(name = "umur")
    private Integer umur;

    @OneToMany(mappedBy = "pasien")
    private List<Appointment> listAppointment;




}