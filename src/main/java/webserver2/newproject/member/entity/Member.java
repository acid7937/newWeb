package webserver2.newproject.member.entity;

import lombok.Getter;
import lombok.Setter;
import webserver2.newproject.board.entity.Board;
import webserver2.newproject.reply.entity.Reply;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String email;
    private String password;
    private String nickname;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    List<Reply> replies = new ArrayList<>();

}
