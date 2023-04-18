package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();

    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save member={}", member);
        store.put(member.getId(),member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Member> findByLoginId(String loginId) {
        List<Member> all = findAll();
        for(Member m : all) {
            if(m.getLoginId().equals(loginId)) {
                return Optional.of(m);
            }

        }
        // 값이 널로 반환해야할때 이걸 사용!
        return Optional.empty();
        /* stream이 돈다는 의미!
        가장 먼저 나오는애를 바로 리턴해줌!!
        return findAll().stream()
                .filter(m-> m.getLoginId().equals(loginId))
                .findFirst();

         */

    }

    public void clearStore(){
        store.clear();
    }

}
