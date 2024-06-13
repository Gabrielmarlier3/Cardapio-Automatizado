import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Ingrediente {
    private Connection connection;

    // Construtor que obtém a conexão do DatabaseConnection
    public Ingrediente() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int obterQuantidadeIngrediente(String nome) {
        String query = "SELECT quantidade FROM ingredientes WHERE nome = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nome);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantidade");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean localizarIngrediente(String nome) {
        String query = "SELECT 1 FROM ingredientes WHERE nome = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nome);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Map<String, Object>> recuperarIngredientes() {
        List<Map<String, Object>> ingredientes = new ArrayList<>();
        String query = "SELECT nome, quantidade FROM ingredientes";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Map<String, Object> ingrediente = new HashMap<>();
                ingrediente.put("nome", rs.getString("nome"));
                ingrediente.put("quantidade", rs.getInt("quantidade"));
                ingredientes.add(ingrediente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredientes;
    }



    public void adicionarIngrediente(String nome, int quantidade) {
        String query = "INSERT INTO ingredientes (nome, quantidade) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE quantidade = quantidade + VALUES(quantidade)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nome);
            pstmt.setInt(2, quantidade);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removerRegistrosDependentes(int idIngrediente) {
        String query = "DELETE FROM ingredientes_pratos WHERE idIngrediente = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, idIngrediente);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removerIngrediente(String nome) {
        String selectIdQuery = "SELECT idIngrediente FROM ingredientes WHERE nome = ?";
        String deleteFromIngredientesPratosQuery = "DELETE FROM ingredientes_pratos WHERE idIngrediente = ?";
        String deleteIngredienteQuery = "DELETE FROM ingredientes WHERE nome = ?";

        try (PreparedStatement selectIdStmt = connection.prepareStatement(selectIdQuery)) {
            selectIdStmt.setString(1, nome);
            try (ResultSet rs = selectIdStmt.executeQuery()) {
                if (rs.next()) {
                    int idIngrediente = rs.getInt("idIngrediente");

                    // Remove referências na tabela ingredientes_pratos
                    try (PreparedStatement deleteFromIngredientesPratosStmt = connection.prepareStatement(deleteFromIngredientesPratosQuery)) {
                        deleteFromIngredientesPratosStmt.setInt(1, idIngrediente);
                        deleteFromIngredientesPratosStmt.executeUpdate();
                    }

                    // Remove o ingrediente da tabela ingredientes
                    try (PreparedStatement deleteIngredienteStmt = connection.prepareStatement(deleteIngredienteQuery)) {
                        deleteIngredienteStmt.setString(1, nome);
                        deleteIngredienteStmt.executeUpdate();
                    }
                } else {
                    System.out.println("Ingrediente não encontrado: " + nome);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    public void editarIngrediente(String ingredienteAntigo, String novoNome, Integer novaQuantidade) {
        StringBuilder queryBuilder = new StringBuilder("UPDATE ingredientes SET ");
        List<Object> params = new ArrayList<>();

        if (novoNome != null) {
            queryBuilder.append("nome = ?, ");
            params.add(novoNome);
        }

        if (novaQuantidade != null) {
            queryBuilder.append("quantidade = ?, ");
            params.add(novaQuantidade);
        }

        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
        queryBuilder.append(" WHERE nome = ?");
        params.add(ingredienteAntigo);

        String query = queryBuilder.toString();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
