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
@Table(name = "tagihan")
public class Tagihan {
    @Id
    @Size(max = 255)
    @Column(name = "kode", nullable = false)
    private String kode;

    @Column(name = "tanggalTerbuat")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalTerbuat;

    @Column(name = "tanggalBayar")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalBayar;

    @Column(name = "isPaid")
    private Boolean isPaid;

    @Column(name = "jumlahTagihan")
    private Integer jumlahTagihan;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kode_appointment", nullable = false)
    private Appointment kodeAppointment;



}