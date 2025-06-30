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
    public String writePage(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/user/login";
        }
        
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        return "post/write";
    }
    
    @PostMapping("/write")
    @ResponseBody
    public String write(PostVO post, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "unauthorized";
        }
        
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "unauthorized";
        }
        
        post.setUserId(loginUser.getUserId());
        boolean result = postService.createPost(post);
        return result ? "success" : "fail";
    }
    
    @GetMapping("/detail")
    public String detail(@RequestParam Long postId, Model model) {
        PostVO post = postService.getPostById(postId);
        if (post == null) {
            return "redirect:/post/feed";
        }
        
        List<CommentVO> comments = commentService.getCommentsByPostId(postId);
        
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "post/detail";
    }
    
    @GetMapping("/edit")
    public String editPage(@RequestParam Long postId, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/user/login";
        }
        
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        
        PostVO post = postService.getPostById(postId);
        if (post == null || !post.getUserId().equals(loginUser.getUserId())) {
            return "redirect:/post/feed";
        }
        
        model.addAttribute("post", post);
        return "post/edit";
    }
    
    @PostMapping("/edit")
    @ResponseBody
    public String edit(PostVO post, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "unauthorized";
        }
        
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "unauthorized";
        }
        
        PostVO existingPost = postService.getPostById(post.getPostId());
        if (existingPost == null || !existingPost.getUserId().equals(loginUser.getUserId())) {
            return "unauthorized";
        }
        
        boolean result = postService.updatePost(post);
        return result ? "success" : "fail";
    }
    
    @PostMapping("/delete")
    @ResponseBody
    public String delete(@RequestParam Long postId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "unauthorized";
        }
        
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "unauthorized";
        }
        
        PostVO post = postService.getPostById(postId);
        if (post == null || (!post.getUserId().equals(loginUser.getUserId()) && !"ADMIN".equals(loginUser.getRole()))) {
            return "unauthorized";
        }
        
        boolean result = postService.deletePost(postId);
        return result ? "success" : "fail";
    }
    
    @PostMapping("/like")
    @ResponseBody
    public String toggleLike(@RequestParam Long postId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "unauthorized";
        }
        
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "unauthorized";
        }
        
        boolean result = postService.toggleLike(postId, loginUser.getUserId());
        return result ? "success" : "fail";
    }
    
    @GetMapping("/myPosts")
    public String myPosts(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/user/login";
        }
        
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        
        List<PostVO> posts = postService.getPostsByUserId(loginUser.getUserId());
        model.addAttribute("posts", posts);
        return "post/myPosts";
    }
}
