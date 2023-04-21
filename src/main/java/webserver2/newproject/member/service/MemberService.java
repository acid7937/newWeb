package webserver2.newproject.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webserver2.newproject.auth.utils.CustomAuthorityUtils;
import webserver2.newproject.exception.BusinessLogicException;
import webserver2.newproject.exception.ExceptionCode;
import webserver2.newproject.member.dto.MemberPatchDto;
import webserver2.newproject.member.dto.MemberPostDto;
import webserver2.newproject.member.dto.MemberResponseDto;
import webserver2.newproject.member.entity.Member;
import webserver2.newproject.member.repository.MemberRepository;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils customAuthorityUtils;



    public Member findMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public Member displayNickname(String nickname) {
        return memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    @Transactional
    public Long createMember(MemberPostDto memberPostDto) {
        Member member = new Member();

        member.setEmail(memberPostDto.getEmail());
        member.setPassword(passwordEncoder.encode(memberPostDto.getPassword()));
        member.setRoles(customAuthorityUtils.createRoles(memberPostDto.getEmail()));

        member.setNickname(memberPostDto.getNickname());

        return memberRepository.save(member).getMemberId();

    }

    public Long updateMember(MemberPatchDto memberPatchDto,Long memberId) {
        Member member = findMemberId(memberId);
        member.setNickname(memberPatchDto.getNickname());
        member.setEmail(memberPatchDto.getEmail());
        member.setPassword(memberPatchDto.getPassword());//여길 passwordEncoder 안하니깐 그냥 저장되는게 보이는가?


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
