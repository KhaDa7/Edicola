package main;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import bean.Quotidiano;
import DAO.EdicolanteDAO;
import DAO.QuotidianoDAO;

public class ScannerMain {

    Scanner input = new Scanner(System.in);

    QuotidianoDAO dao = new QuotidianoDAO();
    EdicolanteDAO edicolanteDAO = new EdicolanteDAO();
    Quotidiano quotidiano = new Quotidiano();
    char altri = ' ';
    int risposta;
    boolean ancora = true;
    Connection connection;
    static final String url = "jdbc:mysql://localhost:3306/edicola";

    public void Menu() throws SQLException, ClassNotFoundException {
        try {
            do {
                System.out.println(
                        "DIGITA: \n 1.Aggiungi un quotidiano. \n 2.Gestisci un quotidiano \n 3.Ottieni il rendiconto giornaliero \n 4.Elimina un quotidiano. ");
                System.out.print("INSERISCI IL COMANDO CHE DESIDERI: ");

                risposta = input.nextInt();
                input.nextLine();

                switch (risposta) {
                    case 1:
                        System.out.println("Aggiungi un quotidiano: ");
                        System.out.println("Inserisci il nome del quotidiano: ");
                        quotidiano.setNome(input.nextLine());
                        System.out.println("Inserisci il prezzo del quotidiano: ");
                        quotidiano.setPrezzo(input.nextDouble());

                        System.out.println("Inserisci l'aggio del quotidiano: ");
                        quotidiano.setAggio(input.nextInt());
                        input.nextLine();

                        dao.insertQuotidiano(quotidiano);
                        break;
                    case 2:
                        System.out.println("Gestisci un quotidiano: ");
                        gestioneQuotidiano();
                        break;
                    case 3:
                        edicolanteDAO.rendicontoGiornaliero();
                        break;
                    case 4:
                        System.out.println("Inserisci l'ID del quotidiano che vuoi eliminare: ");
                        dao.deleteQuotidiano(input.nextInt());
                        input.nextLine();
                        System.out.print("Quotidiano eliminato!");
                        break;
                    default:
                        System.out.println("Scelta immessa non valida!");
                        break;
                }
                while (true) {
                    System.out.println();
                    System.out.print("Vuoi svolgere un'altra operazione? (S/N): ");
                    altri = input.nextLine().toUpperCase().charAt(0);
                    if (altri == 'N') {
                        ancora = false;
                        break;
                    } else if (altri == 'S') {
                        ancora = true;
                        break;
                    } else {
                        System.out.print("Risposta immessa non valida! Digita 'S' per Sì o 'N' per No.");
                    }
                }
            } while (ancora);
        } catch (Exception e) {
            System.out.println("Comando inserito non corretto, puoi inserire solo numeri.");
        }
    }

    public void gestioneQuotidiano() throws SQLException, ClassNotFoundException {

        int idq; // idQuotidiano
        int in; // interi
        double db; // double
        try {
            do {
                System.out.println("DIGITA: \n 1.Inserisci copie ricevute. \n 2.Incrementa copie vendute . \n 3.Modifica prezzo di copertina. \n 4.Modifica aggio. \n 5.Azzera le copie vendute e ricevute.");
                System.out.print("INSERISCI IL COMANDO CHE DESIDERI: ");
                int risposta = input.nextInt();
                input.nextLine();
                System.out.println();
                switch (risposta) {
                    case 1:
                        edicolanteDAO.stampaIdNome();
                        System.out.print("Digitare l'id della pubblicazione: ");
                        try {
                            idq = input.nextInt();
                            input.nextLine();
                            System.out.print("Quante copie hai ricevuto del quotidiano: ");
                            in = input.nextInt();
                            input.nextLine();
                            dao.updateQuotidianoCRicevute(idq, in);
                        } catch (InputMismatchException ime) {
                            System.out.println("Procedura da ripetere! Bisogna immettere un numero.");
                            input.nextLine();
                        }
                        break;
                    case 2:
                        edicolanteDAO.stampaIdNome();
                        System.out.print("Inserisci l'ID del quotidiano che vuoi gestire:  ");
                        try {
                            idq = input.nextInt();
                            input.nextLine();
                            edicolanteDAO.incrementaCopieById(idq);
                        } catch (InputMismatchException ime) {
                            System.out.println("Procedura da ripetere! Bisogna immettere un numero.");
                            input.nextLine();
                        }
                        break;

                    case 3:
                        edicolanteDAO.stampaIdNome();
                        System.out.print("Inserisci l'ID del quotidiano che vuoi gestire: ");
                        try {
                            idq = input.nextInt();
                            input.nextLine();
                            System.out.print("Inserisci il prezzo desiderato: ");
                            db = input.nextDouble();
                            input.nextLine();
                            dao.updateQuotidianoPrezzo(idq, db);
                        } catch (InputMismatchException ime) {
                            System.out.println("Procedura da ripetere! Bisogna immettere un numero.");
                            input.nextLine();
                        }
                        break;
                    case 4:
                        edicolanteDAO.stampaIdNome();
                        System.out.print("Inserisci l'ID del quotidiano che vuoi gestire: ");
                        try {
                            idq = input.nextInt();
                            input.nextLine();
                            System.out.print("Inserisci l'aggio desiderato: ");
                            in = input.nextInt();
                            input.nextLine();
                            dao.updateQuotidianoAggio(idq, in);
                        } catch (InputMismatchException ime) {
                            System.out.println("Procedura da ripetere! Bisogna immettere un numero.");
                            input.nextLine();
                        }

                        break;

                    case 5:
                        edicolanteDAO.stampaIdNome();
                        System.out.print("Inserisci l'ID del quotidiano di cui vuoi azzerare le copie ricevute e vendute: ");
                        try {
                            idq = input.nextInt();
                            input.nextLine();
                            edicolanteDAO.azzeraCopie(idq);
                        } catch (InputMismatchException ime) {
                            System.out.println("Procedura da ripetere! Bisogna immettere un numero.");
                            input.nextLine();
                        }
                        break;
                    default:
                        System.out.println("Scelta immessa non valida!");
                        break;
                }
                while (true) {
                    System.out.println();
                    System.out.print("Vuoi svolgere un'altra operazione di gestione del quotidiano? (S/N): ");
                    altri = input.nextLine().toUpperCase().charAt(0);
                    if (altri == 'N') {
                        ancora = false;
                        break;
                    } else if (altri == 'S') {
                        ancora = true;
                        break;
                    } else {
                        System.out.print("Risposta immessa non valida! Digita 'S' per Sì o 'N' per No: ");
                    }
                }
            } while (ancora);
        }catch (Exception e) {
            System.out.println("Comando inserito non corretto, puoi inserire solo numeri.");
            input.nextLine();
        }
    }
}