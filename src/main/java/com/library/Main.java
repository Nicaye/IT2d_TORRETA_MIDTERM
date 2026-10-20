package com.library;

import com.library.dao.DatabaseConnection;
import com.library.ui.ScenarioMenu;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.initializeDatabase();

        ScenarioMenu menu = new ScenarioMenu();
        menu.start();
    }
}
