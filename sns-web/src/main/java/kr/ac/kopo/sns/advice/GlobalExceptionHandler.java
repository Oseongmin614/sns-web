package kr.ac.kopo.sns.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String ERROR_MESSAGE_KEY = "errorMessage";

    /**
     * AJAX 요청에 대한 예외를 처리 (JSON 응답)
     */
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleAjaxException(Exception e) {
        logger.error("AJAX 예외 발생: {}", e.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put(ERROR_MESSAGE_KEY, e.getMessage());

        if (e instanceof IllegalStateException) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT); // 409 Conflict
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400 Bad Request
    }

    /**
     * 일반적인 서버 예외를 처리 (에러 페이지로 이동)
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e, HttpServletRequest request) {
        logger.error("처리되지 않은 예외 발생: {} 요청", request.getRequestURI(), e);

        ModelAndView mav = new ModelAndView();
        mav.addObject(ERROR_MESSAGE_KEY, "서버에서 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        mav.setViewName("error/errorPage"); // 공통 에러 페이지
        return mav;
    }
}
