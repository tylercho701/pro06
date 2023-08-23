package com.chemicalguysMall.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "comment")
@Getter @Setter @ToString
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    //	글쓴이
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="answer_id")
    private Answer answer;

    //	질문글의 id 얻어오기
    public Long getQuestionId() {
        Long id = null;

        //	질문글의 댓글 다는 경우
        if(this.question != null) {
            id = this.question.getId();

            //	답변글에 댓글 다는 경우
        } else if(this.answer != null) {
            id = this.answer.getQuestion().getId();
        }


        return id;
    }

}
