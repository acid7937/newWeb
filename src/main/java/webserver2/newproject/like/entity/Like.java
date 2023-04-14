package webserver2.newproject.like.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webserver2.newproject.board.entity.Board;
import webserver2.newproject.member.entity.Member;

import javax.persistence.*;

@Entity(name = "likes") //이거 이름 안바꿔 주면 충돌남 SQL 예약어라서..
@Setter @Getter
@NoArgsConstructor
public class Like {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    // MEMBER_ID

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;


    //likeId는 생성자가 필요없어서 @AllArgsConstructor 사용 안했음.
    public Like(Member member, Board board) {
        this.member = member;
        this.board = board;

    }


}
