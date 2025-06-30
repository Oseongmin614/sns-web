package kr.ac.kopo.sns.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.ac.kopo.sns.service.ReportService;
import kr.ac.kopo.sns.vo.ReportVO;
import kr.ac.kopo.sns.vo.UserVO;

@Controller
@RequestMapping("/report")
public class ReportController {
    
    @Autowired
    private ReportService reportService;
    
    @PostMapping("/create")
    @ResponseBody
    public String createReport(ReportVO report, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "unauthorized";
        }
        
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "unauthorized";
        }
        
        report.setReporterId(loginUser.getUserId());
        boolean result = reportService.createReport(report);
        return result ? "success" : "fail";
    }
    
    @GetMapping("/admin/list")
    public String adminReportList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/user/login";
        }
        
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null || !"ADMIN".equals(loginUser.getRole())) {
            return "redirect:/user/login";
        }
        
        List<ReportVO> reports = reportService.getAllReports();
        model.addAttribute("reports", reports);
        return "admin/reports";
    }
    
    @GetMapping("/admin/pending")
    public String adminPendingReports(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/user/login";
        }
        
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null || !"ADMIN".equals(loginUser.getRole())) {
            return "redirect:/user/login";
        }
        
        List<ReportVO> reports = reportService.getPendingReports();
        model.addAttribute("reports", reports);
        return "admin/pendingReports";
    }
    
    @PostMapping("/admin/handle")
    @ResponseBody
    public String handleReport(@RequestParam Long reportId, @RequestParam String status, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "unauthorized";
        }
        
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null || !"ADMIN".equals(loginUser.getRole())) {
            return "unauthorized";
        }
        
        ReportVO report = new ReportVO();
        report.setReportId(reportId);
        report.setStatus(status);
        report.setHandledBy(loginUser.getUserId());
        
        boolean result = reportService.handleReport(report);
        return result ? "success" : "fail";
    }
}
