package com.kiyak.eindopdracht_backend_kiyak.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(
            strategy= GenerationType.IDENTITY
//            generator="native"
    )
//    @GenericGenerator(
//            name = "native",
//            strategy = "native"
//    )
//    @Column(columnDefinition = "serial")
    private long userId;
    private String username;
    private String email;
    private String password;

    //    User kan meerdere liedjes toevoegen.
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    @PrimaryKeyJoinColumn
    private List<File> files;

    @ManyToMany
    @JoinTable (name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;




//    @OneToMany
//    @JoinTable (name = "user_demo",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "files_id"))
//    private Set<DemoFiles> demoFiles;





    public User() { this.files = new ArrayList<>();

    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

//    public void addFile(File file){
//        file.setUser(this);
//        this.files.add(file);
//    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
