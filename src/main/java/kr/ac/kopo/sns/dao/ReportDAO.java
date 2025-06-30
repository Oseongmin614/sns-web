package kr.ac.kopo.sns.dao;

import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import kr.ac.kopo.sns.vo.ReportVO;

@Repository
public class ReportDAO {
    
    @Autowired
    private SqlSessionTemplate sqlSession;
    
    private static final String NAMESPACE = "reportMapper.";
    
    public int insertReport(ReportVO report) {
        return sqlSession.insert(NAMESPACE + "insertReport", report);
    }
    
    public ReportVO selectReportById(Long reportId) {
        return sqlSession.selectOne(NAMESPACE + "selectReportById", reportId);
    }
    
    public List<ReportVO> selectAllReports() {
        return sqlSession.selectList(NAMESPACE + "selectAllReports");
    }
    
    public List<ReportVO> selectPendingReports() {
        return sqlSession.selectList(NAMESPACE + "selectPendingReports");
    }
    
    public int updateReportStatus(ReportVO report) {
        return sqlSession.update(NAMESPACE + "updateReportStatus", report);
    }
    
    public List<ReportVO> selectReportsByReporter(Long reporterId) {
        return sqlSession.selectList(NAMESPACE + "selectReportsByReporter", reporterId);
    }
}
