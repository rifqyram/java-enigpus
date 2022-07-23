package com.enigma.enigpus.service;

import com.enigma.enigpus.entity.Book;
import com.enigma.enigpus.entity.Magazine;
import com.enigma.enigpus.entity.Novel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryServiceImpl implements InventoryService {

    private List<Book> books;
    private final File file;

    public InventoryServiceImpl() {
        this.books = new ArrayList<>();
        this.file = new File("enigpus");
    }

    @Override
    public void add(Book book) {
        if (isDataExist(book)) return;
        books.add(book);
        subscribe();
    }

    @Override
    public Book getOne(String code) {
        if (!books.isEmpty()) {
            for (Book book : getAll()) {
                String[] stringBook = book.toString().split(";");
                if (stringBook[1].equalsIgnoreCase(code)) return book;
            }
        }
        System.out.println("Book not found");
        return null;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> stringBooks = new ArrayList<>();

            while (true) {
                String result = bufferedReader.readLine();
                if (result == null) break;
                stringBooks.add(result);
            }

            convertFileToBooks(books, stringBooks);

            if (isBooksEmpty(books)) return null;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return this.books = books;
    }

    @Override
    public List<Book> searchByTitle(String title) {
        List<Book> filteredBook = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) filteredBook.add(book);
        }

        if (isBooksEmpty(filteredBook)) return null;

        return filteredBook;
    }

    @Override
    public void update(Book book) {
        String[] stringBook = book.toString().split(";");

        Book newBook = getOne(stringBook[1]);

        int idx = books.indexOf(newBook);
        books.set(idx, book);
        subscribe();
    }

    @Override
    public boolean delete(String code) {
        Book book = getOne(code);
        if (book != null) {
            books.remove(book);
            subscribe();
            return true;
        }
        return false;
    }

    private boolean isBooksEmpty(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("Book is empty");
            return true;
        }
        return false;
    }

    private void convertFileToBooks(List<Book> books, List<String> stringBooks) {
        for (String stringBook : stringBooks) {
            String[] split = stringBook.split(";");
            if (split[0].startsWith("N")) {
                books.add(new Novel(split[1], split[2], split[3], Integer.parseInt(split[4]), split[5]));
            } else {
                books.add(new Magazine(split[1], split[2], split[3], Integer.parseInt(split[4])));
            }
        }
    }

    private void subscribe() {
        try {
            FileWriter fileWriter = new FileWriter(file);

            if (!books.isEmpty()) {
                for (Book book : books) {
                    fileWriter.write(book.toString());
                    fileWriter.append('\n');
                }
            }

            fileWriter.flush();

            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
