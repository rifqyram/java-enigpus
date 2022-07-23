package com.enigma.enigpus.entity;

public class Magazine extends Book {

    private String code;
    private String title;
    private String publisher;
    private Integer publicationYear;

    public Magazine(String code, String title, String publisher, Integer publicationYear) {
        this.code = code;
        this.title = title;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
    }

    public Magazine() {
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    @Override
    public String toString() {
        return "Magazine;" + code +
                ";" + title +
                ";" + publisher +
                ";" + publicationYear;
    }
}
