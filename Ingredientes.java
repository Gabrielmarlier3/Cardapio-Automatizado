import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Ingredientes {
    private Connection connection;

    // Corrigido para o nome da classe
    public Ingredientes() {
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

    public int obterQuantidadeIngrediente(String nome) {
        String query = "SELECT quantidade FROM ingredientes WHERE nome = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nome);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Quantidade encontrada
                    return rs.getInt("quantidade");
                } else {
                    // Ingrediente não encontrado
                    return 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean localizarIngrediente(String nome) {
        String query = "SELECT nome FROM ingredientes WHERE nome = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nome);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Ingrediente encontrado
                    return true;
                } else {
                    // Ingrediente não encontrado
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<String> recuperarIngredientes() {
        List<String> ingredientes = new ArrayList<>();
        String query = "SELECT nome FROM ingredientes WHERE quantidade > 0";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                ingredientes.add(rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredientes;
    }


    public void adicionarIngrediente(String nome, int quantidade, double preco) {
        String query = "INSERT INTO ingredientes (nome, quantidade, preco) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nome);
            pstmt.setInt(2, quantidade);
            pstmt.setDouble(3, preco);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Método para remover um ingrediente do banco de dados
    public void removerIngrediente(String nome) {
        String query = "DELETE FROM ingredientes WHERE nome = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nome); // Usando o nome passado como parâmetro
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Método para editar um ingrediente no banco de dados
    public void editarIngrediente(String ingredienteAntigo, String novoNome, Integer novaQuantidade, Double novoPreco) {
        // Inicializa a query SQL
        StringBuilder queryBuilder = new StringBuilder("UPDATE ingredientes SET ");
        List<Object> params = new ArrayList<>();

        // Verifica se o novo nome foi especificado e adiciona à query
        if (novoNome != null) {
            queryBuilder.append("nome = ?, ");
            params.add(novoNome);
        }

        // Verifica se a nova quantidade foi especificada e adiciona à query
        if (novaQuantidade != null) {
            queryBuilder.append("quantidade = ?, ");
            params.add(novaQuantidade);
        }

        // Verifica se o novo preço foi especificado e adiciona à query
        if (novoPreco != null) {
            queryBuilder.append("preco = ?, ");
            params.add(novoPreco);
        }

        // Remove a última vírgula da query
        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());

        // Adiciona a condição WHERE à query
        queryBuilder.append(" WHERE nome = ?");
        params.add(ingredienteAntigo);

        String query = queryBuilder.toString();

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            // Define os parâmetros da consulta SQL
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
                System.out.println(params);
            }

            // Executa a atualização
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
