package kr.ac.kopo.sns.vo;

import lombok.Data;
import java.util.Date;

@Data
public class UserVO {
    private Long userId;
    private String email;
    private String password;
    private String nickname;
    private String profileImg;
    private String bio;
    private String role;
    private String status;
    private String isDeleted;
    private Date createdAt;
    private Date updatedAt;
    private Date lastLoginAt;
    
    // 통계 정보
    private Integer postsCount;
    private Integer followersCount;
    private Integer followingCount;

    // 기본값 설정 메서드
    public void setDefaults() {
        if (this.role == null) this.role = "USER";
        if (this.status == null) this.status = "ACTIVE";
        if (this.isDeleted == null) this.isDeleted = "N";
        if (this.postsCount == null) this.postsCount = 0;
        if (this.followersCount == null) this.followersCount = 0;
        if (this.followingCount == null) this.followingCount = 0;
    }

    // 프로필 이미지 기본값 처리
    public String getProfileImgUrl() {
        return profileImg != null ? profileImg : "/resources/images/default-avatar.jpg";
    }

    // 닉네임 기본값 처리
    public String getDisplayName() {
        return nickname != null ? nickname : "사용자" + userId;
    }

    // 계정 유효성 확인
    public boolean isActiveAccount() {
        return "ACTIVE".equals(status) && "N".equals(isDeleted);
    }
}
