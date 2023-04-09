package webserver2.newproject.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webserver2.newproject.exception.BusinessLogicException;
import webserver2.newproject.exception.ExceptionCode;
import webserver2.newproject.member.dto.MemberPostDto;
import webserver2.newproject.member.entity.Member;
import webserver2.newproject.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public Member findMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public Long createMember(MemberPostDto memberPostDto) {
        Member member = new Member();

        member.setEmail(memberPostDto.getEmail());
        member.setPassword(memberPostDto.getPassword());
        member.setNickname(memberPostDto.getNickname());

        return memberRepository.save(member).getMemberId();

    }

    public Long updateMember(M)
}
