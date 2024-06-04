import java.sql.*;
import java.util.Map;

public class Cardapio {
    private Connection connection;

    public Cardapio() {
        // Configurar a conexão com o banco de dados
        String url = "jdbc:mysql://localhost:3306/cardapioautomatizado";
        String user = "user";
        String password = "gabriel12";

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void criarCardapio(String nomePrato, double precoPrato, Map<String, Integer> ingredientes) {
        try {
            // Desligar auto-commit para realizar a transação
            connection.setAutoCommit(false);

            // Inserir o prato na tabela "pratos"
            String insertPratoQuery = "INSERT INTO pratos (nome, preco) VALUES (?, ?)";
            try (PreparedStatement insertPratoStmt = connection.prepareStatement(insertPratoQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertPratoStmt.setString(1, nomePrato);
                insertPratoStmt.setDouble(2, precoPrato);
                insertPratoStmt.executeUpdate();

                // Obter o ID do prato recém-inserido
                try (ResultSet generatedKeys = insertPratoStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idPrato = generatedKeys.getInt(1);

                        // Inserir os ingredientes na tabela "ingredientes_pratos"
                        String insertIngredientePratoQuery = "INSERT INTO ingredientes_pratos (idIngrediente, idPrato) VALUES ((SELECT idIngrediente FROM ingredientes WHERE nome = ?), ?)";
                        try (PreparedStatement insertIngredientePratoStmt = connection.prepareStatement(insertIngredientePratoQuery)) {
                            for (Map.Entry<String, Integer> entry : ingredientes.entrySet()) {
                                String nomeIngrediente = entry.getKey();
                                int quantidade = entry.getValue();

                                // Atualizar a quantidade do ingrediente na tabela "ingredientes"
                                String updateIngredienteQuery = "UPDATE ingredientes SET quantidade = quantidade - ? WHERE nome = ?";
                                try (PreparedStatement updateIngredienteStmt = connection.prepareStatement(updateIngredienteQuery)) {
                                    updateIngredienteStmt.setInt(1, quantidade);
                                    updateIngredienteStmt.setString(2, nomeIngrediente);
                                    updateIngredienteStmt.executeUpdate();
                                }

                                insertIngredientePratoStmt.setString(1, nomeIngrediente);
                                insertIngredientePratoStmt.setInt(2, idPrato);
                                insertIngredientePratoStmt.executeUpdate();
                            }
                        }
                    }
                }
            }

            // Confirmar a transação
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // Reverter a transação em caso de erro
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                // Ligar novamente o auto-commit
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
