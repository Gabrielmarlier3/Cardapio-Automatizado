import java.util.Map;
import java.sql.SQLException;

public class App {
    public static final Ingrediente INGREDIENTE;
    public static final Cardapio CARDAPIO;

    static {
        INGREDIENTE = new Ingrediente();
        CARDAPIO = new Cardapio();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleGUI().setVisible(true);
            }
        });
    }

    public static void adicionarIngrediente(String nome, int quantidade) {
        INGREDIENTE.adicionarIngrediente(nome, quantidade);
        System.out.println("Ingrediente adicionado com sucesso!");
    }

    public static void removerIngrediente(String nome) {
        INGREDIENTE.removerIngrediente(nome);
        System.out.println("Ingrediente removido com sucesso!");
    }


    public static void editarIngrediente(String nomeAntigo, String novoNome, Integer novaQuantidade) {
        INGREDIENTE.editarIngrediente(nomeAntigo, novoNome, novaQuantidade);
        System.out.println("Ingrediente editado com sucesso!");
    }

    public static boolean localizarIngrediente(String nome) {
        return INGREDIENTE.localizarIngrediente(nome);
    }

    public static int obterQuantidadeIngrediente(String nome) {
        return INGREDIENTE.obterQuantidadeIngrediente(nome);
    }

    public static void criarNovoPrato(String nomePrato, double precoPrato, Map<String, Integer> ingredientesMap) {
        CARDAPIO.criarCardapio(nomePrato, precoPrato, ingredientesMap);
        System.out.println("Prato criado com sucesso!");
    }

    public static void executarPrato(String nomePrato) throws SQLException {
        CARDAPIO.executarPrato(nomePrato);
    }

    public static void removerPrato(String nomePrato) throws SQLException {
        CARDAPIO.removerPrato(nomePrato);
    }


}
