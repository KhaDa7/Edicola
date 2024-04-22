package DAO;

import java.sql.*;

public class EdicolanteDAO extends QuotidianoDAO {

    static final String url = "jdbc:mysql://localhost:3306/edicola";
    public static String guadagnoParziale;

    public int copieRicevute() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, "root", "");

        String query = "SELECT SUM(cricevute) as copie_totali_ricevute FROM quotidiani";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("copie_totali_ricevute");
            } else {
                return 0;
            }
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }

    } 

    public int copieVendute() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, "root", "");

        String query = "SELECT SUM(cvendute) as copie_totali_vendute FROM quotidiani";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int vendute = resultSet.getInt("copie_totali_vendute");
                return vendute;
            } else {
                return 0;
            }
        } finally {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        }

    }



    public void incrementaCopieById(int idQuotidiano) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, "root", "");

        String query = "UPDATE quotidiani SET cvendute = cvendute + 1 WHERE id=? AND cricevute > cvendute;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, idQuotidiano);

            int status = stmt.executeUpdate();

            if (status > 0) {
                System.out.println("Copie vendute incrementate!");
            }else {
                System.out.println("Impossibile incrementare le copie");
            }
        }
        connection.close();

    }


    public void azzeraCopie(int id) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, "root", "");

        String query = "UPDATE quotidiani SET cricevute = 0 ,cvendute = 0 WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);

            int status = stmt.executeUpdate();
            if (status > 0) {
                System.out.println("Copie azzerate correttamente!");
            }else{
                System.out.println("Impossibile azzerare le copie");
            }
            connection.close();

        }

    }

    public void rendicontoGiornaliero() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, "root", "");
        String query = "SELECT * FROM quotidiani";
        double gTotale = 0;
        double gRivista = 0;

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet res = statement.executeQuery()) {
            while (res.next()) {
                String nome = res.getString("nome");
                double prezzo = res.getDouble("prezzo");
                int aggio = res.getInt("aggio");
                int cricevute = res.getInt("cricevute");
                int cvendute = res.getInt("cvendute");
                System.out.println("*** Rendiconto della giornata ***");
                System.out.println("Quotidiano: " + nome);
                System.out.println("Copie ricevute: " + cricevute + ", di cui vendute: " + cvendute
                       + " con un aggio del " + aggio + "%, quindi con " + (cricevute - cvendute) + " rese.");
                gRivista = prezzo * aggio / 100 * cvendute;
                System.out.println("Guadagno sul quotidiano: " + gRivista + " euro");
                gTotale += gRivista;
                System.out.println("----------");
            }
            String guadagnoTotaleString = String.format("%.2f", gTotale);
            System.out.println("Guadagno della giornata: " + guadagnoTotaleString + " euro");
            System.out.println("********************************");
            connection.close();
        }catch (SQLException e){
            System.out.println("Problema di rete, connessione fallita!");
        }
    }

    public void stampaIdNome() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, "root", "");
            String query = "SELECT id, nome FROM quotidiani";

            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet res = statement.executeQuery();
            System.out.println("Pubblicazioni registrate:");
            while (res.next()) {
                System.out.print(res.getString("id") + " ");
                System.out.println(res.getString("nome"));
            }
            conn.close();
        }
        catch (SQLException e) {
            System.out.println("Problema di rete, connessione fallita!");
        }
    }

}
