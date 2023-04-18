package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class SessionManager {
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();
    public static final String SESSION_COOKIE_NAME="mySessionId";

    public void createSession(Object value, HttpServletResponse response) {
        // sessionid생성
        String sessionId=  UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);
        // 쿠키 생성
        Cookie mySessionCookie =  new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);
        // 클라에 전달
    }

    /*
    for(Cookie cookie: cookies) {
           if(cookie.getName().equals(SESSION_COOKIE_NAME)) {
               return sessionStore.get(cookie.getValue());
           }
       }
       return null;
     */
    public Object getSession(HttpServletRequest request) {
       Cookie sessionCookie=  findCookie(request, SESSION_COOKIE_NAME);
        if(sessionCookie == null) {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
    }

    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request,SESSION_COOKIE_NAME);
        if(sessionCookie != null ){
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if(cookies ==null) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }
}
