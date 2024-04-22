package bean;


import java.util.Scanner;

public class Quotidiano {

    private int id;
    private String nome;
    private double prezzo;
    private int aggio;
    private int cRicevute;
    private int cVendute;

    Scanner input = new Scanner(System.in);
    boolean a = true;

    public Quotidiano(int id, String nome, double prezzo, int aggio, int cRicevute, int cVendute) {
        this.id = id;
        this.nome = nome;
        this.prezzo = prezzo;
        this.aggio = aggio;
        this.cRicevute = cRicevute;
        this.cVendute = cVendute;
    }
    public Quotidiano() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        a = true;
        do {
            if(nome.length() > 2){
                a = false;
                this.nome = nome;
            }else {
                System.out.println("Il nome del quotidiano deve essere lunga almeno 3 caratteri");
                System.out.print("Inserisci il nome del quotidiano: ");
                nome = input.nextLine();
            }
        }while(a);
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        a = true;
        do {
            if(prezzo > 0){
                a = false;
                this.prezzo = prezzo;
            }else {
                System.out.println("Il prezzo non può essere negativo");
                System.out.print("Inserisci il prezzo del quotidiano: ");
                prezzo = input.nextDouble();
            }
        }while (a);
    }

    public int getAggio() {
        return aggio;
    }

    public void setAggio(int aggio) {
        a = true;
        do {
            if(aggio >= 5 && aggio <= 20){
                a = false;
                this.aggio = aggio;
            }else {
                System.out.println("L'aggio deve essere compreso tra 5 e 20");
                System.out.print("Inserisci l'aggio del quotidiano: ");
                aggio = input.nextInt();
            }
        }while (a);
    }

    public int getcRicevute() {
        return cRicevute;
    }

    public void setcRicevute(int cRicevute) {
        a = true;
        do {
            if(cRicevute >= 0){
                a = false;
                this.cRicevute = cRicevute;
            }else {
                System.out.println("Il numero di copie ricevute non può essere negativo");
                System.out.print("Inserisci il numero di copie ricevute: ");
                cRicevute = input.nextInt();
            }
        }while (a);
    }

    public int getcVendute() {
        return cVendute;
    }

    public void setcVendute(int cVendute) {
        a = true;
        do {
            if(cVendute >= 0){
                a = false;
                this.cVendute = cVendute;
            }else {
                System.out.println("Il numero di copie vendute non può essere negativo");
                System.out.print("Inserisci il numero di copie vendute: ");
                cVendute = input.nextInt();
            }
        }while (a);
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " - NOME: " + nome +
                " - PREZZO: " + prezzo +
                " - AGGIO: " + aggio +
                " - COPIE RICEVUTE: " + cRicevute +
                " - COPIE VENDUTE: " + cVendute + ".";
        }

    }