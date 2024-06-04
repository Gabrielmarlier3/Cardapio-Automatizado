
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
     private static final Ingredientes ingredientes;

    static {
        Scanner scanner = new Scanner(System.in);
        ingredientes = new Ingredientes();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Recuperar ingredientes");
            System.out.println("2 - Adicionar ingrediente");
            System.out.println("3 - Remover ingrediente");
            System.out.println("4 - Editar ingrediente");
            System.out.println("5 - Criar cardapio");
            System.out.println("0 - Sair");

            System.out.print("Opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    listarIngredientes();
                    break;
                case 2:
                    adicionarIngrediente(scanner);
                    break;
                case 3:
                    removerIngrediente(scanner);
                    break;
                case 4:
                    editarIngrediente(scanner);
                    break;
                case 5:
                    criarNovoPrato(scanner);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void listarIngredientes() {
        System.out.println("Ingredientes disponíveis:");
        for (String ingrediente : ingredientes.recuperarIngredientes()) {
            System.out.println(ingrediente);
        }
    }

    private static void adicionarIngrediente(Scanner scanner) {
        System.out.print("Nome do ingrediente: ");
        String nome = scanner.next();
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();
        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        ingredientes.adicionarIngrediente(nome, quantidade, preco);
        System.out.println("Ingrediente adicionado com sucesso!");
    }

    private static void removerIngrediente(Scanner scanner) {
        System.out.print("Nome do ingrediente a ser removido: ");
        String nome = scanner.next();
        ingredientes.removerIngrediente(nome);
        System.out.println("Ingrediente removido com sucesso!");
    }

    private static void editarIngrediente(Scanner scanner) {
        listarIngredientes();

        System.out.print("\n Nome do ingrediente a ser editado");
        String nomeAntigo = scanner.next();

        while(!ingredientes.localizarIngrediente(nomeAntigo)){
            System.out.print("Nome do ingrediente não encontrado tente novamente: ");
            nomeAntigo = scanner.next();

        }

            System.out.print("Novo nome do ingrediente (ou pressione Enter para manter o atual): ");

            String novoNomeStr = scanner.next().trim();
            String novoNome = novoNomeStr.isEmpty() ? nomeAntigo : novoNomeStr;

            System.out.print("Nova quantidade: ");
            String novaQuantidadeStr = scanner.next().trim();
            Integer novaQuantidade = novaQuantidadeStr.isEmpty() ? null : Integer.parseInt(novaQuantidadeStr);

            System.out.print("Novo preço: ");
            String novoPrecoStr = scanner.next().trim();
            Double novoPreco = novoPrecoStr.isEmpty() ? null : Double.parseDouble(novoPrecoStr);


            ingredientes.editarIngrediente(nomeAntigo, novoNome, novaQuantidade, novoPreco);
            System.out.println("Ingrediente editado com sucesso\n");


    }


    private static void criarNovoPrato(Scanner scanner) {
        Ingredientes ingredientes = new Ingredientes(); // Instância da classe Ingredientes

        System.out.println("Para criar um novo prato, informe os seguintes detalhes:");
        System.out.print("Nome do prato: ");
        String nomePrato = scanner.next();

        System.out.print("Preço do prato: ");
        double precoPrato = Double.parseDouble(scanner.next());

        Map<String, Integer> ingredientesMap = new HashMap<>();

        System.out.println("Ingredientes disponiveis \n"+ingredientes.recuperarIngredientes());
        boolean adicionarIngredientes = true;
        while (adicionarIngredientes) {
            System.out.print("Nome do ingrediente: ");
            String nomeIngrediente = scanner.next();

            // Verificar se o ingrediente existe
            if (!ingredientes.localizarIngrediente(nomeIngrediente)) {
                System.out.println("O ingrediente '" + nomeIngrediente + "' não existe na base de dados.");
                System.out.print("Deseja tentar outro ingrediente? (S/N): ");
                if (scanner.next().equalsIgnoreCase("N")) {
                    return; // Interrompe a criação do prato
                } else {
                    continue; // Tenta adicionar outro ingrediente
                }
            }

            int quantidadeDisponivel = ingredientes.obterQuantidadeIngrediente(nomeIngrediente);
            System.out.println("Quantidade disponível: " + quantidadeDisponivel);

            System.out.print("Quantidade do ingrediente: ");
            int quantidadeIngrediente = Integer.parseInt(scanner.next());

            if (quantidadeIngrediente > quantidadeDisponivel) {
                System.out.println("Quantidade insuficiente. Tente novamente.");
                continue;
            }

            ingredientesMap.put(nomeIngrediente, quantidadeIngrediente);

            System.out.print("Deseja adicionar outro ingrediente? (S/N): ");
            adicionarIngredientes = scanner.next().equalsIgnoreCase("S");
        }

        Cardapio cardapio = new Cardapio();
        cardapio.criarCardapio(nomePrato, precoPrato, ingredientesMap);

        System.out.println("Prato criado com sucesso!");
    }
}



