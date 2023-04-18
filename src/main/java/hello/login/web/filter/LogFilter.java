package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log 필터 초기화");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("do filter 로그 ");
        // HttpServletRequest의 부모여서 다운 캐스팅하기!
       HttpServletRequest httpRequest = (HttpServletRequest) request;
       String requestURI = httpRequest.getRequestURI();

       // 요청에 대해 아이디를 남김!
       String uuid =UUID.randomUUID().toString();

       try{
           log.info("REQUEST [{}][{}]", uuid, requestURI);
           chain.doFilter(request,response);
       }catch (Exception e) {
           throw  e;
       }finally {
           log.info("RESPONSE 즉 finally까지옴");
       }
    }
    @Override
    public void destroy() {
        log.info("log 필터 종료");
    }
}
