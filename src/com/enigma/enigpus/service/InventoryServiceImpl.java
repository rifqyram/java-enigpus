package com.enigma.enigpus.service;

import com.enigma.enigpus.entity.Book;
import com.enigma.enigpus.entity.Magazine;
import com.enigma.enigpus.entity.Novel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryServiceImpl implements InventoryService {

    private final List<Book> books;

    public InventoryServiceImpl() {
        this.books = new ArrayList<>(Arrays.asList(
                new Magazine("M001", "Boba", "Gramedoi", 2000),
                new Novel("N001", "Harry Potter", "Gramedoi", 2000, "J.K Rowling")
        ));
    }

    @Override
    public void add(Book book) {
        if (isDataExist(book)) return;
        books.add(book);
    }

    @Override
    public Book getOne(String code) {
        if (!books.isEmpty()) {
            for (Book book : books) {
                String[] stringBook = book.toString().split(";");
                if (stringBook[1].equalsIgnoreCase(code)) return book;
            }
        }
        System.out.println("Book not found");
        return null;
    }

    @Override
    public List<Book> getAll() {
        if (books.isEmpty()) {
            System.out.println("Book is empty");
            return null;
        }
        return books;
    }

    @Override
    public List<Book> searchByTitle(String title) {
        List<Book> filteredBook = new ArrayList<>();

        for (Book book : getAll()) {
            if (book.getTitle().equalsIgnoreCase(title)) filteredBook.add(book);
        }

        if (filteredBook.isEmpty()) {
            System.out.println("Book not found");
            return null;
        }

        return filteredBook;
    }

    @Override
    public void update(Book book) {
        String[] stringBook = book.toString().split(";");
        Book newBook = getOne(stringBook[1]);
        int idx = books.indexOf(newBook);
        books.set(idx, book);
    }

    @Override
    public boolean delete(String code) {
        Book book = getOne(code);
        if (book != null) {
            books.remove(book);
            return true;
        }
        return false;
    }

    private boolean isDataExist(Book book) {
        if (!books.isEmpty()) {
            for (Book currentBook : books) {
                String currentBookCode = currentBook.toString().split(";")[1];
                String newBookCode = book.toString().split(";")[1];
                if (currentBookCode.equalsIgnoreCase(newBookCode)) {
                    System.out.println("Book code already exist");
                    return true;
                }
            }
        }
        return false;
    }
}
