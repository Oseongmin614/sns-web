package kr.ac.kopo.sns.dao;

import kr.ac.kopo.sns.vo.ReportVO;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 신고 관련 데이터 접근 인터페이스
 */
@Mapper
public interface ReportDAO {

	/**
	 * 신고 목록 조회
	 * 
	 * @return 신고 목록
	 */
	List<ReportVO> selectReportList();

	/**
	 * 신고 등록
	 * 
	 * @param reportVO 신고 정보
	 * @return 등록된 행 수
	 */
	int insertReport(ReportVO reportVO);

	/**
	 * 특정 신고 조회
	 * 
	 * @param reportId 신고 ID
	 * @return 신고 정보
	 */
	ReportVO selectReportById(Long reportId);

	/**
	 * 신고 삭제 (처리 완료)
	 * 
	 * @param reportId 신고 ID
	 * @return 삭제된 행 수
	 */
	int deleteReport(Long reportId);

	/**
	 * 사용자별 신고 내역 조회
	 * 
	 * @param userId 사용자 ID
	 * @return 신고 목록
	 */
	List<ReportVO> selectReportsByUserId(Long userId);

	/**
	 * 게시글별 신고 내역 조회
	 * 
	 * @param postId 게시글 ID
	 * @return 신고 목록
	 */
	List<ReportVO> selectReportsByPostId(Long postId);
}
