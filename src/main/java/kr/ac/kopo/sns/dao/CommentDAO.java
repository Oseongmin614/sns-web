package kr.ac.kopo.sns.dao;

import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import kr.ac.kopo.sns.vo.CommentVO;

@Repository
public class CommentDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	private static final String NAMESPACE = "commentMapper.";

	public int insertComment(CommentVO comment) {
		return sqlSession.insert(NAMESPACE + "insertComment", comment);
	}

	public CommentVO selectCommentById(Long commentId) {
		return sqlSession.selectOne(NAMESPACE + "selectCommentById", commentId);
	}

	public List<CommentVO> selectCommentsByPostId(Long postId) {
		return sqlSession.selectList(NAMESPACE + "selectCommentsByPostId", postId);
	}

	public int updateComment(CommentVO comment) {
		return sqlSession.update(NAMESPACE + "updateComment", comment);
	}

	public int deleteComment(Long commentId) {
		return sqlSession.update(NAMESPACE + "deleteComment", commentId);
	}

	public List<CommentVO> selectCommentsByUserId(Long userId) {
		return sqlSession.selectList(NAMESPACE + "selectCommentsByUserId", userId);
	}
}
