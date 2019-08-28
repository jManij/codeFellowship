package com.example.demo.models;


import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Post {
    String body;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public Long id;
    long timeStamp;   //millis

    @ManyToOne
    ApplicationUser postOwner;

    public Post(String body, long timeStamp, ApplicationUser postOwner) {
        this.body = body;
        this.timeStamp = timeStamp;
        this.postOwner = postOwner;
    }

    public Post(){} //Default constructor

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimeStamp() {
        String s = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Timestamp(timeStamp));
        return s;



    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ApplicationUser getPostOwner() {
        return postOwner;
    }

    public void setPostOwner(ApplicationUser postOwner) {
        this.postOwner = postOwner;
    }
}
