package kr.ac.kopo.sns.service;

import kr.ac.kopo.sns.dao.UserDAO;
import kr.ac.kopo.sns.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.Objects;
import java.util.List;

@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDAO userDAO;

    // ★★ BCryptPasswordEncoder 관련 코드 모두 제거 ★★
    // @Autowired
    // private BCryptPasswordEncoder passwordEncoder;

    /**
     * 회원가입 처리 (암호화 없음)
     */
    public boolean registerUser(UserVO userVO) {
        // 이메일 중복 체크
        if (userDAO.existsByEmail(userVO.getEmail())) {
            logger.warn("이메일 중복: {}", userVO.getEmail());
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 닉네임 중복 체크
        if (userDAO.existsByNickname(userVO.getNickname())) {
            logger.warn("닉네임 중복: {}", userVO.getNickname());
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        
        // ★★ 비밀번호 암호화 로직 제거 ★★
        // 비밀번호를 그대로 DB에 저장합니다.
        
        // 기본값 설정
        if (userVO.getRole() == null) userVO.setRole("USER");
        if (userVO.getStatus() == null) userVO.setStatus("ACTIVE");
        userVO.setCreatedAt(new Date());

        int result = userDAO.insertUser(userVO);
        if (result > 0) {
            logger.info("회원가입 성공: {}", userVO.getEmail());
            return true;
        }
        return false;
    }

    /**
     * 로그인 처리 (암호화 없음)
     */
    public UserVO loginUser(String email, String password) {
        UserVO user = userDAO.selectUserByEmail(email);

        if (user == null) {
            logger.warn("존재하지 않는 이메일로 로그인 시도: {}", email);
            return null;
        }

        if (!"ACTIVE".equals(user.getStatus())) {
            logger.warn("비활성 계정으로 로그인 시도: {} (상태: {})", email, user.getStatus());
            return null;
        }

        // ★★ 비밀번호 비교 로직 변경: 단순 문자열 비교 ★★
        if (Objects.equals(password, user.getPassword())) {
            logger.info("로그인 성공: {}", email);
            userDAO.updateLastLoginTime(user.getUserId());
            user.setPassword(null); // 보안을 위해 세션에 담기 전 비밀번호 제거
            return user;
        } else {
            logger.warn("잘못된 비밀번호로 로그인 시도: {}", email);
            return null;
        }
    }

    // --- 이하 다른 메서드들은 참고용으로 구조만 바로잡았습니다. ---
    @Transactional(readOnly = true)
    public UserVO getUserById(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("사용자 ID는 null일 수 없습니다.");
        }
        
        UserVO user = userDAO.selectUserById(userId);
        if (user != null) {
            user.setPassword(null); // 보안을 위해 비밀번호 제거
        }
        
        return user;
    }
    @Transactional(readOnly = true)
    public boolean isEmailExists(String email) {
        return userDAO.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean isNicknameExists(String nickname) {
        return userDAO.existsByNickname(nickname);
    }
    
    // ... 기타 필요한 메서드들 ...
}
