package org.koffa;
public class Main {
    public static void main(String[] args) {
        ConsoleInterface ci = new ConsoleInterface();
        while(ci.isRunning()) {
            ci.printMainMenu();
        }
    }
}