package apap.proyek.rumahsehat.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @Size(max = 255)
    @Column(name = "kode", nullable = false)
    private String id;

    @Column(name = "waktuAwal")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime waktuAwal;

    @Column(name = "isDone")
    private Boolean isDone;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pasien", nullable = false, referencedColumnName = "uuid")
    private Pasien pasien;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dokter", nullable = false, referencedColumnName = "uuid")
    private Dokter dokter;

    @OneToOne(mappedBy = "kodeAppointment")
    private Tagihan tagihan;

    @OneToOne(mappedBy = "kodeAppointment")
    private Resep resep;

}