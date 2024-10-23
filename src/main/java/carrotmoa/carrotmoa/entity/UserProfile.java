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
import java.time.LocalDate;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "user_profile", schema = "carrot_moa")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Size(max = 10)
    @NotNull
    @Column(name = "nickname", nullable = false, length = 10)
    private String nickname;

    @Size(max = 255)
    @Column(name = "pic_url")
    private String picUrl;

    @Size(max = 100)
    @Column(name = "bio", length = 100)
    private String bio;

    @NotNull
    @ColumnDefault("(curtime())")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @ColumnDefault("(curtime())")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "address_id")
    private Long addressId;

    @Size(max = 255)
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birthday")
    private LocalDate birthday;

}