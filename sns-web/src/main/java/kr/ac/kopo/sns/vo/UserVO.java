package kr.ac.kopo.sns.vo;

import lombok.Data;
import java.util.Date;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor 포함
public class UserVO {

    private Long userId;
    private String email;
    private String password;
    private String nickname;
    private String profileImg; // 실제 프로필 이미지 경로 필드
    private String role = "USER";
    private String provider;
    private String providerId;
    private String status = "ACTIVE";
    private Date createdAt;
    private Date updatedAt;

    // JSP에서 ${user.active} 형태로 호출 가능
    public boolean isActive() {
        return "ACTIVE".equals(this.status);
    }

    // JSP에서 ${user.admin} 형태로 호출 가능
    public boolean isAdmin() {
        return "ADMIN".equals(this.role);
    }
}
