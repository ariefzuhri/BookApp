package com.ariefzuhri.bookapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
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

    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        image = in.readString();
        description = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(image);
        parcel.writeString(description);
    }
}
