package kr.ac.kopo.sns.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.ac.kopo.sns.dao.CommentDAO;
import kr.ac.kopo.sns.vo.CommentVO;

@Service
public class CommentService {

	@Autowired
	private CommentDAO commentDAO;

	public boolean createComment(CommentVO comment) {
		try {
			if (comment.getStatus() == null) {
				comment.setStatus("ACTIVE");
			}

			int result = commentDAO.insertComment(comment);
			return result > 0;
		} catch (Exception e) {
			System.err.println("댓글 작성 중 오류 발생: " + e.getMessage());
			return false;
		}
	}

	public CommentVO getCommentById(Long commentId) {
		try {
			return commentDAO.selectCommentById(commentId);
		} catch (Exception e) {
			System.err.println("댓글 조회 중 오류 발생: " + e.getMessage());
			return null;
		}
	}

	public List<CommentVO> getCommentsByPostId(Long postId) {
		try {
			return commentDAO.selectCommentsByPostId(postId);
		} catch (Exception e) {
			System.err.println("게시글별 댓글 조회 중 오류 발생: " + e.getMessage());
			return null;
		}
	}

	public boolean updateComment(CommentVO comment) {
		try {
			int result = commentDAO.updateComment(comment);
			return result > 0;
		} catch (Exception e) {
			System.err.println("댓글 수정 중 오류 발생: " + e.getMessage());
			return false;
		}
	}

	public boolean deleteComment(Long commentId) {
		try {
			int result = commentDAO.deleteComment(commentId);
			return result > 0;
		} catch (Exception e) {
			System.err.println("댓글 삭제 중 오류 발생: " + e.getMessage());
			return false;
		}
	}

	public List<CommentVO> getCommentsByUserId(Long userId) {
		try {
			return commentDAO.selectCommentsByUserId(userId);
		} catch (Exception e) {
			System.err.println("사용자별 댓글 조회 중 오류 발생: " + e.getMessage());
			return null;
		}
	}
}
