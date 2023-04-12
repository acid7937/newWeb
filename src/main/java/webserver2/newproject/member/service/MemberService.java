package webserver2.newproject.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import webserver2.newproject.exception.BusinessLogicException;
import webserver2.newproject.exception.ExceptionCode;
import webserver2.newproject.member.dto.MemberPatchDto;
import webserver2.newproject.member.dto.MemberPostDto;
import webserver2.newproject.member.dto.MemberResponseDto;
import webserver2.newproject.member.entity.Member;
import webserver2.newproject.member.repository.MemberRepository;


@Service
@RequiredArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;


    public Member findMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public Long createMember(MemberPostDto memberPostDto) {
        Member member = new Member();

        member.setEmail(memberPostDto.getEmail());
        member.setPassword(memberPostDto.getPassword());
//        member.setPassword(passwordEncoder.encode(memberPostDto.getPassword()));

        member.setNickname(memberPostDto.getNickname());

        return memberRepository.save(member).getMemberId();

    }

    public Long updateMember(MemberPatchDto memberPatchDto,Long memberId) {
        Member member = findMemberId(memberId);
        member.setNickname(memberPatchDto.getNickname());
        member.setEmail(memberPatchDto.getEmail());
        member.setPassword(memberPatchDto.getPassword());


        return memberRepository.save(member).getMemberId();
    }

    public MemberResponseDto findByMemberId(Long memberId) {
        Member member = findMemberId(memberId);

        return MemberResponseDto.FindFromMember(member);
    }

    public void deleteMember(Long memberId) {
        findMemberId(memberId);
        memberRepository.deleteById(memberId);
    }
}
