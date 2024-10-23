package carrotmoa.carrotmoa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "user", schema = "carrot_moa")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 30)
    @NotNull

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = true, length = 20)
    private String name;
    @Column(name = "is_withdrawal", nullable = false)
    private Boolean isWithdrawal;

    @Column(name = "authority_id", nullable = false)
    private Long authorityId;

    @ColumnDefault("(curtime())")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ColumnDefault("(curtime())")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
