package kr.ac.kopo.sns.dao;

import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import kr.ac.kopo.sns.vo.PostVO;

@Repository
public class PostDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	private static final String NAMESPACE = "postMapper.";

	public int insertPost(PostVO post) {
		return sqlSession.insert(NAMESPACE + "insertPost", post);
	}

	public PostVO selectPostById(Long postId) {
		return sqlSession.selectOne(NAMESPACE + "selectPostById", postId);
	}

	public List<PostVO> selectAllPosts() {
		return sqlSession.selectList(NAMESPACE + "selectAllPosts");
	}

	public List<PostVO> selectPostsByUserId(Long userId) {
		return sqlSession.selectList(NAMESPACE + "selectPostsByUserId", userId);
	}

	public int updatePost(PostVO post) {
		return sqlSession.update(NAMESPACE + "updatePost", post);
	}

	public int deletePost(Long postId) {
		return sqlSession.update(NAMESPACE + "deletePost", postId);
	}

	public int insertLike(Map<String, Object> params) {
		return sqlSession.insert(NAMESPACE + "insertLike", params);
	}

	public int deleteLike(Map<String, Object> params) {
		return sqlSession.delete(NAMESPACE + "deleteLike", params);
	}

	public int checkLike(Map<String, Object> params) {
		return sqlSession.selectOne(NAMESPACE + "checkLike", params);
	}

	public int updateLikeCount(Long postId) {
		return sqlSession.update(NAMESPACE + "updateLikeCount", postId);
	}

	public List<PostVO> selectPostsWithPaging(Map<String, Object> params) {
		return sqlSession.selectList(NAMESPACE + "selectPostsWithPaging", params);
	}
}
