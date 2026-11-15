package modelo;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
 
public class ConexionBD {
    public static Connection conectar() {
        return conectar("jdbc:sqlite:preventa.db");
    }
 
    /** Permite usar jdbc:sqlite::memory: en las pruebas. */
    public static Connection conectar(String url) {
        try {
            Connection conn = DriverManager.getConnection(url);
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS pedidos (id INTEGER PRIMARY KEY AUTOINCREMENT, cliente TEXT, sku TEXT, total REAL);");
            }
            return conn;
        } catch (Exception e) {
            System.out.println("Error SQLite: " + e.getMessage());
            return null;
        }
    }
 
    public static boolean guardarPedido(Connection conn, String cliente, String sku, double total) {
        String sql = "INSERT INTO pedidos (cliente, sku, total) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cliente);
            ps.setString(2, sku);
            ps.setDouble(3, total);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }
 
    public static int contarPedidos(Connection conn) {
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM pedidos")) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (Exception e) {
            return -1;
        }
    }
}
