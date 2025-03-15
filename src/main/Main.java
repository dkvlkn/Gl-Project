package main;

import javax.swing.SwingUtilities;

import gui.HomePage;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomePage());
    }
}