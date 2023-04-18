package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LoginCheckFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    private static final String[] whiteList = {"/", "/members/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 로그인안한 사람이 url 들어오면 내보내기!

        HttpServletRequest httpServletRequest =(HttpServletRequest)request;
        String requestURI = httpServletRequest.getRequestURI();

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        try{
            log.info("인증체크 필터 시작{}", requestURI);
            if(isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행{}", requestURI);
                HttpSession session =httpServletRequest.getSession(false);
                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null) {
                    log.info("미인증 사용자 요청 {}", requestURI);
                    // login 하고 다시 저 페이지로 갈 수 있게!
                    httpServletResponse.sendRedirect("/login?redirectURL="+ requestURI);
                    return;
                }
            }
            chain.doFilter(request,response);
        }catch (Exception e){
            throw e;
        }finally {
            log.info("인증 체크 필터 종료! {}", requestURI);
        }
    }

    /**
     * white list일 경우 인증 체크 안하게!
     */

    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }

    @Override
    public void destroy() {

    }
}
