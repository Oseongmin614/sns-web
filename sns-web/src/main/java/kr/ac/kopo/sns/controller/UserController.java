package kr.ac.kopo.sns.controller;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import kr.ac.kopo.sns.service.UserService;
import kr.ac.kopo.sns.vo.UserVO;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
@Mapper
public class UserController {

    @Autowired
    private UserService userService;

    // 로그인 페이지 표시
    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model) {
        // 이미 로그인된 사용자는 메인 페이지로 리다이렉트
        UserVO user = (UserVO) session.getAttribute("user");
        if (user != null) {
            return "redirect:/";
        }
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                       @RequestParam("password") String password,
                       HttpSession session,
                       Model model) {
        UserVO user = userService.loginUser(email, password);
        if (user == null) {
            model.addAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
            return "user/login";
        }
        
        session.setAttribute("user", user);
        return "redirect:/";
    }


    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/user/login";
    }
    @PostMapping("/logout")
    public String logoutPost(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signupPage(HttpSession session) {
        // 이미 로그인된 사용자는 메인 페이지로 리다이렉트
        UserVO user = (UserVO) session.getAttribute("user");
        if (user != null) {
            return "redirect:/";
        }
        return "user/signup";
    }

    // 회원가입 처리 (폼 제출)
    @PostMapping("/signup")
    public String signup(@ModelAttribute UserVO user, Model model) {
        // 이메일 중복 체크
        if (userService.isEmailExists(user.getEmail())) {
            model.addAttribute("error", "이미 사용 중인 이메일입니다.");
            return "user/signup";
        }

        // 닉네임 중복 체크
        if (userService.isNicknameExists(user.getNickname())) {
            model.addAttribute("error", "이미 사용 중인 닉네임입니다.");
            return "user/signup";
        }

        // 회원가입 처리
        boolean result = userService.registerUser(user);
        if (result) {
            model.addAttribute("success", "회원가입이 완료되었습니다. 로그인해주세요.");
            return "redirect:/user/login";
        } else {
            model.addAttribute("error", "회원가입 처리 중 오류가 발생했습니다.");
            return "user/signup";
        }
    }

    // AJAX 이메일 중복 체크
    @PostMapping("/check-email")
    @ResponseBody
    public Map<String, Object> checkEmail(@RequestParam String email) {
        Map<String, Object> response = new HashMap<>();
        boolean exists = userService.isEmailExists(email);
        response.put("success", true);
        response.put("exists", exists);
        response.put("message", exists ? "이미 사용 중인 이메일입니다." : "사용 가능한 이메일입니다.");
        return response;
    }

    // AJAX 닉네임 중복 체크
    @PostMapping("/check-nickname")
    @ResponseBody
    public Map<String, Object> checkNickname(@RequestParam String nickname) {
        Map<String, Object> response = new HashMap<>();
        boolean exists = userService.isNicknameExists(nickname);
        response.put("success", true);
        response.put("exists", exists);
        response.put("message", exists ? "이미 사용 중인 닉네임입니다." : "사용 가능한 닉네임입니다.");
        return response;
    }
    @GetMapping("/profile/{userId}")
    public String userProfile(@PathVariable Long userId, Model model) {
        UserVO user = userService.getUserById(userId);
        if (user == null) {
            model.addAttribute("errorMessage", "존재하지 않는 사용자입니다.");
            return "error/404";
        }
        
        model.addAttribute("user", user);
        
        return "user/profile";
    }
}
