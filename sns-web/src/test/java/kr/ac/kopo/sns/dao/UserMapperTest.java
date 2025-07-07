package kr.ac.kopo.sns.dao;

import kr.ac.kopo.sns.vo.UserVO;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.sql.*;
import java.util.List;
import javax.sql.DataSource;
import static org.junit.Assert.*;
import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:/config/spring/spring-mvc.xml"
})
public class UserMapperTest {

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "snsweb";
    private static final String PASSWORD = "1234";

    private Connection conn;

    @Before
    public void setUp() throws Exception {
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
        assertNotNull("DB 연결 실패", conn);
    }

    @After
    public void tearDown() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Test
    public void testSelectPostListQuery() throws Exception {
        String sql = 
                "SELECT " +
                "    p.POST_ID, " +
                "    u.PROFILE_NAME, " +
                "    SUBSTR(p.CONTENT, 1, 50) || '...' as CONTENT_PREVIEW, " +
                "    p.LIKE_COUNT, " +
                "    p.COMMENT_COUNT, " +
                "    p.CREATED_AT " +
                "FROM POSTS p " +
                "JOIN USERS u ON p.USER_ID = u.USER_ID " +
                "WHERE p.IS_DELETED = 'N' " +
                "ORDER BY p.LIKE_COUNT DESC, p.CREATED_AT DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            assertNotNull(rs);

            ResultSetMetaData meta = rs.getMetaData();
            assertEquals("POST_ID", meta.getColumnName(1));
            assertEquals("PROFILE_NAME", meta.getColumnName(2));
            assertEquals("CONTENT_PREVIEW", meta.getColumnName(3));
            assertEquals("LIKE_COUNT", meta.getColumnName(4));
            assertEquals("COMMENT_COUNT", meta.getColumnName(5));
            assertEquals("CREATED_AT", meta.getColumnName(6));

            int count = 0;
            System.out.println("POST_ID\tPROFILE_NAME\tCONTENT_PREVIEW\tLIKE_COUNT\tCOMMENT_COUNT\tCREATED_AT");
            System.out.println("-------------------------------------------------------------------------------");
            while (rs.next() && count < 5) {
                int postId = rs.getInt("POST_ID");
                String profileName = rs.getString("PROFILE_NAME");
                String contentPreview = rs.getString("CONTENT_PREVIEW");
                int likeCount = rs.getInt("LIKE_COUNT");
                int commentCount = rs.getInt("COMMENT_COUNT");
                Timestamp createdAt = rs.getTimestamp("CREATED_AT");

                System.out.printf("%d\t%s\t%s\t%d\t%d\t%s\n",
                        postId, profileName, contentPreview, likeCount, commentCount, createdAt);

                // 값 검증
                assertNotNull(profileName);
                assertNotNull(contentPreview);
                assertNotNull(createdAt);

                count++;
            }
            System.out.println("-------------------------------------------------------------------------------");
            System.out.println("총 출력 행 수: " + count);
        }
    }

}

