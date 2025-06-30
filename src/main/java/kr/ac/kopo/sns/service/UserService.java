package kr.ac.kopo.sns.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.ac.kopo.sns.dao.UserDAO;
import kr.ac.kopo.sns.vo.UserVO;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 로그인
    public UserVO login(String email, String password) {
        try {
            UserVO user = userDAO.selectUserByEmail(email);
            
            if (user != null && "ACTIVE".equals(user.getStatus()) && 
                "N".equals(user.getIsDeleted())) {
                
                // 비밀번호 확인
                if (user.getPassword() != null && 
                    passwordEncoder.matches(password, user.getPassword())) {
                    return user;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 회원가입
    @Transactional
    public boolean registerUser(UserVO user) {
        try {
            // 비밀번호 암호화
            if (user.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            
            // 기본값 설정
            user.setRole("USER");
            user.setStatus("ACTIVE");
            user.setIsDeleted("N");
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());
            
            return userDAO.insertUser(user) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 이메일 중복 체크
    public boolean isEmailExists(String email) {
        try {
            UserVO user = userDAO.selectUserByEmail(email);
            return user != null && "N".equals(user.getIsDeleted());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 닉네임 중복 체크
    public boolean isNicknameExists(String nickname) {
        try {
            return userDAO.countByNickname(nickname) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 사용자 ID로 조회
    public UserVO getUserById(Long userId) {
        try {
            return userDAO.selectUserById(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 마지막 로그인 시간 업데이트
    public void updateLastLoginTime(Long userId) {
        try {
            userDAO.updateLastLoginTime(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 모든 사용자 조회
    public List<UserVO> getAllUsers() {
        try {
            return userDAO.selectAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 비밀번호 변경
    @Transactional
    public boolean changePassword(Long userId, String currentPassword, String newPassword) {
        try {
            UserVO user = userDAO.selectUserById(userId);
            
            if (user != null && user.getPassword() != null &&
                passwordEncoder.matches(currentPassword, user.getPassword())) {
                
                String encodedNewPassword = passwordEncoder.encode(newPassword);
                return userDAO.updatePassword(userId, encodedNewPassword) > 0;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
