package com.enigma.enigpus.entity;

public class Novel extends Book{

    private String code;
    private String title;
    private String publisher;
    private Integer publicationYear;
    private String author;

    public Novel() {
    }

    public Novel(String code, String title, String publisher, Integer publicationYear, String author) {
        this.code = code;
        this.title = title;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.author = author;
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

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Novel;" + code +
                ";" + title +
                ";" + publisher +
                ";" + publicationYear +
                ";" + author;
    }
}
