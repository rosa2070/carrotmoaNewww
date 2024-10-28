package carrotmoa.carrotmoa.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {
    LIKE(1),
    COMMENT(2),
    REPLY(3),
    WISHLIST(4);
    private final int id;
    public static NotificationType fromId(int id) {
        for (NotificationType type : NotificationType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid NotificationType id: " + id);
    }
}
