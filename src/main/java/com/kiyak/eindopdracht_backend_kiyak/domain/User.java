package com.kiyak.eindopdracht_backend_kiyak.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator = "native"
    )
    @GenericGenerator(
           name = "native",
           strategy = "native"
   )
    @Column(columnDefinition = "serial")
    private long id;
    private String username;
    private String email;
    private String password;

    //    User kan meerdere liedjes toevoegen.
    @OneToMany(mappedBy = "user")
//    @PrimaryKeyJoinColumn
    private Set<Demo> demos;

    @ManyToMany
    @JoinTable (name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {

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
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Set<Demo> getFiles() {
        return demos;
    }

    public void setFiles(Set<Demo> demos) {
        this.demos = demos;
    }
}
