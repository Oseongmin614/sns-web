package kr.ac.kopo.sns.dao;

import kr.ac.kopo.sns.vo.PostVO;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostDAO {
    // 무한스크롤용 (기존)
    List<PostVO> selectFeedChunk(Map<String, Object> param);
    
    // 페이지네이션용 - PostController에서 사용
    List<PostVO> getPostsWithPaging(Map<String, Object> param);
    
    // 페이지네이션용 - MainController에서 사용 
    List<PostVO> getPostList(Map<String, Object> param);
    
    // 전체 게시글 수 조회
    int getTotalPostCount();
    
    // 게시글 상세 조회
    PostVO getPostById(Long postId);
    
    // 사용자별 게시글 조회
    List<PostVO> getPostsByUserId(Long userId);
    
    // 게시글 작성
    boolean createPost(PostVO post);
    
    // 게시글 수정
    boolean updatePost(PostVO post);
    
    // 게시글 삭제
    boolean deletePost(Long postId);
    
    // 좋아요 토글
    boolean toggleLike(Map<String, Object> param);
    
    // 좋아요 수 증가
    boolean increaseLikeCount(Long postId);
}
