package com.enigma.enigpus.view;

import com.enigma.enigpus.entity.Book;
import com.enigma.enigpus.entity.Magazine;
import com.enigma.enigpus.entity.Novel;
import com.enigma.enigpus.service.InventoryService;
import com.enigma.enigpus.service.InventoryServiceImpl;

import java.util.List;
import java.util.Scanner;

public class EnigpusView {

    private final Scanner scanner;
    private final InventoryService inventoryService;

    public EnigpusView() {
        this.scanner = new Scanner(System.in);
        this.inventoryService = new InventoryServiceImpl();
    }

    public void menu() {
        whileLoop:
        while (true) {
            System.out.println("Pilih Menu");
            System.out.println("==============================");
            System.out.println("1. Menambahkan buku\n" +
                    "2. Melihat isi buku\n" +
                    "3. Mencari buku berdasarkan judul\n" +
                    "4. Merubah buku\n" +
                    "5. Menghapus buku\n" +
                    "X. Keluar dari aplikasi");
            System.out.println("==============================");
            switch (scanner.nextLine()) {
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

    private void addBookMenu() {
        System.out.println("====================");
        System.out.println("Inputkan jenis buku");
        System.out.println("====================");
        System.out.println("1. Novel\n" +
                "2. Majalah\n" +
                "3. Kembali ke menu utama");
        System.out.println("====================");
        switch (scanner.nextLine()) {
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
        System.out.println("=========================");
    }

    private void addNovel() {
        System.out.println("=========================");
        System.out.println("Tambahkan Informasi Novel");
        System.out.println("=========================");
        System.out.println("Inputkan code buku");
        String code = scanner.nextLine();
        System.out.println("Inputkan judul buku");
        String title = scanner.nextLine();
        System.out.println("Inputkan penerbit buku");
        String publisher = scanner.nextLine();
        System.out.println("Inputkan tahun terbit buku");
        String publicationYear = scanner.nextLine();
        System.out.println("Inputkan penulis buku");
        String author = scanner.nextLine();

        try {
            Book novel = new Novel(code, title, publisher, Integer.parseInt(publicationYear), author);
            inventoryService.add(novel);
        } catch (Exception e) {
            System.out.println("Invalid input publication year");
        }
    }

    private void addMagazine() {
        System.out.println("=========================");
        System.out.println("Tambahkan Informasi Novel");
        System.out.println("=========================");
        System.out.println("Inputkan code buku");
        String code = scanner.nextLine();
        System.out.println("Inputkan judul buku");
        String title = scanner.nextLine();
        System.out.println("Inputkan penerbit buku");
        String publisher = scanner.nextLine();
        System.out.println("Inputkan tahun terbit buku");
        String publicationYear = scanner.nextLine();

        try {
            Book magazine = new Magazine(code, title, publisher, Integer.parseInt(publicationYear));
            inventoryService.add(magazine);
        } catch (Exception e) {
            System.out.println("Invalid input publication year");
        }
    }

    private void getAllBook() {
        System.out.println("=========================");
        System.out.println("Daftar Semua Buku");
        System.out.println("=========================");

        List<Book> books = inventoryService.getAll();
        printBooks(books);

        System.out.println("=========================");
    }

    private void searchBookByTitle() {
        System.out.println("=========================");
        System.out.println("Inputkan judul buku yang ingin dicari");
        String title = scanner.nextLine();

        List<Book> books = inventoryService.searchByTitle(title);
        printBooks(books);

        System.out.println("=========================");
    }

    private void updateBook() {
        System.out.println("=========================");
        System.out.println("Inputkan code buku yang ingin dirubah");
        String code = scanner.nextLine();
        System.out.println("=========================");

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
        System.out.println("Inputkan judul buku");
        String title = scanner.nextLine();
        System.out.println("Inputkan penerbit buku");
        String publisher = scanner.nextLine();
        System.out.println("Inputkan tahun terbit buku");
        String publicationYear = scanner.nextLine();
        System.out.println("Inputkan penulis buku");
        String author = scanner.nextLine();

        try {
            book = new Novel(novel.getCode(), title, publisher, Integer.parseInt(publicationYear), author);
        } catch (Exception e) {
            System.out.println("Invalid input publication year");
        }

        return book;
    }

    private Book updateMagazine(Book book) {
        Magazine magazine = (Magazine) book;
        System.out.println("Inputkan judul buku");
        String title = scanner.nextLine();
        System.out.println("Inputkan penerbit buku");
        String publisher = scanner.nextLine();
        System.out.println("Inputkan tahun terbit buku");
        String publicationYear = scanner.nextLine();

        try {
            book = new Magazine(magazine.getCode(), title, publisher, Integer.parseInt(publicationYear));
        } catch (Exception e) {
            System.out.println("Invalid input publication year");
        }

        return book;
    }

    private void deleteBook() {
        System.out.println("=========================");
        System.out.println("Inputkan code buku yang ingin dihapus");
        String code = scanner.nextLine();
        System.out.println("=========================");
        inventoryService.delete(code);
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

}
