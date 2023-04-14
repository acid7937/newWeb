package webserver2.newproject.member.entity;

import lombok.*;
import webserver2.newproject.board.entity.Board;
import webserver2.newproject.reply.entity.Reply;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(unique = true)
    private String nickname;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    List<Reply> replies = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();
}
