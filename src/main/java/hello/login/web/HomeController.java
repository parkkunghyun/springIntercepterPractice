package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private  final MemberRepository memberRepository;
   private  final SessionManager sessionManager;

    // @GetMapping("/")
    public String home() {
        return "home";
    }


    //@GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId",required = false)Long memberId, Model model) {
        // login 되면 다른 화면 보여주기!
        if(memberId ==null) {
            return "home";
        }

        Member member = memberRepository.findById(memberId);
        if(member == null) {
            return "home";
        }
        model.addAttribute("member", member) ;
        return "loginHome";
    }

    //@GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {
        // session 관리자에 저장된 회원정보 조회
        Member sessionMember = (Member)sessionManager.getSession(request);

        if(sessionMember == null) {
            return "home";
        }
        model.addAttribute("member", sessionMember) ;
        return "loginHome";
    }

   // @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {
        // session 관리자에 저장된 회원정보 조회

        // 일단은 로그인 안한 화면이 나올테니까 false로 설정!
        HttpSession session = request.getSession(false);
        if(session == null) {
            return "home";
        }
        Member loginMember =(Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(loginMember == null) {
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "loginHome";
    }

    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        // session 관리자에 저장된 회원정보 조회
        if(loginMember == null) {
            return "home";
        }
        model.addAttribute("member", loginMember);
        return "loginHome";
    }


}