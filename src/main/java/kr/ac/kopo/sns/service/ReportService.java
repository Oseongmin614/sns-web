package kr.ac.kopo.sns.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.ac.kopo.sns.dao.ReportDAO;
import kr.ac.kopo.sns.vo.ReportVO;

@Service
public class ReportService {

	@Autowired
	private ReportDAO reportDAO;

	public boolean createReport(ReportVO report) {
		try {
			if (report.getStatus() == null) {
				report.setStatus("PENDING");
			}

			int result = reportDAO.insertReport(report);
			return result > 0;
		} catch (Exception e) {
			System.err.println("신고 등록 중 오류 발생: " + e.getMessage());
			return false;
		}
	}

	public ReportVO getReportById(Long reportId) {
		try {
			return reportDAO.selectReportById(reportId);
		} catch (Exception e) {
			System.err.println("신고 조회 중 오류 발생: " + e.getMessage());
			return null;
		}
	}

	public List<ReportVO> getAllReports() {
		try {
			return reportDAO.selectAllReports();
		} catch (Exception e) {
			System.err.println("전체 신고 조회 중 오류 발생: " + e.getMessage());
			return null;
		}
	}

	public List<ReportVO> getPendingReports() {
		try {
			return reportDAO.selectPendingReports();
		} catch (Exception e) {
			System.err.println("대기 중인 신고 조회 중 오류 발생: " + e.getMessage());
			return null;
		}
	}

	public boolean handleReport(ReportVO report) {
		try {
			int result = reportDAO.updateReportStatus(report);
			return result > 0;
		} catch (Exception e) {
			System.err.println("신고 처리 중 오류 발생: " + e.getMessage());
			return false;
		}
	}

	public List<ReportVO> getReportsByReporter(Long reporterId) {
		try {
			return reportDAO.selectReportsByReporter(reporterId);
		} catch (Exception e) {
			System.err.println("신고자별 신고 조회 중 오류 발생: " + e.getMessage());
			return null;
		}
	}
}
