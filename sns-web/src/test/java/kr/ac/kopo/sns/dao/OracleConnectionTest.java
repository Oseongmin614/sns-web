package kr.ac.kopo.sns.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Oracle 데이터베이스 연결 테스트 클래스
 * JUnit을 사용하여 데이터베이스 연결 및 기본 쿼리 테스트를 수행합니다.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class OracleConnectionTest {

    // JDBC 연결 정보
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "snsweb";
    private static final String PASSWORD = "1234";

    private Connection conn;

    /**
     * 테스트 실행 전 데이터베이스 연결 설정
     */
    @Before
    public void setUp() throws Exception {
        // 드라이버 로딩
        Class.forName(DRIVER);
        // 데이터베이스 연결
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
        assertNotNull("데이터베이스 연결이 null입니다.", conn);
        assertFalse("데이터베이스 연결이 닫혀있습니다.", conn.isClosed());
    }

    /**
     * 테스트 실행 후 데이터베이스 연결 해제
     */
    @After
    public void tearDown() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    /**
     * Oracle 드라이버 로딩 테스트
     */
    @Test
    public void testDriverLoading() {
        try {
            Class.forName(DRIVER);
            System.out.println("✓ Oracle 드라이버 로딩 성공");
        } catch (ClassNotFoundException e) {
            fail("Oracle 드라이버 로딩 실패: " + e.getMessage());
        }
    }

    /**
     * 데이터베이스 연결 테스트
     */
    @Test
    public void testDatabaseConnection() {
        assertNotNull("데이터베이스 연결 객체가 null입니다.", conn);
        
        try {
            assertFalse("데이터베이스 연결이 닫혀있습니다.", conn.isClosed());
            assertTrue("데이터베이스 연결이 유효하지 않습니다.", conn.isValid(5));
            System.out.println("✓ Oracle 데이터베이스 연결 성공");
        } catch (SQLException e) {
            fail("데이터베이스 연결 테스트 실패: " + e.getMessage());
        }
    }

    /**
     * USERS 테이블 존재 여부 테스트
     */
    @Test
    public void testUsersTableExists() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT COUNT(*) as table_count FROM user_tables WHERE table_name = 'USERS'";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            assertTrue("결과가 존재하지 않습니다.", rs.next());
            int tableCount = rs.getInt("table_count");
            assertEquals("USERS 테이블이 존재하지 않습니다.", 1, tableCount);
            System.out.println("✓ USERS 테이블 존재 확인");

        } catch (SQLException e) {
            fail("USERS 테이블 존재 확인 실패: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }
    }

    /**
     * USERS 테이블 컬럼 구조 테스트
     */
    @Test
    public void testUsersTableStructure() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT column_name FROM user_tab_columns WHERE table_name = 'USERS' " +
                        "AND column_name IN ('USER_ID', 'USERNAME', 'EMAIL', 'PROFILE_NAME')";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            int columnCount = 0;
            while (rs.next()) {
                columnCount++;
                String columnName = rs.getString("column_name");
                System.out.println("✓ 컬럼 확인: " + columnName);
            }

            assertTrue("필수 컬럼이 부족합니다. 확인된 컬럼 수: " + columnCount, columnCount >= 4);
            System.out.println("✓ USERS 테이블 구조 확인 완료");

        } catch (SQLException e) {
            fail("USERS 테이블 구조 확인 실패: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }
    }

    /**
     * USERS 테이블 데이터 조회 테스트
     */
    @Test
    public void testUsersTableQuery() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT USER_ID, USERNAME, EMAIL, PROFILE_NAME FROM USERS WHERE ROWNUM <= 5";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            System.out.println("\n=== 사용자 목록 (최대 5개) ===");
            System.out.println("ID\t이름\t\t이메일\t\t\t프로필명");
            System.out.println("------------------------------------------------------------");

            int recordCount = 0;
            while (rs.next()) {
                recordCount++;
                int userId = rs.getInt("USER_ID");
                String username = rs.getString("USERNAME");
                String email = rs.getString("EMAIL");
                String profileName = rs.getString("PROFILE_NAME");

                System.out.printf("%d\t%s\t\t%s\t\t%s\n", 
                    userId, 
                    username != null ? username : "NULL", 
                    email != null ? email : "NULL", 
                    profileName != null ? profileName : "NULL");

                // 데이터 유효성 검증
                assertTrue("USER_ID는 0보다 커야 합니다.", userId > 0);
            }

            System.out.println("조회된 레코드 수: " + recordCount);
            System.out.println("✓ USERS 테이블 쿼리 실행 성공");

        } catch (SQLException e) {
            fail("USERS 테이블 쿼리 실행 실패: " + e.getMessage());
        } finally {
            closeResources(rs, pstmt);
        }
    }

    /**
     * 데이터베이스 메타데이터 테스트
     */
    @Test
    public void testDatabaseMetadata() {
        try {
            String dbProductName = conn.getMetaData().getDatabaseProductName();
            String dbProductVersion = conn.getMetaData().getDatabaseProductVersion();
            String driverName = conn.getMetaData().getDriverName();
            String driverVersion = conn.getMetaData().getDriverVersion();

            System.out.println("\n=== 데이터베이스 정보 ===");
            System.out.println("DB 제품명: " + dbProductName);
            System.out.println("DB 버전: " + dbProductVersion);
            System.out.println("드라이버명: " + driverName);
            System.out.println("드라이버 버전: " + driverVersion);

            assertTrue("Oracle 데이터베이스가 아닙니다.", 
                dbProductName.toUpperCase().contains("ORACLE"));
            System.out.println("✓ 데이터베이스 메타데이터 확인 완료");

        } catch (SQLException e) {
            fail("데이터베이스 메타데이터 조회 실패: " + e.getMessage());
        }
    }

    /**
     * 트랜잭션 테스트
     */
    @Test
    public void testTransaction() {
        try {
            // 자동 커밋 비활성화
            conn.setAutoCommit(false);
            assertFalse("자동 커밋이 비활성화되지 않았습니다.", conn.getAutoCommit());

            // 롤백 테스트
            conn.rollback();
            System.out.println("✓ 롤백 테스트 성공");

            // 자동 커밋 활성화
            conn.setAutoCommit(true);
            assertTrue("자동 커밋이 활성화되지 않았습니다.", conn.getAutoCommit());
            System.out.println("✓ 트랜잭션 테스트 완료");

        } catch (SQLException e) {
            fail("트랜잭션 테스트 실패: " + e.getMessage());
        }
    }

    /**
     * 연결 풀 테스트 (기본 연결 속성)
     */
    @Test
    public void testConnectionProperties() {
        try {
            // 연결 속성 확인
            assertFalse("연결이 읽기 전용으로 설정되어 있습니다.", conn.isReadOnly());
            
            // 격리 수준 확인
            int isolationLevel = conn.getTransactionIsolation();
            assertTrue("트랜잭션 격리 수준이 유효하지 않습니다.", 
                isolationLevel >= Connection.TRANSACTION_READ_UNCOMMITTED && 
                isolationLevel <= Connection.TRANSACTION_SERIALIZABLE);

            System.out.println("✓ 연결 속성 확인 완료");
            System.out.println("  - 읽기 전용: " + conn.isReadOnly());
            System.out.println("  - 트랜잭션 격리 수준: " + isolationLevel);

        } catch (SQLException e) {
            fail("연결 속성 확인 실패: " + e.getMessage());
        }
    }

    /**
     * 리소스 해제 유틸리티 메서드
     */
    private void closeResources(ResultSet rs, PreparedStatement pstmt) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            System.err.println("ResultSet 해제 실패: " + e.getMessage());
        }
        
        try {
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) {
            System.err.println("PreparedStatement 해제 실패: " + e.getMessage());
        }
    }
}