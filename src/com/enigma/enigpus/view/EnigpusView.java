package com.enigma.enigpus.view;

import com.enigma.enigpus.entity.Book;
import com.enigma.enigpus.entity.Magazine;
import com.enigma.enigpus.entity.Novel;
import com.enigma.enigpus.service.InventoryService;
import com.enigma.enigpus.service.InventoryServiceImpl;
import com.enigma.enigpus.util.Util;

import java.util.List;

public class EnigpusView {

    private final InventoryService inventoryService;

    public EnigpusView() {
        this.inventoryService = new InventoryServiceImpl();
    }

    public void menu() {
        whileLoop:
        while (true) {
            printMainMenu();
            switch (Util.input("Pilih")) {
                case "1":
                    addBookMenu();
                    break;
                case "2":
                    getAllBook();
                    break;
                case "3":
                    searchBookByTitle();
                    break;
                case "4":
                    updateBook();
                    break;
                case "5":
                    deleteBook();
                    break;
                case "x":
                case "X":
                    break whileLoop;
                default:
                    System.out.println("Wrong Input");
                    break;
            }
        }
    }

    private void printMainMenu() {
        System.out.println(repeat(30, "="));
        System.out.println("Pilih Menu");
        System.out.println("1. Menambahkan buku\n" +
                "2. Melihat isi buku\n" +
                "3. Mencari buku berdasarkan judul\n" +
                "4. Merubah buku\n" +
                "5. Menghapus buku\n" +
                "X. Keluar dari aplikasi");
        System.out.println(repeat(30, "="));
    }

    private void addBookMenu() {
        System.out.println(repeat(20, "-"));
        System.out.println("Inputkan jenis buku");
        System.out.println(repeat(20, "-"));
        System.out.println("1. Novel\n" +
                "2. Majalah\n" +
                "X. Kembali ke menu utama");
        System.out.println(repeat(30, "="));
        switch (Util.input("Pilih")) {
            case "1":
                addNovel();
                break;
            case "2":
                addMagazine();
                break;
            case "X":
            case "x":
                menu();
                break;
            default:
                System.out.println("Wrong Input");
                break;
        }
        System.out.println(repeat(30, "="));
    }

    private void addNovel() {
        System.out.println(repeat(30, "="));
        System.out.println("Tambahkan Informasi Novel");
        System.out.println(repeat(30, "="));
        String code = Util.input("Inputkan code buku");
        String title = Util.input("Inputkan judul buku");
        String publisher = Util.input("Inputkan penerbit buku");
        String publicationYear = Util.input("Inputkan tahun terbit buku");
        String author = Util.input("Inputkan penulis buku");

        try {
            Book novel = new Novel(code, title, publisher, Integer.parseInt(publicationYear), author);
            inventoryService.add(novel);
        } catch (Exception e) {
            System.out.println("Invalid input publication year");
        }
    }

    private void addMagazine() {
        System.out.println(repeat(30, "="));
        System.out.println("Tambahkan Informasi Majalah");
        System.out.println(repeat(30, "="));
        String code = Util.input("Inputkan code buku");
        String title = Util.input("Inputkan judul buku");
        String publisher = Util.input("Inputkan penerbit buku");
        String publicationYear = Util.input("Inputkan tahun terbit buku");

        try {
            Book magazine = new Magazine(code, title, publisher, Integer.parseInt(publicationYear));
            inventoryService.add(magazine);
        } catch (Exception e) {
            System.out.println("Invalid input publication year");
        }
    }

    private void getAllBook() {
        System.out.println(repeat(30, "="));
        System.out.println("Daftar Semua Buku");
        System.out.println(repeat(30, "="));

        List<Book> books = inventoryService.getAll();
        printBooks(books);

        System.out.println(repeat(30, "="));
    }

    private void searchBookByTitle() {
        System.out.println(repeat(30, "="));
        String title = Util.input("Inputkan judul buku yang ingin dicari");

        List<Book> books = inventoryService.searchByTitle(title);
        printBooks(books);

        System.out.println(repeat(30, "="));
    }

    private void updateBook() {
        System.out.println(repeat(30, "="));
        String code = Util.input("Inputkan code buku yang ingin dirubah");
        System.out.println(repeat(30, "="));

        Book book = inventoryService.getOne(code);

        if (book == null) return;

        if (book.toString().startsWith("N")) {
            book = updateNovel(book);
        } else {
            book = updateMagazine(book);
        }

        inventoryService.update(book);
    }

    private Book updateNovel(Book book) {
        Novel novel = (Novel) book;
        String title = Util.input("Inputkan judul buku");
        String publisher = Util.input("Inputkan penerbit buku");
        String publicationYear = Util.input("Inputkan tahun terbit buku");
        String author = Util.input("Inputkan penulis buku");

        try {
            book = new Novel(novel.getCode(), title, publisher, Integer.parseInt(publicationYear), author);
        } catch (Exception e) {
            System.out.println("Invalid input publication year");
        }

        return book;
    }

    private Book updateMagazine(Book book) {
        Magazine magazine = (Magazine) book;
        String title = Util.input("Inputkan judul buku");
        String publisher = Util.input("Inputkan penerbit buku");
        String publicationYear = Util.input("Inputkan tahun terbit buku");

        try {
            book = new Magazine(magazine.getCode(), title, publisher, Integer.parseInt(publicationYear));
        } catch (Exception e) {
            System.out.println("Invalid input publication year");
        }

        return book;
    }

    private void deleteBook() {
        System.out.println(repeat(30, "="));
        String code = Util.input("Inputkan code buku yang ingin dihapus");
        System.out.println(repeat(30, "="));
        boolean delete = inventoryService.delete(code);
        if (delete) System.out.println("Book deleted successfully");
        System.out.println(repeat(30, "="));
    }

    private void printBooks(List<Book> books) {
        if (books == null) return;
        for (Book book : books) {
            if (book.toString().startsWith("N")) {
                Novel novel = (Novel) book;
                System.out.printf("Type: Novel, Code: %s, Judul, %s, Penerbit: %s, Tahun Terbit: %d, Penulis: %s\n",
                        novel.getCode(), novel.getTitle(), novel.getPublisher(), novel.getPublicationYear(), novel.getAuthor());
            } else {
                Magazine novel = (Magazine) book;
                System.out.printf("Type: Majalah, Code: %s, Judul, %s, Penerbit: %s, Tahun Terbit: %d\n",
                        novel.getCode(), novel.getTitle(), novel.getPublisher(), novel.getPublicationYear());
            }

        }
    }

    private String repeat(int n, String str) {
        return new String(new char[n]).replace("\0", str);
    }

}
