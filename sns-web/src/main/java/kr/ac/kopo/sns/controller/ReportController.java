package kr.ac.kopo.sns.controller;

import kr.ac.kopo.sns.service.ReportService;
import kr.ac.kopo.sns.vo.ReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 신고 관련 요청을 처리하는 컨트롤러
 */
@Controller
@RequestMapping("/report")
public class ReportController {
    
    @Autowired
    private ReportService reportService;
    
    /**
     * 신고 목록 페이지 (관리자용)
     */
    @GetMapping("/list")
    public String reportList(Model model) {
        List<ReportVO> reportList = reportService.getReportList();
        model.addAttribute("reportList", reportList);
        return "report/reportList";
    }
    
    /**
     * 신고 등록 (AJAX)
     */
    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<String> createReport(@RequestBody ReportVO reportVO) {
        boolean success = reportService.createReport(reportVO);
        if (success) {
            return ResponseEntity.ok("신고가 접수되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("신고 접수 중 오류가 발생했습니다.");
        }
    }
    
    /**
     * 신고 상세 조회
     */
    @GetMapping("/detail/{reportId}")
    public String reportDetail(@PathVariable Long reportId, Model model) {
        ReportVO report = reportService.getReportDetail(reportId);
        if (report != null) {
            model.addAttribute("report", report);
            return "report/reportDetail";
        } else {
            model.addAttribute("errorMessage", "해당 신고를 찾을 수 없습니다.");
            return "error/errorPage";
        }
    }
    
    /**
     * 신고 처리 완료 (삭제)
     */
    @PostMapping("/process/{reportId}")
    @ResponseBody
    public ResponseEntity<String> processReport(@PathVariable Long reportId) {
        boolean success = reportService.processReport(reportId);
        if (success) {
            return ResponseEntity.ok("신고가 처리되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("신고 처리 중 오류가 발생했습니다.");
        }
    }
    
    /**
     * 사용자별 신고 내역 조회 (API)
     */
    @GetMapping("/user/{userId}")
    @ResponseBody
    public ResponseEntity<List<ReportVO>> getReportsByUser(@PathVariable Long userId) {
        List<ReportVO> reports = reportService.getReportsByUser(userId);
        return ResponseEntity.ok(reports);
    }
    
    /**
     * 게시글별 신고 내역 조회 (API)
     */
    @GetMapping("/post/{postId}")
    @ResponseBody
    public ResponseEntity<List<ReportVO>> getReportsByPost(@PathVariable Long postId) {
        List<ReportVO> reports = reportService.getReportsByPost(postId);
        return ResponseEntity.ok(reports);
    }
}
