package carrotmoa.carrotmoa.repository;

import carrotmoa.carrotmoa.entity.User;
import carrotmoa.carrotmoa.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatFindUserResponse {
    private long userId;
    private String nickName;

public ChatFindUserResponse(UserProfile profile){
    this.userId = profile.getUserId();
    this.nickName = profile.getNickname();
}

}
