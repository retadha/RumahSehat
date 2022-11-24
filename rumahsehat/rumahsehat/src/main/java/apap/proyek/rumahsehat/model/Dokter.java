package apap.proyek.rumahsehat.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dokter")
public class Dokter {
    @Id
    @Column(name = "uuid")
    private String uuid;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "uuid", nullable = false)
    private UserModel user;

    @Column(name = "tarif")
    private Integer tarif;

    @OneToMany(mappedBy = "dokter")
    private List<Appointment> listAppointment;




}