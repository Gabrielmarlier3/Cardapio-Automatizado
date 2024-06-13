import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class SimpleGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private DefaultTableModel tableModel;
    private JTable table;
    private JComboBox<String> ingredienteComboBox;
    private JComboBox<String> pratoComboBox;
    private DefaultTableModel pratosTableModel;
    private JTable pratosTable;

    public SimpleGUI() {
        // Configurações básicas do JFrame
        setTitle("Home");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centraliza a janela na tela

        // Inicializa o CardLayout e o painel principal
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Dimensões personalizadas para cada página
        Dimension homeButtonSize = new Dimension(100, 30); // Tamanho para os botões na Home
        Dimension estoqueButtonSize = new Dimension(200, 50); // Tamanho para os botões no Estoque
        Dimension cardapioButtonSize = new Dimension(180, 50); // Tamanho para os botões no Cardápio

        // Cria o painel inicial com os botões principais
        JPanel homePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcHome = new GridBagConstraints();
        gbcHome.insets = new Insets(20, 20, 20, 20); // Espaçamento entre os botões
        gbcHome.fill = GridBagConstraints.NONE; // Evita que os botões se expandam para preencher todo o espaço disponível

        RoundedButton button1 = new RoundedButton("Estoque", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        button1.setPreferredSize(homeButtonSize);
        RoundedButton button2 = new RoundedButton("Cardápio", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        button2.setPreferredSize(homeButtonSize);

        // Adiciona os botões ao painel inicial lado a lado
        gbcHome.gridx = 0;
        gbcHome.gridy = 0;
        homePanel.add(button1, gbcHome);

        gbcHome.gridx = 1;
        gbcHome.gridy = 0;
        homePanel.add(button2, gbcHome);

        // Cria o painel "Estoque" com quatro botões organizados em 2x2
        JPanel estoquePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(17, 17, 17, 17); // Espaçamento entre os botões
        gbc.fill = GridBagConstraints.NONE; // Evita que os botões se expandam para preencher todo o espaço disponível

        RoundedButton estoqueButton1 = new RoundedButton("Ingredientes no Estoque", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        estoqueButton1.setPreferredSize(estoqueButtonSize);
        RoundedButton estoqueButton2 = new RoundedButton("Adicionar ingrediente", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        estoqueButton2.setPreferredSize(estoqueButtonSize);
        RoundedButton estoqueButton3 = new RoundedButton("Remover ingrediente", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        estoqueButton3.setPreferredSize(estoqueButtonSize);
        RoundedButton estoqueButton4 = new RoundedButton("Editar ingrediente", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        estoqueButton4.setPreferredSize(estoqueButtonSize);
        RoundedButton estoqueButtonVoltar = new RoundedButton("Voltar", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        estoqueButtonVoltar.setPreferredSize(estoqueButtonSize);

        // Adiciona os botões ao painel "Estoque" com posicionamento específico
        gbc.gridx = 0;
        gbc.gridy = 0;
        estoquePanel.add(estoqueButton1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        estoquePanel.add(estoqueButton2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        estoquePanel.add(estoqueButton3, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        estoquePanel.add(estoqueButton4, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        estoquePanel.add(estoqueButtonVoltar, gbc);

        // Cria o painel "Cardápio" com três botões organizados em 2x2
        JPanel cardapioPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcCardapio = new GridBagConstraints();
        gbcCardapio.insets = new Insets(17, 17, 17, 17); // Espaçamento entre os botões
        gbcCardapio.fill = GridBagConstraints.NONE; // Evita que os botões se expandam para preencher todo o espaço disponível

        RoundedButton cardapioButton1 = new RoundedButton("Criar cardápio", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        cardapioButton1.setPreferredSize(cardapioButtonSize);
        RoundedButton cardapioButton2 = new RoundedButton("Executar cardápio", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        cardapioButton2.setPreferredSize(cardapioButtonSize);
        RoundedButton cardapioButton3 = new RoundedButton("Remover cardápio", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        cardapioButton3.setPreferredSize(cardapioButtonSize);
        RoundedButton cardapioButtonVoltar = new RoundedButton("Voltar", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        cardapioButtonVoltar.setPreferredSize(cardapioButtonSize);

        // Adiciona os botões ao painel "Cardápio" com posicionamento específico
        gbcCardapio.gridx = 0;
        gbcCardapio.gridy = 0;
        cardapioPanel.add(cardapioButton1, gbcCardapio);

        gbcCardapio.gridx = 1;
        gbcCardapio.gridy = 0;
        cardapioPanel.add(cardapioButton2, gbcCardapio);

        gbcCardapio.gridx = 0;
        gbcCardapio.gridy = 1;
        cardapioPanel.add(cardapioButton3, gbcCardapio);

        gbcCardapio.gridx = 1;
        gbcCardapio.gridy = 1;
        cardapioPanel.add(cardapioButtonVoltar, gbcCardapio);

        // Adiciona ActionListener ao botão "Pratos Cadastrados"
        RoundedButton pratosCadastradosButton = new RoundedButton("Pratos Cadastrados", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        pratosCadastradosButton.setPreferredSize(cardapioButtonSize);
        pratosCadastradosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Atualiza o modelo da tabela antes de mostrar o painel
                atualizarPratosTable(pratosTable); // Certifique-se de definir pratosTable corretamente
                cardLayout.show(mainPanel, "Pratos Cadastrados");
            }
        });

// Adiciona os botões ao painel "Cardápio" com posicionamento específico
        gbcCardapio.gridx = 0;
        gbcCardapio.gridy = 0;
        cardapioPanel.add(cardapioButton1, gbcCardapio);

        gbcCardapio.gridx = 1;
        gbcCardapio.gridy = 0;
        cardapioPanel.add(cardapioButton2, gbcCardapio);

        gbcCardapio.gridx = 0;
        gbcCardapio.gridy = 1;
        cardapioPanel.add(cardapioButton3, gbcCardapio);

        gbcCardapio.gridx = 1;
        gbcCardapio.gridy = 1;
        cardapioPanel.add(pratosCadastradosButton, gbcCardapio);

        gbcCardapio.gridx = 0;
        gbcCardapio.gridy = 2;
        gbcCardapio.gridwidth = 2; // Centraliza o botão "Voltar"
        cardapioPanel.add(cardapioButtonVoltar, gbcCardapio);

        // Cria os painéis específicos para cada botão
        JPanel ingredientesNoEstoquePanel = createIngredientesNoEstoquePanel();
        JPanel adicionarIngredientePanel = createAdicionarIngredientePanel();
        JPanel removerIngredientePanel = createRemoverIngredientePanel();
        JPanel editarIngredientePanel = createEditarIngredientePanel();
        JPanel criarCardapioPanel = createCriarCardapioPanel();
        JPanel executarCardapioPanel = createExecutarCardapioPanel();
        JPanel removerCardapioPanel = createRemoverCardapioPanel();
        JPanel pratosCadastradosPanel = createPratosCadastradosPanel(); // Novo painel

        mainPanel.add(homePanel, "Home");
        mainPanel.add(estoquePanel, "Estoque");
        mainPanel.add(cardapioPanel, "Cardápio");
        mainPanel.add(ingredientesNoEstoquePanel, "Ingredientes no Estoque");
        mainPanel.add(adicionarIngredientePanel, "Adicionar ingrediente");
        mainPanel.add(removerIngredientePanel, "Remover ingrediente");
        mainPanel.add(editarIngredientePanel, "Editar ingrediente");
        mainPanel.add(criarCardapioPanel, "Criar cardápio");
        mainPanel.add(executarCardapioPanel, "Executar cardápio");
        mainPanel.add(removerCardapioPanel, "Remover cardápio");
        mainPanel.add(pratosCadastradosPanel, "Pratos Cadastrados");

        // Adiciona o painel principal ao JFrame
        add(mainPanel, BorderLayout.CENTER);

        // Adiciona ActionListener ao botão "Estoque"
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Estoque");
            }
        });

        // Adiciona ActionListener ao botão "Cardápio"
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Cardápio");
            }
        });

        // Adiciona ActionListener aos botões do painel "Estoque"
        estoqueButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Atualiza o modelo da tabela antes de mostrar o painel
                tableModel.setRowCount(0); // Limpa as linhas existentes
                for (Map<String, Object> ingrediente : App.INGREDIENTE.recuperarIngredientes()) {
                    tableModel.addRow(new Object[]{ingrediente.get("nome"), ingrediente.get("quantidade")});
                }
                cardLayout.show(mainPanel, "Ingredientes no Estoque");
            }
        });

        estoqueButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Adicionar ingrediente");
            }
        });

        estoqueButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Remover ingrediente");
            }
        });

        estoqueButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Editar ingrediente");
            }
        });

        // Adiciona ActionListener ao botão "Voltar" no painel "Estoque"
        estoqueButtonVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Home");
            }
        });

        // Adiciona ActionListener aos botões do painel "Cardápio"
        cardapioButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Criar cardápio");
            }
        });

        cardapioButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Executar cardápio");
            }
        });

        cardapioButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Remover cardápio");
            }
        });

        // Adiciona ActionListener ao botão "Voltar" no painel "Cardápio"
        cardapioButtonVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Home");
            }
        });
    }

    // Método para criar painel "Ingredientes no Estoque"
    private JPanel createIngredientesNoEstoquePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Tabela para exibir os ingredientes
        String[] columnNames = {"Nome do Ingrediente", "Quantidade"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        RoundedButton voltarButton = new RoundedButton("Voltar", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        voltarButton.setPreferredSize(new Dimension(100, 30));
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Estoque");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(voltarButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // Método utilitário para criar painéis de conteúdo
    private JPanel createPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        RoundedButton voltarButton = new RoundedButton("Voltar", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        voltarButton.setPreferredSize(new Dimension(100, 30));
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Estoque"); // Ajuste conforme necessário
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(voltarButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Método para criar painel "Adicionar Ingrediente" com campos de entrada
    private JPanel createAdicionarIngredientePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel ingredienteLabel = new JLabel("Ingrediente:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(ingredienteLabel, gbc);

        JTextField ingredienteField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(ingredienteField, gbc);

        JLabel quantidadeLabel = new JLabel("Quantidade:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(quantidadeLabel, gbc);

        JFormattedTextField quantidadeField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        quantidadeField.setColumns(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(quantidadeField, gbc);

        RoundedButton adicionarButton = new RoundedButton("Adicionar", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        adicionarButton.setPreferredSize(new Dimension(100, 30));
        adicionarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ingrediente = ingredienteField.getText();
                int quantidade = Integer.parseInt(quantidadeField.getText().replaceAll("\\D", ""));

                if (ingrediente.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Erro: insira um ingrediente válido", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Adiciona o ingrediente ao banco de dados
                App.INGREDIENTE.adicionarIngrediente(ingrediente, quantidade);

                JOptionPane.showMessageDialog(panel, "Ingrediente adicionado com sucesso!");
                ingredienteField.setText("");
                quantidadeField.setText("");

                // Atualiza o JComboBox no painel de remoção
                atualizarIngredienteComboBox(ingredienteComboBox);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(adicionarButton, gbc);

        RoundedButton voltarButton = new RoundedButton("Voltar", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        voltarButton.setPreferredSize(new Dimension(100, 30));
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Estoque");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(voltarButton, gbc);

        return panel;
    }

    private void atualizarPratoComboBox(JComboBox<String> comboBox) {
        comboBox.removeAllItems(); // Remove todos os itens existentes
        for (String prato : App.CARDAPIO.recuperarPratos()) {
            comboBox.addItem(prato); // Adiciona os novos itens
        }
    }

    private void atualizarIngredienteComboBox(JComboBox<String> comboBox) {
        comboBox.removeAllItems(); // Remove todos os itens existentes
        for (Map<String, Object> ingrediente : App.INGREDIENTE.recuperarIngredientes()) {
            comboBox.addItem((String) ingrediente.get("nome")); // Adiciona os novos itens
        }
    }

    // Método para criar painel "Remover Ingrediente" com campos de entrada
    private JPanel createRemoverIngredientePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel ingredienteLabel = new JLabel("Ingrediente:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(ingredienteLabel, gbc);

        // Inicializa o JComboBox para selecionar o ingrediente
        ingredienteComboBox = new JComboBox<>();
        atualizarIngredienteComboBox(ingredienteComboBox); // Atualiza o JComboBox com os ingredientes
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(ingredienteComboBox, gbc);

        RoundedButton removerButton = new RoundedButton("Remover", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        removerButton.setPreferredSize(new Dimension(100, 30));
        removerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ingrediente = (String) ingredienteComboBox.getSelectedItem();

                if (ingrediente == null || ingrediente.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Erro: selecione um ingrediente válido", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Remove o ingrediente do banco de dados
                try {
                    App.INGREDIENTE.removerIngrediente(ingrediente);
                    JOptionPane.showMessageDialog(panel, "Ingrediente removido com sucesso!");
                    atualizarIngredienteComboBox(ingredienteComboBox); // Atualiza o JComboBox após a remoção
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Erro ao remover ingrediente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(removerButton, gbc);

        RoundedButton voltarButton = new RoundedButton("Voltar", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        voltarButton.setPreferredSize(new Dimension(100, 30));
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarIngredienteComboBox(ingredienteComboBox);
                cardLayout.show(mainPanel, "Estoque");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(voltarButton, gbc);

        return panel;
    }

    // Método para criar painel "Editar Ingrediente" com campos de entrada
    private JPanel createEditarIngredientePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel ingredienteAntigoLabel = new JLabel("Ingrediente Antigo:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(ingredienteAntigoLabel, gbc);

        ingredienteComboBox = new JComboBox<>();
        atualizarIngredienteComboBox(ingredienteComboBox); // Atualiza o JComboBox com os ingredientes
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(ingredienteComboBox, gbc);

        JLabel novoNomeLabel = new JLabel("Novo Nome:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(novoNomeLabel, gbc);

        JTextField novoNomeField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(novoNomeField, gbc);

        JLabel novaQuantidadeLabel = new JLabel("Nova Quantidade:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(novaQuantidadeLabel, gbc);

        JFormattedTextField novaQuantidadeField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        novaQuantidadeField.setColumns(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(novaQuantidadeField, gbc);

        RoundedButton editarButton = new RoundedButton("Editar", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        editarButton.setPreferredSize(new Dimension(100, 30));
        editarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ingredienteAntigo = (String) ingredienteComboBox.getSelectedItem();
                String novoNome = novoNomeField.getText();
                String novaQuantidadeStr = novaQuantidadeField.getText();
                Integer novaQuantidade = novaQuantidadeStr.isEmpty() ? null : Integer.parseInt(novaQuantidadeStr);

                if (ingredienteAntigo == null || ingredienteAntigo.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Erro: selecione um ingrediente válido", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (novoNome == null || novoNome.isEmpty()) {
                    novoNome = ingredienteAntigo;
                }

                // Edita o ingrediente no banco de dados
                App.editarIngrediente(ingredienteAntigo, novoNome, novaQuantidade);

                JOptionPane.showMessageDialog(panel, "Ingrediente editado com sucesso!");
                atualizarIngredienteComboBox(ingredienteComboBox);
                novoNomeField.setText("");
                novaQuantidadeField.setText("");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(editarButton, gbc);

        RoundedButton voltarButton = new RoundedButton("Voltar", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        voltarButton.setPreferredSize(new Dimension(100, 30));
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarIngredienteComboBox(ingredienteComboBox);
                cardLayout.show(mainPanel, "Estoque");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(voltarButton, gbc);

        return panel;
    }

    // Método para criar painel "Criar Cardápio" com campos de entrada
    private JPanel createCriarCardapioPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nomePratoLabel = new JLabel("Nome do Prato:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nomePratoLabel, gbc);

        JTextField nomePratoField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nomePratoField, gbc);

        JLabel precoPratoLabel = new JLabel("Preço do Prato:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(precoPratoLabel, gbc);

        JFormattedTextField precoPratoField = new JFormattedTextField(NumberFormat.getNumberInstance());
        precoPratoField.setColumns(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(precoPratoField, gbc);

        JLabel ingredienteLabel = new JLabel("Ingrediente:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(ingredienteLabel, gbc);

        JTextField ingredienteField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(ingredienteField, gbc);

        JLabel quantidadeLabel = new JLabel("Quantidade:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(quantidadeLabel, gbc);

        JFormattedTextField quantidadeField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        quantidadeField.setColumns(10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(quantidadeField, gbc);

        Map<String, Integer> ingredientesMap = new HashMap<>();

        RoundedButton adicionarIngredienteButton = new RoundedButton("Adicionar Ingrediente", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        adicionarIngredienteButton.setPreferredSize(new Dimension(200, 30));
        adicionarIngredienteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ingrediente = ingredienteField.getText();
                int quantidade = Integer.parseInt(quantidadeField.getText().replaceAll("\\D", ""));

                if (ingrediente.isEmpty() || quantidade <= 0) {
                    JOptionPane.showMessageDialog(panel, "Erro: insira um ingrediente e uma quantidade válida", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean found = App.localizarIngrediente(ingrediente);

                if (!found) {
                    JOptionPane.showMessageDialog(panel, "Erro: ingrediente não encontrado", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ingredientesMap.put(ingrediente, quantidade);
                ingredienteField.setText("");
                quantidadeField.setText("");
                JOptionPane.showMessageDialog(panel, "Ingrediente adicionado ao prato!");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(adicionarIngredienteButton, gbc);

        RoundedButton criarPratoButton = new RoundedButton("Criar Prato", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        criarPratoButton.setPreferredSize(new Dimension(200, 30));
        criarPratoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomePrato = nomePratoField.getText();
                double precoPrato = Double.parseDouble(precoPratoField.getText().replaceAll("\\D", ""));

                if (nomePrato.isEmpty() || precoPrato <= 0 || ingredientesMap.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Erro: insira um nome de prato, preço e pelo menos um ingrediente", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                App.criarNovoPrato(nomePrato, precoPrato, ingredientesMap);
                nomePratoField.setText("");
                precoPratoField.setText("");
                ingredienteField.setText("");
                quantidadeField.setText("");
                ingredientesMap.clear();
                JOptionPane.showMessageDialog(panel, "Prato criado com sucesso!");

                // Atualiza o JComboBox após a criação do prato
                atualizarPratoComboBox(pratoComboBox);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(criarPratoButton, gbc);

        RoundedButton voltarButton = new RoundedButton("Voltar", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        voltarButton.setPreferredSize(new Dimension(100, 30));
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Cardápio");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(voltarButton, gbc);

        return panel;
    }

    private JPanel createExecutarCardapioPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel pratoLabel = new JLabel("Prato:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(pratoLabel, gbc);

        // Inicializa o JComboBox para selecionar o prato
        pratoComboBox = new JComboBox<>();
        atualizarPratoComboBox(pratoComboBox); // Atualiza o JComboBox com os pratos
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(pratoComboBox, gbc);

        RoundedButton executarButton = new RoundedButton("Executar", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        executarButton.setPreferredSize(new Dimension(100, 30));
        executarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String prato = (String) pratoComboBox.getSelectedItem();

                if (prato == null || prato.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Erro: selecione um prato válido", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // Executa o prato
                    App.CARDAPIO.executarPrato(prato);
                    JOptionPane.showMessageDialog(panel, "Prato executado com sucesso!");
                    atualizarPratoComboBox(pratoComboBox); // Atualiza o JComboBox após a execução do prato
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Erro ao executar prato: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(executarButton, gbc);

        RoundedButton voltarButton = new RoundedButton("Voltar", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        voltarButton.setPreferredSize(new Dimension(100, 30));
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Cardápio");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(voltarButton, gbc);

        return panel;
    }

    private JPanel createRemoverCardapioPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel pratoLabel = new JLabel("Prato:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(pratoLabel, gbc);

        // Inicializa o JComboBox para selecionar o prato
        pratoComboBox = new JComboBox<>();
        atualizarPratoComboBox(pratoComboBox); // Atualiza o JComboBox com os pratos
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(pratoComboBox, gbc);

        RoundedButton removerButton = new RoundedButton("Remover", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        removerButton.setPreferredSize(new Dimension(100, 30));
        removerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String prato = (String) pratoComboBox.getSelectedItem();

                if (prato == null || prato.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Erro: selecione um prato válido", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // Remove o prato
                    App.CARDAPIO.removerPrato(prato);
                    JOptionPane.showMessageDialog(panel, "Prato removido com sucesso!");
                    atualizarPratoComboBox(pratoComboBox); // Atualiza o JComboBox após a remoção do prato
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Erro ao remover prato: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(removerButton, gbc);

        RoundedButton voltarButton = new RoundedButton("Voltar", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        voltarButton.setPreferredSize(new Dimension(100, 30));
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Cardápio");
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(voltarButton, gbc);

        return panel;
    }

    private JPanel createPratosCadastradosPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Tabela para exibir os pratos
        String[] columnNames = {"Nome do Prato", "Preço"};
        pratosTableModel = new DefaultTableModel(columnNames, 0);
        pratosTable = new JTable(pratosTableModel);

        JScrollPane scrollPane = new JScrollPane(pratosTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        RoundedButton voltarButton = new RoundedButton("Voltar", 3, new Color(255, 189, 138), new Color(133, 71, 23), new Color(0, 0, 0));
        voltarButton.setPreferredSize(new Dimension(100, 30));
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Cardápio");
            }
        });

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(voltarButton, gbc);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void atualizarPratosTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Limpa as linhas existentes
        for (Map<String, Object> prato : App.CARDAPIO.recuperarPratosDetalhados()) {
            model.addRow(new Object[]{prato.get("nome"), prato.get("preco")});
        }
    }

    public static void main(String[] args) {
        // Cria e exibe a interface gráfica
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleGUI().setVisible(true);
            }
        });
    }

    // Classe para criar botões arredondados
    class RoundedButton extends JButton {
        private int borderThickness;
        private Color borderColor;
        private Color backgroundColor;
        private Color textColor;

        public RoundedButton(String label, int borderThickness, Color backgroundColor, Color borderColor, Color textColor) {
            super(label);
            this.borderThickness = borderThickness;
            this.borderColor = borderColor;
            this.backgroundColor = backgroundColor;
            this.textColor = textColor;
            setContentAreaFilled(false);
            setBorder(new EmptyBorder(5, 15, 5, 15));
            setBackground(backgroundColor);
            setForeground(textColor);
            setFont(new Font("Arial", Font.BOLD, 14));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            g2.setColor(borderColor);
            g2.setStroke(new java.awt.BasicStroke(borderThickness));
            g2.drawRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
