package kr.ac.kopo.sns.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import kr.ac.kopo.sns.service.CommentService;
import kr.ac.kopo.sns.vo.CommentVO;
import kr.ac.kopo.sns.vo.UserVO;

@Controller
@RequestMapping("/comment")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    @PostMapping("/write")
    @ResponseBody
    public String writeComment(CommentVO comment, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "unauthorized";
        }
        
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "unauthorized";
        }
        
        comment.setUserId(loginUser.getUserId());
        boolean result = commentService.createComment(comment);
        return result ? "success" : "fail";
    }
    
    @PostMapping("/edit")
    @ResponseBody
    public String editComment(CommentVO comment, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "unauthorized";
        }
        
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "unauthorized";
        }
        
        CommentVO existingComment = commentService.getCommentById(comment.getCommentId());
        if (existingComment == null || !existingComment.getUserId().equals(loginUser.getUserId())) {
            return "unauthorized";
        }
        
        boolean result = commentService.updateComment(comment);
        return result ? "success" : "fail";
    }
    
    @PostMapping("/delete")
    @ResponseBody
    public String deleteComment(@RequestParam Long commentId, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "unauthorized";
        }
        
        UserVO loginUser = (UserVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "unauthorized";
        }
        
        CommentVO comment = commentService.getCommentById(commentId);
        if (comment == null || (!comment.getUserId().equals(loginUser.getUserId()) && !"ADMIN".equals(loginUser.getRole()))) {
            return "unauthorized";
        }
        
        boolean result = commentService.deleteComment(commentId);
        return result ? "success" : "fail";
    }
}
