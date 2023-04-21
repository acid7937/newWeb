package webserver2.newproject.reply.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import webserver2.newproject.reply.entity.Reply;

@Getter @Setter
@AllArgsConstructor
public class ReplyResponseDto {

    private Long replyId;
    private String reContent;
    private Long member;


    //정적 팩토리 메서드 추가
    public static ReplyResponseDto FindFromReply(Reply reply) {
        return new ReplyResponseDto(
                reply.getReplyId(),
                reply.getReContent(),
                reply.getMember().getMemberId()
        );
    }
}
