package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class DoZapisuOdczytu implements Serializable {//posredniczaca klasa do zapisu i odczytu
    int czarne, ile_klikniec;
    ArrayList historia = new ArrayList();
    boolean tablica[][] = new boolean[7][7];
    DoZapisuOdczytu(int ilosc_czarnych, int ilosc_klikniec, ArrayList historiaB, boolean tablicaB[][]){ //zapisujemy lub wczytujemy:
        czarne = ilosc_czarnych;                    //ilosc czarnych pol
        ile_klikniec = ilosc_klikniec;              //ilosc klikniec
        historia = historiaB;                       //historie ruchow
        tablica = tablicaB;                         //tablice z rozmieszczonymi czarnymi polami
    }
}
