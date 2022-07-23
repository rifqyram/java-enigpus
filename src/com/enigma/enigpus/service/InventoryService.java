package com.enigma.enigpus.service;

import com.enigma.enigpus.entity.Book;

import java.util.List;

public interface InventoryService {

    void add(Book book);

    Book getOne(String code);

    List<Book> getAll();

    List<Book> searchByTitle(String title);

    void update(Book book);

    boolean delete(String code);

}
