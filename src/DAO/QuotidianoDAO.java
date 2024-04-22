package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Quotidiano;

public class QuotidianoDAO  {
    static final String url = "jdbc:mysql://localhost:3306/edicola";
    Quotidiano quotidiano = new Quotidiano();
    protected Connection connection;
    // CRUD
    public void insertQuotidiano(Quotidiano quotidiano) throws SQLException, ClassNotFoundException{
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, "root", "");

        String query = "INSERT INTO quotidiani (nome,prezzo,aggio,cricevute,cvendute) VALUES (?,?,?,?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, quotidiano.getNome());
            stmt.setDouble(2, quotidiano.getPrezzo());
            stmt.setInt(3, quotidiano.getAggio());
            stmt.setInt(4, 0);
            stmt.setInt(5, 0);

            int status = stmt.executeUpdate();

            if (status > 0) {
                System.out.println("Quotidiano inserido con sucesso!");
            }else{
                System.out.println("Impossibile inserire il quotidiano");
            }
        }
        connection.close();
    }

    public List<Quotidiano> getAllQuotidiani() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, "root", "");

        List<Quotidiano> quotidiani = new ArrayList<>();
        String query = "SELECT * FROM quotidiani";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Quotidiano quotidiano = mapResultSetToQuotidiano(resultSet);
                quotidiani.add(quotidiano);
            }
        }
        connection.close();
        return quotidiani;
    }

    public Quotidiano getQuotidianoByID(int id)throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, "root", "");

        String query = "SELECT * FROM quotidiani WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapResultSetToQuotidiano(rs);
                }
            }
        }
        connection.close();
        return null;
    }

    public void updateQuotidianoAggio(int id, int aggio) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, "root", "");

        String query = "UPDATE quotidiani SET aggio=? WHERE id=? AND cvendute = 0";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            quotidiano.setAggio(aggio);

            statement.setInt(1, aggio);
            statement.setInt(2, id);

            int status = statement.executeUpdate();

            if (status > 0) {
                System.out.println("Aggio modificato!");
            }else {
                System.out.println("Impossibile aggiornare l'aggio");
            }
        }
        connection.close();
    }

    public void updateQuotidianoCRicevute(int id, int cricevute) throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, "root", "");

        String query = "UPDATE quotidiani SET cricevute=? WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            quotidiano.setcRicevute(cricevute);

            stmt.setInt(1, cricevute);
            stmt.setInt(2, id);

            int status = stmt.executeUpdate();

            if (status > 0) {
                System.out.println("Copie ricevute modificate!");
            }else{
                System.out.println("Impossibile aggiornare l'ricevute");
            }

        }
        connection.close();
    }

    public void updateQuotidianoPrezzo(int id, double prezzo) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, "root", "");

        String query = "UPDATE quotidiani SET prezzo=? WHERE id=? AND cvendute = 0";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            quotidiano.setPrezzo(prezzo);

            stmt.setDouble(1, prezzo);
            stmt.setInt(2, id);

            int status = stmt.executeUpdate();
            if (status > 0) {
                System.out.println("Prezzo modificato!");
            }else {
                System.out.println("Prezzo non modificato!");
            }
        }
        connection.close();
    }

    public void deleteQuotidiano(int id) throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, "root", "");

        String query = "DELETE FROM quotidiani WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int status = stmt.executeUpdate();
            if (status > 0) {
                System.out.println("Quotidiano eliminato!");
            }
        }
        connection.close();

    }

    // Metodo per mappare un ResultSet a un oggetto Quotidiano
    public Quotidiano mapResultSetToQuotidiano(ResultSet resultSet) throws SQLException {

        Quotidiano quotidiano = new Quotidiano();

        quotidiano.setId(resultSet.getInt("id"));
        quotidiano.setNome(resultSet.getString("nome"));
        quotidiano.setPrezzo(resultSet.getDouble("prezzo"));
        quotidiano.setAggio(resultSet.getInt("aggio"));
        quotidiano.setcRicevute(resultSet.getInt("cricevute"));
        quotidiano.setcVendute(resultSet.getInt("cvendute"));

        return quotidiano;
    }

    
}
