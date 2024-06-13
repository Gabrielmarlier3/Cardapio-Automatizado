import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
public class Cardapio {
    private Connection connection;

    public Cardapio() {
        String url = "jdbc:mysql://localhost:3306/cardapioautomatizado";
        String user = "user";
        String password = "gabriel12";

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Map<String, Object>> recuperarPratosDetalhados() {
        List<Map<String, Object>> pratos = new ArrayList<>();
        String query = "SELECT nome, preco FROM pratos";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> prato = new HashMap<>();
                prato.put("nome", rs.getString("nome"));
                prato.put("preco", rs.getDouble("preco"));
                pratos.add(prato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pratos;
    }

    public void criarCardapio(String nomePrato, double precoPrato, Map<String, Integer> ingredientes) {
        try {
            connection.setAutoCommit(false);

            String insertPratoQuery = "INSERT INTO pratos (nome, preco) VALUES (?, ?)";
            try (PreparedStatement insertPratoStmt = connection.prepareStatement(insertPratoQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertPratoStmt.setString(1, nomePrato);
                insertPratoStmt.setDouble(2, precoPrato);
                insertPratoStmt.executeUpdate();

                try (ResultSet generatedKeys = insertPratoStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idPrato = generatedKeys.getInt(1);

                        String insertIngredientePratoQuery = "INSERT INTO ingredientes_pratos (idIngrediente, idPrato, quantidade) VALUES ((SELECT idIngrediente FROM ingredientes WHERE nome = ?), ?, ?)";
                        try (PreparedStatement insertIngredientePratoStmt = connection.prepareStatement(insertIngredientePratoQuery)) {
                            for (Map.Entry<String, Integer> entry : ingredientes.entrySet()) {
                                String nomeIngrediente = entry.getKey();
                                int quantidade = entry.getValue();

                                String updateIngredienteQuery = "UPDATE ingredientes SET quantidade = quantidade - ? WHERE nome = ?";
                                try (PreparedStatement updateIngredienteStmt = connection.prepareStatement(updateIngredienteQuery)) {
                                    updateIngredienteStmt.setInt(1, quantidade);
                                    updateIngredienteStmt.setString(2, nomeIngrediente);
                                    updateIngredienteStmt.executeUpdate();
                                }

                                insertIngredientePratoStmt.setString(1, nomeIngrediente);
                                insertIngredientePratoStmt.setInt(2, idPrato);
                                insertIngredientePratoStmt.setInt(3, quantidade);
                                insertIngredientePratoStmt.executeUpdate();
                            }
                        }
                    }
                }
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void executarPrato(String nomePrato) throws SQLException {
        try {
            connection.setAutoCommit(false);

            String selectPratoQuery = "SELECT idPrato FROM pratos WHERE nome = ?";
            int idPrato;
            try (PreparedStatement selectPratoStmt = connection.prepareStatement(selectPratoQuery)) {
                selectPratoStmt.setString(1, nomePrato);
                try (ResultSet rs = selectPratoStmt.executeQuery()) {
                    if (rs.next()) {
                        idPrato = rs.getInt("idPrato");
                    } else {
                        throw new SQLException("Prato não encontrado: " + nomePrato);
                    }
                }
            }

            String selectIngredientesQuery = "SELECT i.nome, ip.quantidade FROM ingredientes i JOIN ingredientes_pratos ip ON i.idIngrediente = ip.idIngrediente WHERE ip.idPrato = ?";
            try (PreparedStatement selectIngredientesStmt = connection.prepareStatement(selectIngredientesQuery)) {
                selectIngredientesStmt.setInt(1, idPrato);
                try (ResultSet rs = selectIngredientesStmt.executeQuery()) {
                    while (rs.next()) {
                        String nomeIngrediente = rs.getString("nome");
                        int quantidade = rs.getInt("quantidade");

                        String checkQuantidadeQuery = "SELECT quantidade FROM ingredientes WHERE nome = ?";
                        try (PreparedStatement checkQuantidadeStmt = connection.prepareStatement(checkQuantidadeQuery)) {
                            checkQuantidadeStmt.setString(1, nomeIngrediente);
                            try (ResultSet rsCheck = checkQuantidadeStmt.executeQuery()) {
                                if (rsCheck.next()) {
                                    int quantidadeAtual = rsCheck.getInt("quantidade");
                                    if (quantidadeAtual < quantidade) {
                                        throw new SQLException("Quantidade insuficiente de " + nomeIngrediente + ". Necessário: " + quantidade + ", Disponível: " + quantidadeAtual);
                                    }
                                }
                            }
                        }

                        String updateIngredienteQuery = "UPDATE ingredientes SET quantidade = quantidade - ? WHERE nome = ?";
                        try (PreparedStatement updateIngredienteStmt = connection.prepareStatement(updateIngredienteQuery)) {
                            updateIngredienteStmt.setInt(1, quantidade);
                            updateIngredienteStmt.setString(2, nomeIngrediente);
                            updateIngredienteStmt.executeUpdate();
                        }
                    }
                }
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void removerPrato(String nomePrato) throws SQLException {
        try {
            connection.setAutoCommit(false);

            String selectPratoQuery = "SELECT idPrato FROM pratos WHERE nome = ?";
            int idPrato;
            try (PreparedStatement selectPratoStmt = connection.prepareStatement(selectPratoQuery)) {
                selectPratoStmt.setString(1, nomePrato);
                try (ResultSet rs = selectPratoStmt.executeQuery()) {
                    if (rs.next()) {
                        idPrato = rs.getInt("idPrato");
                    } else {
                        throw new SQLException("Prato não encontrado: " + nomePrato);
                    }
                }
            }

            String deleteIngredientesPratosQuery = "DELETE FROM ingredientes_pratos WHERE idPrato = ?";
            try (PreparedStatement deleteIngredientesPratosStmt = connection.prepareStatement(deleteIngredientesPratosQuery)) {
                deleteIngredientesPratosStmt.setInt(1, idPrato);
                deleteIngredientesPratosStmt.executeUpdate();
            }

            String deletePratoQuery = "DELETE FROM pratos WHERE idPrato = ?";
            try (PreparedStatement deletePratoStmt = connection.prepareStatement(deletePratoQuery)) {
                deletePratoStmt.setInt(1, idPrato);
                deletePratoStmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<String> recuperarPratos() {
        List<String> pratos = new ArrayList<>();
        String query = "SELECT nome FROM pratos";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                pratos.add(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pratos;
    }
}
