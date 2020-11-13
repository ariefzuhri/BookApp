package com.ariefzuhri.bookapp;

public class Book {
    private String title;
    private String author;
    private String image;
    private String description;

    public Book(){}

    public Book(String title, String author, String image, String description) {
        this.title = title;
        this.author = author;
        this.image = image;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
