package kr.ac.kopo.sns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import kr.ac.kopo.sns.service.UserService;
import kr.ac.kopo.sns.vo.UserVO;
import java.util.HashMap;
import java.util.Map;

@SessionAttributes({"userVO"}) // 세션에 userVO 저장
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 로그인 페이지 표시
    @GetMapping("/login")
    public String loginPage(Model model) {
        // 이미 로그인된 사용자는 메인 페이지로 리다이렉트
        if (model.containsAttribute("userVO")) {
            return "redirect:/";
        }
        return "user/login";
    }

    // 로그인 처리 (AJAX 응답 없이, 일반 폼 제출 시)
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {
        UserVO user = userService.login(email, password);
        if (user == null) {
            // 로그인 실패
            return "redirect:/user/login";
        }
        // 로그인 성공
        model.addAttribute("userVO", user); // 세션에 저장됨
        userService.updateLastLoginTime(user.getUserId());
        return "redirect:/";
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(SessionStatus status) {
        status.setComplete(); // 세션에서 userVO 제거
        return "redirect:/user/login";
    }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signupPage() {
        return "user/signup";
    }

    // 회원가입 처리 (AJAX 응답 없이, 일반 폼 제출 시)
    @PostMapping("/signup")
    public String signup(@ModelAttribute UserVO user, Model model) {
        // 이메일 중복 체크
        if (userService.isEmailExists(user.getEmail())) {
            model.addAttribute("message", "이미 사용 중인 이메일입니다.");
            return "user/signup";
        }
        // 닉네임 중복 체크
        if (userService.isNicknameExists(user.getNickname())) {
            model.addAttribute("message", "이미 사용 중인 닉네임입니다.");
            return "user/signup";
        }
        // 회원가입 처리
        boolean result = userService.registerUser(user);
        if (result) {
            model.addAttribute("message", "회원가입이 완료되었습니다.");
            return "redirect:/user/login";
        } else {
            model.addAttribute("message", "회원가입 처리 중 오류가 발생했습니다.");
            return "user/signup";
        }
    }
}
