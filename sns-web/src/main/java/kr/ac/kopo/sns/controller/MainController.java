package kr.ac.kopo.sns.controller;

import kr.ac.kopo.sns.service.PostService;
import kr.ac.kopo.sns.vo.PostVO;
import kr.ac.kopo.sns.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SNS 메인/피드/기타 페이지 통합 컨트롤러
 */
@Controller
public class MainController {

    @Autowired
    private PostService postService;

    /**
     * 메인 페이지 (피드)
     * - 로그인 여부에 따라 분기
     * - 게시글 목록(최신순), 페이지네이션, 로그인 사용자 정보 제공
     */
    @GetMapping({"/", "/index"})
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                       HttpSession session, Model model) {
        UserVO user = (UserVO) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("userName", user.getNickname());
            // 향후: 사용자별 맞춤 정보 추가 가능
        } else {
            model.addAttribute("isLoggedIn", false);
        }

        // 게시글 목록 조회 (최신순, 페이지네이션)
        Map<String, Object> result = postService.getPostList(page);
        model.addAttribute("postList", result.get("postList"));
        model.addAttribute("currentPage", result.get("currentPage"));
        model.addAttribute("totalPages", result.get("totalPages"));
        model.addAttribute("totalCount", result.get("totalCount"));
        model.addAttribute("hasPrevious", result.get("hasPrevious"));
        model.addAttribute("hasNext", result.get("hasNext"));

        return "index"; // /WEB-INF/jsp/index.jsp
    }

    

    /**
     * 회사 소개 페이지
     */
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    /**
     * 개인정보처리방침 페이지
     */
    @GetMapping("/privacy")
    public String privacy() {
        return "privacy";
    }

    /**
     * 이용약관 페이지
     */
    @GetMapping("/terms")
    public String terms() {
        return "terms";
    }

    /**
     * 고객지원 페이지
     */
    @GetMapping("/support")
    public String support() {
        return "support";
    }

    /**
     * 도움말 페이지
     */
    @GetMapping("/help")
    public String help() {
        return "help";
    }

    /**
     * 문의하기 페이지
     */
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
    
/*    @GetMapping("/feedChunk")
    public List<PostVO> feedChunk(
            @RequestParam(value = "lastId", required = false) Long lastId,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        List<PostVO> posts = postService.getFeedChunk(lastId, limit);
        // VO에 createdAtStr 필드를 추가하거나, map으로 변환하여 날짜 포맷팅 문자열을 설정
        posts.forEach(p -> p.setCreatedAtStr(
            new SimpleDateFormat("yyyy-MM-dd HH:mm").format(p.getCreatedAt())
        ));
        return posts;
    }
  */  
    @GetMapping("/post/feedChunk")
    @ResponseBody
    public Map<String, Object> feedChunk(
            @RequestParam(value = "lastId", required = false) Long lastId,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

    	
        // 서비스에서 반환된 Map
        Map<String, Object> result = postService.getFeedChunk(lastId, limit);

        // posts 키로 들어있는 List<PostVO> 꺼내기
        @SuppressWarnings("unchecked")
        List<PostVO> posts = (List<PostVO>) result.get("posts");

        // 날짜 포맷터
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        // 다시 Map에 담아 반환
        result.put("posts", posts);
        return result;
    }
}
