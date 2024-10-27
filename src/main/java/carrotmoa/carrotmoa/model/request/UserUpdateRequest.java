package carrotmoa.carrotmoa.model.request;

import carrotmoa.carrotmoa.config.security.CustomUserDetails;
import carrotmoa.carrotmoa.entity.User;
import lombok.Data;

@Data

public class UserUpdateRequest {
    private long userId;
    private String nickname;
    private String name;
    private String phoneNumber;
    private String bio;
    public UserUpdateRequest(CustomUserDetails user){
        this.userId = user.getUserProfile().getUserId();
        this.nickname = user.getUsername();
        this.name = user.getUserProfile().getName();
        this.phoneNumber = user.getUserProfile().getPhoneNumber();
        this.bio = user.getUserProfile().getBio();
    }
}
