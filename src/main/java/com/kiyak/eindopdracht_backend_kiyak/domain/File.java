package com.kiyak.eindopdracht_backend_kiyak.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "files")

public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,
            generator = "native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    @Column(columnDefinition = "serial")
    private long id;
    private String name;
    private String contentType;
    private Long size;
    private String comment;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public File() {
    }

    public File(long id, String name, String contentType, Long size, String comment) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.comment = comment;
    }

    //    @OneToOne(mappedBy = "file", cascade = CascadeType.ALL)
//    private Comment comment;

    @Lob
    private byte[] data;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
//
//    public List<DemoFiles> getDemoFiles() {
//        return demoFiles;
//    }
//
//    public void setDemoFiles(List<DemoFiles> demoFiles) {
//        this.demoFiles = demoFiles;
//    }

//    @Override
//    public String toString() {
//        return "Demo{" +
//                "id=" + id +
//                ", user=" + user +
//                ", comment=" + comment +
//                '}';
//    }


}







//
//@Entity
//@Table(name = "files")
//public class DemoFiles {
//    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
//    private String id;
//
//    private String name;
//
//    private String type;
//
//    @Lob
//    private byte[] data;
//
//    public DemoFiles() {
//    }
//
//    public DemoFiles(String name, String type, byte[] data) {
//        this.name = name;
//        this.type = type;
//        this.data = data;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public byte[] getData() {
//        return data;
//    }
//
//    public void setData(byte[] data) {
//        this.data = data;
//    }
//
//}