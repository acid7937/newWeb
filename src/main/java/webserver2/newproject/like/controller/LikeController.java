package webserver2.newproject.like.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webserver2.newproject.like.service.LikeService;
import webserver2.newproject.member.entity.Member;
import webserver2.newproject.member.service.MemberService;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final MemberService memberService;

    @PostMapping("up/{boardId}")
    public ResponseEntity addLike(@PathVariable("boardId")@Positive Long boardId,
                                  @AuthenticationPrincipal String email ) {
        //이메일을 불러옴
        Member member = memberService.findByEmail(email);
        //id 랑 멤버 추가해 버림
        likeService.addLike(boardId,member);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
