package apap.proyek.rumahsehat.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1081427352141694546L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Size(max = 50)
    @Column(name = "nama")
    private String nama;

    @Size(max = 50)
    @Column(name = "role", length = 50)
    private String role;

    @Size(max = 50)
    @Column(name = "username")
    private String username;

    @Size(max = 50)
    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "password")
    private String password;

}