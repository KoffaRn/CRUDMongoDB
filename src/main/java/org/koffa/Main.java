package org.koffa;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        ConsoleInterface ci = new ConsoleInterface();

        while(ci.isRunning()) {
            ci.printMainMenu();
        }
    }
}