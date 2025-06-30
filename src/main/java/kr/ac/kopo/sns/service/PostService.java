package kr.ac.kopo.sns.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kr.ac.kopo.sns.dao.PostDAO;
import kr.ac.kopo.sns.vo.PostVO;

@Service
public class PostService {
    
    @Autowired
    private PostDAO postDAO;
    
    public boolean createPost(PostVO post) {
        try {
            if (post.getStatus() == null) {
                post.setStatus("ACTIVE");
            }
            
            int result = postDAO.insertPost(post);
            return result > 0;
        } catch (Exception e) {
            System.err.println("게시글 작성 중 오류 발생: " + e.getMessage());
            return false;
        }
    }
    
    public PostVO getPostById(Long postId) {
        try {
            return postDAO.selectPostById(postId);
        } catch (Exception e) {
            System.err.println("게시글 조회 중 오류 발생: " + e.getMessage());
            return null;
        }
    }
    
    public List<PostVO> getAllPosts() {
        try {
            return postDAO.selectAllPosts();
        } catch (Exception e) {
            System.err.println("전체 게시글 조회 중 오류 발생: " + e.getMessage());
            return null;
        }
    }
    
    public List<PostVO> getPostsByUserId(Long userId) {
        try {
            return postDAO.selectPostsByUserId(userId);
        } catch (Exception e) {
            System.err.println("사용자별 게시글 조회 중 오류 발생: " + e.getMessage());
            return null;
        }
    }
    
    public boolean updatePost(PostVO post) {
        try {
            int result = postDAO.updatePost(post);
            return result > 0;
        } catch (Exception e) {
            System.err.println("게시글 수정 중 오류 발생: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deletePost(Long postId) {
        try {
            int result = postDAO.deletePost(postId);
            return result > 0;
        } catch (Exception e) {
            System.err.println("게시글 삭제 중 오류 발생: " + e.getMessage());
            return false;
        }
    }
    
    public boolean toggleLike(Long postId, Long userId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("postId", postId);
            params.put("userId", userId);
            
            int likeExists = postDAO.checkLike(params);
            
            if (likeExists > 0) {
                postDAO.deleteLike(params);
            } else {
                postDAO.insertLike(params);
            }
            
            postDAO.updateLikeCount(postId);
            return true;
        } catch (Exception e) {
            System.err.println("좋아요 토글 중 오류 발생: " + e.getMessage());
            return false;
        }
    }
    
    public List<PostVO> getPostsWithPaging(int page, int size) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("offset", (page - 1) * size);
            params.put("limit", size);
            
            return postDAO.selectPostsWithPaging(params);
        } catch (Exception e) {
            System.err.println("페이징 게시글 조회 중 오류 발생: " + e.getMessage());
            return null;
        }
    }
}
