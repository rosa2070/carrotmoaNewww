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
@Table(name = "host_additional_form", schema = "carrot_moa")
public class HostAdditionalForm {
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