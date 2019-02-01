package com.example.a60010743.movieshow.model;

public class ReviewContent {
    public ReviewContent(String author, String content) {
        this.author = author;
        this.content = content;
    }

    private String author;

    private String content;

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
