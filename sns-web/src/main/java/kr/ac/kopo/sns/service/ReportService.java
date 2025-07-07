package kr.ac.kopo.sns.service;

import kr.ac.kopo.sns.dao.ReportDAO;
import kr.ac.kopo.sns.vo.ReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 신고 비즈니스 로직 서비스
 * 인터페이스와 구현체를 하나로 통합한 클래스
 */
@Service
public class ReportService {
    
    @Autowired
    private ReportDAO reportDAO;
    
    /**
     * 신고 목록 조회
     * @return 신고 목록
     */
    public List<ReportVO> getReportList() {
        return reportDAO.selectReportList();
    }
    
    /**
     * 신고 등록
     * @param reportVO 신고 정보
     * @return 등록 성공 여부
     */
    public boolean createReport(ReportVO reportVO) {
        // 신고 사유 유효성 검사
        if (reportVO.getReason() == null || reportVO.getReason().trim().isEmpty()) {
            throw new IllegalArgumentException("신고 사유는 필수입니다.");
        }
        
        // 중복 신고 방지 로직 (같은 사용자가 같은 게시글을 중복 신고하는 것 방지)
        List<ReportVO> existingReports = reportDAO.selectReportsByPostId(reportVO.getPostId());
        for (ReportVO existing : existingReports) {
            if (existing.getUserId().equals(reportVO.getUserId())) {
                throw new IllegalStateException("이미 신고한 게시글입니다.");
            }
        }
        
        int result = reportDAO.insertReport(reportVO);
        return result > 0;
    }
    
    /**
     * 신고 상세 조회
     * @param reportId 신고 ID
     * @return 신고 정보
     */
    public ReportVO getReportDetail(Long reportId) {
        if (reportId == null) {
            throw new IllegalArgumentException("신고 ID는 필수입니다.");
        }
        return reportDAO.selectReportById(reportId);
    }
    
    /**
     * 신고 처리 완료 (삭제)
     * @param reportId 신고 ID
     * @return 처리 성공 여부
     */
    public boolean processReport(Long reportId) {
        if (reportId == null) {
            throw new IllegalArgumentException("신고 ID는 필수입니다.");
        }
        
        int result = reportDAO.deleteReport(reportId);
        return result > 0;
    }
    
    /**
     * 사용자별 신고 내역 조회
     * @param userId 사용자 ID
     * @return 신고 목록
     */
    public List<ReportVO> getReportsByUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("사용자 ID는 필수입니다.");
        }
        return reportDAO.selectReportsByUserId(userId);
    }
    
    /**
     * 게시글별 신고 내역 조회
     * @param postId 게시글 ID
     * @return 신고 목록
     */
    public List<ReportVO> getReportsByPost(Long postId) {
        if (postId == null) {
            throw new IllegalArgumentException("게시글 ID는 필수입니다.");
        }
        return reportDAO.selectReportsByPostId(postId);
    }
}
