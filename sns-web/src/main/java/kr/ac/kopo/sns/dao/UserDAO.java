package kr.ac.kopo.sns.dao;

import kr.ac.kopo.sns.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 사용자 관련 데이터 접근 객체 (DAO) 인터페이스
 * MyBatis 매퍼와 연동
 */
@Mapper
public interface UserDAO {
    
    /**
     * 사용자 등록 (회원가입)
     * @param user 등록할 사용자 정보
     * @return 등록된 행의 수
     */
    int insertUser(UserVO user);
    
    /**
     * 이메일로 사용자 조회 (로그인용)
     * @param email 이메일 주소
     * @return 사용자 정보, 없으면 null
     */
    UserVO selectUserByEmail(@Param("email") String email);
    
    /**
     * 사용자 ID로 사용자 조회
     * @param userId 사용자 ID
     * @return 사용자 정보, 없으면 null
     */
    UserVO selectUserById(@Param("userId") Long userId);
    
    /**
     * 닉네임으로 사용자 조회
     * @param nickname 닉네임
     * @return 사용자 정보, 없으면 null
     */
    UserVO selectUserByNickname(@Param("nickname") String nickname);
    
    /**
     * 이메일 중복 체크
     * @param email 확인할 이메일 주소
     * @return 중복되면 true, 아니면 false
     */
    boolean existsByEmail(@Param("email") String email);
    
    /**
     * 닉네임 중복 체크
     * @param nickname 확인할 닉네임
     * @return 중복되면 true, 아니면 false
     */
    boolean existsByNickname(@Param("nickname") String nickname);
    
    /**
     * 사용자 정보 업데이트
     * @param user 업데이트할 사용자 정보
     * @return 업데이트된 행의 수
     */
    int updateUser(UserVO user);
    
    /**
     * 비밀번호 변경
     * @param userId 사용자 ID
     * @param newPassword 새 비밀번호 (암호화된)
     * @return 업데이트된 행의 수
     */
    int updatePassword(@Param("userId") Long userId, @Param("password") String newPassword);
    
    /**
     * 프로필 이미지 업데이트
     * @param userId 사용자 ID
     * @param profileImg 프로필 이미지 URL
     * @return 업데이트된 행의 수
     */
    int updateProfileImage(@Param("userId") Long userId, @Param("profileImg") String profileImg);
    
    /**
     * 사용자 상태 변경
     * @param userId 사용자 ID
     * @param status 새로운 상태 (ACTIVE, INACTIVE, BANNED)
     * @return 업데이트된 행의 수
     */
    int updateUserStatus(@Param("userId") Long userId, @Param("status") String status);
    
    /**
     * 마지막 로그인 시간 업데이트
     * @param userId 사용자 ID
     * @return 업데이트된 행의 수
     */
    int updateLastLoginTime(@Param("userId") Long userId);
    
    /**
     * 사용자 삭제 (물리 삭제) 
     * @param userId 사용자 ID
     * @return 삭제된 행의 수 
     */
    int deleteUser(@Param("userId") Long userId);
    
    /**
     * 모든 사용자 조회 (관리자용)
     * @return 사용자 목록
     */
    List<UserVO> selectAllUsers();
    
    /**
     * 페이징을 사용한 사용자 조회
     * @param offset 시작 위치
     * @param limit 조회할 개수
     * @return 사용자 목록
     */
    List<UserVO> selectUsersWithPaging(@Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 전체 사용자 수 조회
     * @return 전체 사용자 수
     */
    int countTotalUsers();
    
    /**
     * 역할별 사용자 조회
     * @param role 사용자 역할 (USER, ADMIN)
     * @return 해당 역할의 사용자 목록
     */
    List<UserVO> selectUsersByRole(@Param("role") String role);
    
    /**
     * 상태별 사용자 조회
     * @param status 사용자 상태 (ACTIVE, INACTIVE, BANNED)
     * @return 해당 상태의 사용자 목록
     */
    List<UserVO> selectUsersByStatus(@Param("status") String status);
    
    /**
     * 검색어로 사용자 검색 (이메일 또는 닉네임)
     * @param keyword 검색 키워드
     * @return 검색된 사용자 목록
     */
    List<UserVO> searchUsers(@Param("keyword") String keyword);
    
    /**
     * 소셜 로그인 사용자 조회
     * @param provider 소셜 로그인 제공자
     * @param providerId 소셜 로그인 제공자 ID
     * @return 사용자 정보, 없으면 null
     */
    UserVO selectUserByProviderInfo(@Param("provider") String provider, 
                                    @Param("providerId") String providerId);
    
    /**
     * 소셜 로그인 정보 업데이트
     * @param userId 사용자 ID
     * @param provider 소셜 로그인 제공자
     * @param providerId 소셜 로그인 제공자 ID
     * @return 업데이트된 행의 수
     */
    int updateProviderInfo(@Param("userId") Long userId, 
                          @Param("provider") String provider, 
                          @Param("providerId") String providerId);
}