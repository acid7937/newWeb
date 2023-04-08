package webserver2.newproject.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webserver2.newproject.reply.entity.Reply;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String title;

    private String content;

    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Reply> reply = new ArrayList<>();


}
