package carrotmoa.carrotmoa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "account", schema = "carrot_moa")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Size(max = 10)
    @NotNull
    @Column(name = "bank_name", nullable = false, length = 10)
    private String bankName;

    @NotNull
    @Column(name = "account", nullable = false)
    private Integer account;

    @Size(max = 20)
    @NotNull
    @Column(name = "account_holder", nullable = false, length = 20)
    private String accountHolder;

    @NotNull
    @ColumnDefault("(curtime())")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @ColumnDefault("(curtime())")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}