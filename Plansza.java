package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Plansza extends JFrame {       //klasa planszy
    Model model = new Model();              //obiekt z klasy model w ktorym jest cala mechanika gry
    JButton tab[][] = new JButton[7][7] ;   //tablica guzikow planszy
    JPanel plansza = new JPanel();          //reszta guzikow...
    JPanel sterowanie = new JPanel();
    JTextField t = new JTextField(10);
    JButton cofnij = new JButton();
    JButton cofnij_wprzod = new JButton();
    JButton zapisz = new JButton();
    JButton wczytaj = new JButton();
    JButton help = new JButton();

    public Plansza() {
        int i,j;
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(1,2));      //rozmieszczenie paneli i planszy
        cp.add(plansza); cp.add(sterowanie);
        sterowanie.setLayout(new GridLayout(7,1));
        sterowanie.add(t);
        sterowanie.add(cofnij);
        cofnij.setText("cofnij w tyl");
        sterowanie.add(cofnij_wprzod);
        cofnij_wprzod.setText("cofnij w przod");
        sterowanie.add(zapisz);
        zapisz.setText("ZapiszGre");
        sterowanie.add(wczytaj);
        wczytaj.setText("WczytajGre");
        sterowanie.add(help);
        help.setText("Help");
        cofnij.addActionListener(new Cofnij());
        zapisz.addActionListener(new Zapisz());
        wczytaj.addActionListener(new Wczytaj());
        help.addActionListener(new Help());
        cofnij_wprzod.addActionListener(new Cofnij_wprzod());
        t.setFont(t.getFont().deriveFont(30.0f));
        plansza.setLayout(new GridLayout(7,7));     //rozmiar planszy do klikania
        for (i=0;i<7;i++)                                   //rozmieszczenie guzikow
            for (j=0;j<7;j++){
                    tab[i][j] = new JButton("");
                    tab[i][j].setBackground(Color.white);
                    plansza.add(tab[i][j]);
                    (tab[i][j]).addActionListener(new B(i, j));
                    if(i==0 && j==1){tab[i][j].setBackground(Color.CYAN);}      //rozmieszczenie na "sztywno" obserwatorow
                    else if(i==2 && j==4){tab[i][j].setBackground(Color.CYAN);}
                    else if(i==3 && j==6){tab[i][j].setBackground(Color.CYAN);}
                    else if(i==6 && j==1){tab[i][j].setBackground(Color.CYAN);}
            }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    class B implements ActionListener {         //klikniecie pola planszy
        int i,j;
        B(int i,int j){this.i=i;this.j=j;}
        public void actionPerformed(ActionEvent e) {
            tab = model.klikniecie(tab,i,j);
        }   //po wciscisnieciu aktualizujemy tablice guzikow
    }
    class Cofnij implements ActionListener {    //klikniecie guzika cofnij
        public void actionPerformed(ActionEvent e) {
            tab = model.cofnij(tab);
        } //po wciscisnieciu aktualizujemy tablice guzikow
    }
    class Cofnij_wprzod implements ActionListener {//klikniecie guzika cofnij
        public void actionPerformed(ActionEvent e) {
            tab = model.cofnij_wprzod(tab);
        }//po wciscisnieciu aktualizujemy tablice guzikow
    }
    class Zapisz implements ActionListener {//klikniecie guzika zapisz
        public void actionPerformed(ActionEvent e) {
            model.zapisz();
        }
    }
    class Wczytaj implements ActionListener {//klikniecie guzika wczytaj
        public void actionPerformed(ActionEvent e) {
            tab = model.wczytaj(tab);
        } //po wciscisnieciu aktualizujemy tablice guzikow
    }
    class Help implements ActionListener {//klikniecie guzika help
        public void actionPerformed(ActionEvent e) {
            model.help();
        }
    }
}
