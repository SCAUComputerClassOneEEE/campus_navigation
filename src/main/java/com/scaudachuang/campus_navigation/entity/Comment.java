package com.scaudachuang.campus_navigation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "comment")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;//主键

    @Column(name = "b_id",columnDefinition = "int          not null,")
    private int b_id;

    @Column(name = "message",columnDefinition = "varchar(255) null comment '评论内容'")
    private String message;//内容

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "time_of_commentary",columnDefinition = "datetime     null comment '评论时间'")
    private Date timeOfCommentary;//评论的时间

    @Column(name = "number_of_praise",columnDefinition = "int          null comment '点赞个数'")
    private int numberOfPraise;//点赞次数

    /*
    @JsonIgnoreProperties(value = "comments")
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false,fetch = FetchType.LAZY)
    @JoinColumn(name = "b_id")
    private Building building;
    */

    @Override
    public String toString() {
        return "{Comment:"+this.id+",\n" +
                "\t\t\t["+this.message+","+this.timeOfCommentary+","+this.numberOfPraise+"]\n" +
                "\t\t}";
    }
}
