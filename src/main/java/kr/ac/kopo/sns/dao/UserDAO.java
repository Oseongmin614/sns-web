package kr.ac.kopo.sns.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import kr.ac.kopo.sns.vo.UserVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDAO {

    @Autowired
    private SqlSessionTemplate sqlSession;

    // 이메일로 사용자 조회
    public UserVO selectUserByEmail(String email) {
        return sqlSession.selectOne("kr.ac.kopo.sns.dao.UserDAO.selectUserByEmail", email);
    }

    // ID로 사용자 조회
    public UserVO selectUserById(Long userId) {
        return sqlSession.selectOne("kr.ac.kopo.sns.dao.UserDAO.selectUserById", userId);
    }

    // 사용자 등록
    public int insertUser(UserVO user) {
        return sqlSession.insert("kr.ac.kopo.sns.dao.UserDAO.insertUser", user);
    }

    // 닉네임 중복 체크
    public int countByNickname(String nickname) {
        return sqlSession.selectOne("kr.ac.kopo.sns.dao.UserDAO.countByNickname", nickname);
    }

    // 마지막 로그인 시간 업데이트
    public int updateLastLoginTime(Long userId) {
        return sqlSession.update("kr.ac.kopo.sns.dao.UserDAO.updateLastLoginTime", userId);
    }

    // 비밀번호 변경
    public int updatePassword(Long userId, String newPassword) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("password", newPassword);
        return sqlSession.update("kr.ac.kopo.sns.dao.UserDAO.updatePassword", params);
    }

    // 모든 사용자 조회
    public List<UserVO> selectAllUsers() {
        return sqlSession.selectList("kr.ac.kopo.sns.dao.UserDAO.selectAllUsers");
    }
}
