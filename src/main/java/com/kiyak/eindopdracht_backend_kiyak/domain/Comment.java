package com.kiyak.eindopdracht_backend_kiyak.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long id;

    @Column(name = "body",columnDefinition = "TEXT")
    @NotEmpty(message = "*Please write something")
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate;

//
    @OneToOne
    @JsonIgnore
    private File file;
//
//    @ManyToOne
//    @PrimaryKeyJoinColumn(name = "user_id")
////    @JoinColumn(name = "app_user_id")
////    , referencedColumnName = "app_user_id", nullable = false, insertable = false)
////    @NotNull
//    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date date) {
        this.createDate = date;
    }

    public File getDemoFiles() {
        return file;
    }

    public void setDemoFiles(File file) {
        this.file = file;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }


}
