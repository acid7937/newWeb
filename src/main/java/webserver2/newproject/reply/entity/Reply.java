package webserver2.newproject.reply.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webserver2.newproject.board.entity.Board;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Reply {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;
    private String reContent;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
