package webserver2.newproject.member.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import webserver2.newproject.member.dto.MemberPatchDto;
import webserver2.newproject.member.dto.MemberPostDto;
import webserver2.newproject.member.dto.MemberResponseDto;
import webserver2.newproject.member.service.MemberService;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Getter @Setter
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity postMember(@RequestBody @Validated MemberPostDto memberPostDto) {
        Long memberId = memberService.createMember(memberPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberId);
    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> postMember(@RequestBody @Validated MemberPostDto memberPostDto) {
//        try {
//            Long memberId = memberService.createMember(memberPostDto);
//            return ResponseEntity.status(HttpStatus.CREATED).body(memberId);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

    @PatchMapping("/{memberId}")
    public ResponseEntity patchMember(@RequestBody MemberPatchDto memberPatchDto,
                                      @PathVariable("memberId") Long memberId) {
        memberService.updateMember(memberPatchDto,memberId);

        return ResponseEntity.status(HttpStatus.OK).body(memberId);
    }
    @GetMapping("/{memberId}")
    public ResponseEntity getMember(@PathVariable("memberId") Long memberId) {

        MemberResponseDto memberResponseDto = memberService.findByMemberId(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }
    @DeleteMapping("/{memberId}")
    public ResponseEntity deleteMember(@PathVariable("memberId") Long memberId) {

        memberService.deleteMember(memberId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
