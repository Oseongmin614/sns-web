package kr.ac.kopo.sns.vo;

import java.util.Date;

/**
 * 신고 정보를 담는 Value Object
 */
public class ReportVO {
    
    private Long reportId;      // 신고 ID (PK)
    private Long postId;        // 신고 대상 게시글 ID (FK)
    private Long userId;        // 신고자 ID (FK)
    private String reason;      // 신고 사유
    private Date createdAt;     // 신고일시
    
    // 기본 생성자
    public ReportVO() {}
    
    // 매개변수가 있는 생성자
    public ReportVO(Long postId, Long userId, String reason) {
        this.postId = postId;
        this.userId = userId;
        this.reason = reason;
    }
    
    // Getter & Setter
    public Long getReportId() {
        return reportId;
    }
    
    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }
    
    public Long getPostId() {
        return postId;
    }
    
    public void setPostId(Long postId) {
        this.postId = postId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "ReportVO{" +
                "reportId=" + reportId +
                ", postId=" + postId +
                ", userId=" + userId +
                ", reason='" + reason + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}