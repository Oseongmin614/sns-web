package kr.ac.kopo.sns.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.ac.kopo.sns.service.PostService;
import kr.ac.kopo.sns.service.CommentService;
import kr.ac.kopo.sns.vo.PostVO;
import kr.ac.kopo.sns.vo.UserVO;
import kr.ac.kopo.sns.vo.CommentVO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/post")
public class PostController {
    
    @Autowired
    private PostService postService;
    
    @Autowired
    private CommentService commentService;
    
    @GetMapping("/feed")
    public String feed(Model model, @RequestParam(defaultValue = "1") int page) {
        List<PostVO> posts = postService.getPostsWithPaging(page, 10);
        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        return "post/feed";
    }
    
    @GetMapping("/write")
    public String writePage() {
        return "post/write";
    }
    
    @PostMapping("/write")
    @ResponseBody
    public String write(PostVO post, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");

        post.setUserId(user.getUserId());
        boolean result = postService.createPost(post);
        return result ? "success" : "fail";
    }
    
    @GetMapping("/{postId}")
    public String detail(@PathVariable Long postId, Model model) {
        PostVO post = postService.getPostById(postId);
        if (post == null) {
            return "redirect:/post/feed"; // 혹은 에러 페이지
        }

        List<CommentVO> comments = commentService.getCommentsByPostId(postId);

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "post/detail";
    }
    
    @GetMapping("/edit")
    public String editPage(@RequestParam Long postId, HttpSession session, Model model) {
        UserVO user = (UserVO) session.getAttribute("user");

        PostVO post = postService.getPostById(postId);
        if (post == null || !post.getUserId().equals(user.getUserId())) {
            return "redirect:/post/feed";
        }
        
        model.addAttribute("post", post);
        return "post/edit";
    }
    
    @PostMapping("/edit")
    @ResponseBody
    public String edit(PostVO post, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");

        PostVO existingPost = postService.getPostById(post.getPostId());
        if (existingPost == null || !existingPost.getUserId().equals(user.getUserId())) {
            return "unauthorized";
        }
        
        boolean result = postService.updatePost(post);
        return result ? "success" : "fail";
    }
    
    @PostMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam Long postId, HttpSession session) {
        UserVO user = (UserVO) session.getAttribute("user");

        PostVO post = postService.getPostById(postId);
        if (post == null || (!post.getUserId().equals(user.getUserId()) && !"ADMIN".equals(user.getRole()))) {
            return "unauthorized";
        }
        
        boolean result = postService.deletePost(postId);
        return result ? "success" : "fail";
    }
    
    @PostMapping("/{postId}/like")
    @ResponseBody
    public Map<String, Object> toggleLike(@PathVariable Long postId, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        UserVO user = (UserVO) session.getAttribute("user");

        boolean liked = postService.toggleLike(postId, user.getUserId());
        int likeCount = postService.getLikeCount(postId); // 좋아요 수 다시 조회

        response.put("success", true);
        response.put("liked", liked);
        response.put("likeCount", likeCount);

        return response;
    }
    
    @GetMapping("/myPosts")
    public String myPosts(HttpSession session, Model model) {
        UserVO user = (UserVO) session.getAttribute("user");

        List<PostVO> posts = postService.getPostsByUserId(user.getUserId());
        model.addAttribute("posts", posts);
        return "post/myPosts";
    }
    
  
}
