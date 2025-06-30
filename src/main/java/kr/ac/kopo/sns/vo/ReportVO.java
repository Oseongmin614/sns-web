package kr.ac.kopo.sns.vo;

import java.util.Date;

public class ReportVO {
	private Long reportId;
	private Long reporterId;
	private String targetType;
	private Long targetId;
	private String reason;
	private String detail;
	private String status;
	private Date createdAt;
	private Long handledBy;
	private Date handledAt;
	private String reporterNickname;
	private String handlerNickname;

	public ReportVO() {
	}

	public ReportVO(Long reporterId, String targetType, Long targetId, String reason) {
		this.reporterId = reporterId;
		this.targetType = targetType;
		this.targetId = targetId;
		this.reason = reason;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public Long getReporterId() {
		return reporterId;
	}

	public void setReporterId(Long reporterId) {
		this.reporterId = reporterId;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getHandledBy() {
		return handledBy;
	}

	public void setHandledBy(Long handledBy) {
		this.handledBy = handledBy;
	}

	public Date getHandledAt() {
		return handledAt;
	}

	public void setHandledAt(Date handledAt) {
		this.handledAt = handledAt;
	}

	public String getReporterNickname() {
		return reporterNickname;
	}

	public void setReporterNickname(String reporterNickname) {
		this.reporterNickname = reporterNickname;
	}

	public String getHandlerNickname() {
		return handlerNickname;
	}

	public void setHandlerNickname(String handlerNickname) {
		this.handlerNickname = handlerNickname;
	}
}
