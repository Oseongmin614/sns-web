package kr.ac.kopo.sns.service;

import kr.ac.kopo.sns.dao.PostDAO;
import kr.ac.kopo.sns.vo.PostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {

    @Autowired
    private PostDAO postDAO;

    /**
     * PostController용 - getPostsWithPaging 호출
     */
    public List<PostVO> getPostsWithPaging(int page, int pageSize) {
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;
        
        Map<String, Object> param = new HashMap<>();
        param.put("startRow", startRow);
        param.put("endRow", endRow);
        
        return postDAO.getPostsWithPaging(param);
    }

    /**
     * MainController용 - getPostList 호출 + 페이지네이션 정보 포함
     */
    public Map<String, Object> getPostList(int page) {
        final int pageSize = 10; // 한 페이지당 게시글 수
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        // 게시글 목록 조회 (최신순)
        Map<String, Object> param = new HashMap<>();
        param.put("startRow", startRow);
        param.put("endRow", endRow);
        List<PostVO> postList = postDAO.getPostList(param);

        // 전체 게시글 수 조회
        int totalCount = postDAO.getTotalPostCount();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // 페이지네이션 정보 계산
        boolean hasPrevious = page > 1;
        boolean hasNext = page < totalPages;

        Map<String, Object> result = new HashMap<>();
        result.put("postList", postList);
        result.put("currentPage", page);
        result.put("totalPages", totalPages);
        result.put("totalCount", totalCount);
        result.put("hasPrevious", hasPrevious);
        result.put("hasNext", hasNext);

        return result;
    }

    /**
     * 무한스크롤용
     */
    public Map<String, Object> getFeedChunk(Long lastId, int limit) {
        Map<String, Object> param = new HashMap<>();
        param.put("lastId", lastId);
        param.put("limit", limit);
        List<PostVO> list = postDAO.selectFeedChunk(param);
        Long nextCursor = (list.isEmpty()) ? -1L : list.get(list.size()-1).getPostId();
        Map<String, Object> result = new HashMap<>();
        result.put("posts", list);
        result.put("nextCursor", nextCursor);
        return result;
    }

    public PostVO getPostById(Long postId) {
        return postDAO.getPostById(postId);
    }

    public List<PostVO> getPostsByUserId(Long userId) {
        return postDAO.getPostsByUserId(userId);
    }

    public boolean createPost(PostVO post) {
        return postDAO.createPost(post);
    }

    public boolean updatePost(PostVO post) {
        return postDAO.updatePost(post);
    }

    public boolean deletePost(Long postId) {
        return postDAO.deletePost(postId);
    }

    public boolean toggleLike(Long postId, Long userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("postId", postId);
        param.put("userId", userId);
        return postDAO.toggleLike(param);
    }

    public boolean increaseLikeCount(Long postId) {
        return postDAO.increaseLikeCount(postId);
    }
}
