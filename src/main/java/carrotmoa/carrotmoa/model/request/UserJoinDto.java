package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.entity.Account;
import carrotmoa.carrotmoa.entity.User;
import carrotmoa.carrotmoa.entity.UserProfile;
import lombok.Data;

import java.time.Instant;
@Data
public class UserJoinDto {
    private String email;
    private String password;
    private String nickname;
    private Long authorityId;
    private Boolean isWithdrawal = false;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
    private String bankName;
    private Integer account;
    private String accountHolder;
    private long userId;


    public User toUserEntity() {
        return User.builder()
                .email(this.getEmail())
                .password(this.getPassword())
                .authorityId(this.getAuthorityId())
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .isWithdrawal(this.getIsWithdrawal())
                .build();
    }

    public UserProfile toUserProfileEntity(long userId) {
        return UserProfile.builder()
                .userId(userId)
                .nickname(this.getNickname())
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }

    public Account toHostAdditionalFormEntity(long userId) {
        return carrotmoa.carrotmoa.entity.Account.builder()
                .userId(userId)
                .bankName(this.getBankName())
                .account(this.getAccount())
                .accountHolder(this.getAccountHolder())
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}

