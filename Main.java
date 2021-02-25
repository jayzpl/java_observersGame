package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {    //glowna klasa gry
        JFrame f = new Plansza();               //stworzenie planszy o wybranych parametrach
        f.setSize(600,400);
        f.setLocation(100,100);
        f.setVisible(true);
    }
}
