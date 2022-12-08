package apap.proyek.rumahsehat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "resep")
public class Resep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "isDone")
    private Boolean isDone;

    @Column(name = "createdAt")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "confirmer_uuid", referencedColumnName = "uuid")
    private Apoteker confirmerUuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kodeAppointment")
    private Appointment kodeAppointment;


    @OneToMany(mappedBy = "resep", cascade = CascadeType.REMOVE)
    private List<Jumlah> listJumlah;
}

