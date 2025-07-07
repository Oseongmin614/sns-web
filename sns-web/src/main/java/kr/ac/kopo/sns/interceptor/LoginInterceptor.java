package kr.ac.kopo.sns.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import kr.ac.kopo.sns.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse; 
import jakarta.servlet.http.HttpSession;
/**
 * 로그인 체크를 위한 인터셉터
 */
public class LoginInterceptor implements HandlerInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
            throws Exception {
        
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String requestURL = request.getRequestURL().toString();
        
        logger.debug("로그인 체크 인터셉터 실행: {}", requestURI);
        
        HttpSession session = request.getSession(false);
        UserVO user = null;
        
        if (session != null) {
            user = (UserVO) session.getAttribute("user");
        }
        
        if (user == null) {
            logger.info("미인증 사용자 요청: {}", requestURI);
            
            // AJAX 요청인지 확인
            String requestedWith = request.getHeader("X-Requested-With");
            boolean isAjax = "XMLHttpRequest".equals(requestedWith);
            
            if (isAjax) {
                // AJAX 요청의 경우 JSON 응답
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\":\"로그인이 필요합니다.\",\"redirectUrl\":\"" + contextPath + "/user/login\"}");
                return false;
            } else {
                // 일반 요청의 경우 로그인 페이지로 리다이렉트
                String loginURL = contextPath + "/user/login";
                
                // 원래 요청 URL을 파라미터로 저장 (로그인 후 돌아갈 수 있도록)
                if (!"GET".equalsIgnoreCase(request.getMethod())) {
                    // POST 요청의 경우 단순히 로그인 페이지로
                    response.sendRedirect(loginURL);
                } else {
                    // GET 요청의 경우 원래 URL을 저장
                    String redirectURL = loginURL + "?returnUrl=" + java.net.URLEncoder.encode(requestURI, "UTF-8");
                    response.sendRedirect(redirectURL);
                }
                return false;
            }
        }
        
        // 사용자가 활성 상태인지 확인
        if (!"ACTIVE".equals(user.getStatus())) {
            logger.warn("비활성 사용자 접근 시도: {} (상태: {})", user.getEmail(), user.getStatus());
            
            // 세션 무효화
            session.invalidate();
            
            // AJAX 요청인지 확인
            String requestedWith = request.getHeader("X-Requested-With");
            boolean isAjax = "XMLHttpRequest".equals(requestedWith);
            
            if (isAjax) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\":\"계정이 비활성화되었습니다.\",\"redirectUrl\":\"" + contextPath + "/user/login\"}");
                return false;
            } else {
                response.sendRedirect(contextPath + "/user/login?error=account_disabled");
                return false;
            }
        }
        
        logger.debug("로그인 체크 통과: {} (사용자: {})", requestURI, user.getEmail());
        return true;
    }
}