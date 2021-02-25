package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

//w modelu dokonujemy zmian zwiazanych z mechanika gry.
public class Model implements Serializable{

    public class Ktore_i_j implements Serializable{ //klasa posredniczaca jej obiekty sa zapisywane do historii i posiadaja zapisane koordynaty pola
        int i;
        int j;
    }

    boolean tablica[][] = new boolean[7][7]; //prosta dodatkowa tablica ktora pokazuje gdzie bialy a gdzie czarny. True jako czarny; false jako bialy
    int ilosc_czarnych=0;   //ilosc czarnych pol
    int ilosc_klikniec=0;   //ilosc klikniec
    ArrayList hitoria = new ArrayList();    //arraylista z historia ruchow (zawiera obiekty ktore zawieraja polozenie danego pola)
    DoZapisuOdczytu A = new DoZapisuOdczytu(ilosc_czarnych, ilosc_klikniec, hitoria, tablica);  //obiekt z klasy posredniczacej ktory posredniczy w zapisie i odczycie danych z pliku

    public boolean sprawdz_czy_mozna_kliknac(){ //metoda sprawdza czy mozna kliknac pole
        if(ilosc_czarnych<12 && ilosc_klikniec>=0){return true;}    //jesli czarnych pol jest mniej niz 12 i ilosc kliknic jest wieksza lub rowna 0 to mozna kliknac
        else{return false;}
    }

    public JButton[][] klikniecie(JButton[][] tab, int i, int j){   //metoda do zmiany guzikow po kliknieciu
        if(sprawdz_czy_mozna_kliknac() && czarny_obok_czarny(i,j)) {    //sprawdz czy pole mozna zamienic (kliknac)
            ilosc_czarnych++;   //jak tak to zwieksz ilosc czarnych pol
            tab[i][j].setBackground(Color.black);   //ustaw to pole na pole czarne
            tablica[i][j] = true;       //dodaj do tablicy ktora zapamietuje gdzie sa czarne pola ze to pole jest czarne
            Ktore_i_j ktore = new Ktore_i_j();  //tworzymy obiekt ktory bedzie zapisywac pozycje pola z klasy posredniczacej
            ktore.i = i; ktore.j = j;   //przekazanie do obiektu pposredniczacego pozycji pola
            hitoria.add(ilosc_klikniec, ktore); //dodanie wykonanego ruchu czyli pozycji ktore pole sie zmienilo do historii
            ilosc_klikniec++;   //zwiekszenie ilosci klikniec
            return tab; //zwrocenie nowej tablicy guzikow
        }
        return tab; //zwrocenie nowej tablicy guzikow
    }
    public JButton[][] cofnij(JButton tab[][]){ //metoda pozwala na cofanie ruchow
        if(ilosc_klikniec>0) {      //jesli historia zawiera ruch to:
            Ktore_i_j sample = new Ktore_i_j(); //tworzymy obiekt ktory bedzie zapisywac pozycje pola z klasy posredniczacej
            sample = (Ktore_i_j) hitoria.get(ilosc_klikniec-1); //zapis danych do obiektu
            ilosc_klikniec--;
            ilosc_czarnych--;
            tab[sample.i][sample.j].setBackground(Color.white); //ustawienie danego pola na pole zwykle
            tablica[sample.i][sample.j] = false;    //ustawienie w tablicy do zapamietywania pozycji danego pola ze pole stalo sie biale
            return tab; //zwrocenie nowej tablicy guzikow
        }
        return tab;//zwrocenie nowej tablicy guzikow
    }
    public JButton[][] cofnij_wprzod(JButton tab[][]){  //metoda pozwala na cofanie sie "w przod" kolejnych wykonanych ruchow
        if(ilosc_klikniec>=0) {     //jesli historia zawiera ruch to:
            if(ilosc_klikniec < hitoria.size()) {   //jesli ilosc klikniec jest mniejsza od rozmiaru historii
                Ktore_i_j sample = new Ktore_i_j(); //tworzymy obiekt ktory bedzie zapisywac pozycje pola z klasy posredniczacej
                sample = (Ktore_i_j) hitoria.get(ilosc_klikniec);   //zapis danych do obiektu
                ilosc_klikniec++;
                ilosc_czarnych++;
                tab[sample.i][sample.j].setBackground(Color.black); //ustawienie danego pola na pole czarne
                tablica[sample.i][sample.j] = true; //ustawienie w tablicy do zapamietywania pozycji danego pola ze pole stalo sie czarne
                return tab;     //zwrocenie nowej tablicy guzikow
            }
        }
        return tab; //zwrocenie nowej tablicy guzikow
    }

    public void zapisz(){   //metoda zapisuje nam stan gry do pliku czyli ilosc czarnych pol, ilosc klikniec, historie ruchow oraz tablice z polozeniem czarnych pol
        try{
            FileOutputStream f = new FileOutputStream("ZapisanaGra");   //tworzenie obiektu z klasy FileOutputStream z podaniem "na sztywno" nazwy pliku
            ObjectOutputStream os = new ObjectOutputStream(f);              //worzenie obiektu z klasy ObjectOutputStream przekazanie mu obiektu f
            os.writeObject(A);                                              //zapisanie obiektu przy uzyciu metody writeObject
            f.close();                                                      //zamkniecie pliku
            System.out.println("Poprawnie Zapisane");
        }catch (IOException a){System.out.println("Cos sie nie zapisalo");
            System.out.println(a);}
    }

    public  JButton[][] wczytaj(JButton tab[][]){   //metoda wczytuje nam zapisany plik ze stanem gry. Metoda zwraca uzupelniona tablice guzikow
        try{
            ObjectInputStream is = new ObjectInputStream(new FileInputStream("ZapisanaGra")); //tworzymy obiekt z klasy ObjectInputStream przekazujac mu obiekt z klasy FileInputStream z nazwa pliku
            A = (DoZapisuOdczytu) is.readObject();              //nadpisujemy dane obiektu a danymi zczytanymi z pliku
            is.close();                             //zamykamy plik

            ilosc_klikniec = A.ile_klikniec;    //wczytanie do aplikacji danych odczytanych z pliku
            ilosc_czarnych = A.czarne;
            hitoria = A.historia;
            tablica = A.tablica;
            if(tablica!=null) {     //wczytanie i rozmieszczenie na planszy czarnych pol zapisanych wczesniej w tablicy
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        if (tablica[i][j]) {        //jesli na danym polu bylo czarne pole to teraz tez je ustaw
                            tab[i][j].setBackground(Color.black);
                        } else {
                            tab[i][j].setBackground(Color.white);
                        }
                    }
                }
            }
        }
        catch (IOException s){
            System.out.println("cos nie dziala");   //pomocnicze wypisanie wiadomosci w konsoli
            System.out.println(s);
        }catch (ClassNotFoundException s){}
        return tab;
    }

    public void help(){     //metoda wypisuje nam instrukcje gry w konsoli
        System.out.println("Diagram jest planem terenu z polami obserwacyjnymi (PO), w których umieszczono liczby.\n Każda liczba oznacza, ile żółtych pól można w sumie zobaczyć z danego PO (łącznie z tym PO)  patrząc w czterech głównych kierunkach,\n czyli w rzędzie i kolumnie, na przecięciu których znajduje się dane PO. Zasięg widzenia ograniczają czarne pola, których nie oznaczono \nna rysunku. Łamigłówka polega na zaczernieniu właściwych pól, przy czym należy pamiętać o tym, aby czarne pola");
        System.out.println("nie stykały się bokami;\n" +
                "stykając się rogami, nie „odcinały” jednego lub grupy żółtych pól od pozostałych, czyli nie „rozcinały” diagramu na części.");
    }

    public boolean czarny_obok_czarny(int i, int j){    //metoda sprawdza na sztywno czy na danym polu mozemu umiescic czarne pole
        if(i==0 && j!=0 && j!=6){                       //czarne pola nie moga stykac sie bokami (scianami) a tylko rogami. Ta metoda to sprawdza
            if(tablica[i+1][j]){return false;}
            else if(tablica[i][j-1]){return false;}
            else if(tablica[i][j+1]){return false;}
            else{return true;}
        }
        else if(i==6 && j!=6 && j!=0){
            if(tablica[i-1][j]){return false;}
            else if(tablica[i][j-1]){return false;}
            else if(tablica[i][j+1]){return false;}
            else{return true;}
        }
        else if(j==0 && i!=0 && i!=6){
            if(tablica[i][j+1]){return false;}
            else if(tablica[i-1][j]){return false;}
            else if(tablica[i+1][j]){return false;}
            else{return true;}
        }
        else if(j==6 && i!=6 && i!=0){
            if(tablica[i][j-1]){return false;}
            else if(tablica[i-1][j]){return false;}
            else if(tablica[i+1][j]){return false;}
            else{return true;}
        }
        else if(i==0 && j==0){
            if(tablica[i+1][j]){return false;}
            else if(tablica[i][j+1]){return false;}
            else{return true;}
        }
        else if(i==0 && j==6){
            if(tablica[i+1][j]){return false;}
            else if(tablica[i][j-1]){return false;}
            else{return true;}
        }
        else if(i==6 && j==0){
            if(tablica[i-1][j]){return false;}
            else if(tablica[i][j+1]){return false;}
            else{return true;}
        }
        else if(i==6 && j==6){
            if(tablica[i-1][j]){return false;}
            else if(tablica[i][j-1]){return false;}
            else{return true;}
        }
        else{
            if(tablica[i-1][j]){return false;}
            else if(tablica[i+1][j]){return false;}
            else if(tablica[i][j-1]){return false;}
            else if(tablica[i][j+1]){return false;}
            else{return true;}
        }
    }
}
