
package com.library.ui;

import com.library.dao.ScenarioDao;
import com.library.model.ScenarioEntity;

import java.util.List;
import java.util.Scanner;

public class ScenarioMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final ScenarioDao dao = new ScenarioDao();

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("==============================================");
            System.out.println("             LIBRARY BOOK CATALOG");
            System.out.println("==============================================");
            System.out.println("[1] Add New Book");
            System.out.println("[2] View Book Catalog");
            System.out.println("[3] Exit System");
            System.out.println("==============================================");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addBook();
                case "2" -> viewBooks();
                case "3" -> {
                    running = false;
                    System.out.println("\nExiting system... Thank you!");
                }
                default -> System.out.println(
                        "Invalid choice. Please enter 1, 2, or 3."
                );
            }
        }
    }

    private void addBook() {
        System.out.println("\n--- ADD NEW BOOK ---");

        String title = readRequired("Enter Title: ");
        String author = readRequired("Enter Author: ");
        String isbn = readRequired("Enter ISBN: ");

        while (!isbn.matches("\\d{13}")) {
            System.out.println("ISBN must contain exactly 13 digits.");
            isbn = readRequired("Enter ISBN: ");
        }

        String status;

        while (true) {
            status = readRequired(
                    "Enter Status (AVAILABLE / BORROWED): "
            ).toUpperCase();

            if (status.equals("AVAILABLE") || status.equals("BORROWED")) {
                break;
            }

            System.out.println(
                    "Invalid status. Enter AVAILABLE or BORROWED."
            );
        }

        ScenarioEntity book = new ScenarioEntity(
                title, author, isbn, status
        );

        if (dao.addBook(book)) {
            System.out.println("SUCCESS: Book cataloged into database!");
        } else {
            System.out.println("FAILED: Book could not be saved.");
        }
    }

    private void viewBooks() {
        List<ScenarioEntity> books = dao.getAllBooks();

        System.out.println("\n============================ LIBRARY BOOK CATALOG ==================================================");
        System.out.printf(
                "%-5s | %-25s | %-22s | %-13s | %-10s%n",
                "ID", "TITLE", "AUTHOR", "ISBN", "STATUS"
        );
        System.out.println(
                "--------------------------------------------------------------------------"
        );

        if (books.isEmpty()) {
            System.out.println("No books found in the catalog.");
        } else {
            for (ScenarioEntity book : books) {
                System.out.printf(
                        "%-5d | %-25.25s | %-22.22s | %-13s | %-10s%n",
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getIsbn(),
                        book.getStatus()
                );
            }
        }

        System.out.println("Total Records Displayed: " + books.size());
        System.out.println(
                "===================================================================================================="
        );
    }

    private String readRequired(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty. Please try again.");
        }
    }
}
