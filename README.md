### README

# Cardápio Automatizado

## Introdução

Este projeto é um sistema de gestão de cardápios e estoques de ingredientes desenvolvido em Java utilizando a biblioteca Swing para a interface gráfica e MySQL para a persistência de dados. Ele permite gerenciar ingredientes, criar e executar cardápios, além de listar os pratos cadastrados.

## Requisitos

### Requisitos de Software

- **Java Development Kit (JDK)**: Versão 8 ou superior
- **MySQL**: Versão 5.7 ou superior
- **MySQL Connector/J**: Versão 8.0 ou superior
- **IDE Java**: IntelliJ IDEA, Eclipse ou NetBeans

### Requisitos de Hardware

- **Memória RAM**: Mínimo de 2 GB
- **Espaço em Disco**: Mínimo de 200 MB para a instalação do JDK e IDE

## Configuração do Ambiente

### 1. Instalação do JDK

Baixe e instale o JDK a partir do site oficial da Oracle:
- [Download JDK](https://www.oracle.com/java/technologies/javase-downloads.html)

### 2. Instalação do MySQL

Baixe e instale o MySQL a partir do site oficial:
- [Download MySQL](https://dev.mysql.com/downloads/mysql/)

### 3. Configuração do Banco de Dados

1. Abra o MySQL Workbench ou qualquer outro cliente de MySQL.
2. Crie um banco de dados chamado `cardapioautomatizado`:
   ```sql
   CREATE DATABASE cardapioautomatizado;
   ```
3. Use o banco de dados criado:
   ```sql
   USE cardapioautomatizado;
   ```
4. Crie as tabelas necessárias:
   ```sql
   CREATE TABLE ingrediente (
       idIngrediente INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
       nome VARCHAR(40) NOT NULL UNIQUE,
       quantidade INTEGER NOT NULL,
       PRIMARY KEY (idIngrediente)
   );

   CREATE TABLE pratos (
       idPrato INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
       nome VARCHAR(60) NOT NULL UNIQUE,
       preco DECIMAL(10, 2) NOT NULL,
       PRIMARY KEY (idPrato)
   );

   CREATE TABLE ingredientes_pratos (
       idIngrediente INTEGER UNSIGNED NOT NULL,
       idPrato INTEGER UNSIGNED NOT NULL,
       quantidade INTEGER NOT NULL,
       PRIMARY KEY (idIngrediente, idPrato),
       FOREIGN KEY (idIngrediente) REFERENCES ingrediente(idIngrediente),
       FOREIGN KEY (idPrato) REFERENCES pratos(idPrato)
   );
   ```

### 4. Configuração do Projeto Java

1. Clone o repositório do projeto:
   ```bash
   git clone <URL do repositório>
   cd cardapio-automatizado
   ```

2. Abra o projeto em sua IDE Java preferida.

3. Adicione o MySQL Connector/J ao projeto. Faça o download do [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) e adicione o JAR ao build path do seu projeto.

### 5. Configuração do Arquivo `App.java`

1. Abra o arquivo `App.java` e configure a conexão com o banco de dados:
   ```java
   public class App {
       public static final Ingrediente INGREDIENTE = new Ingrediente();
       public static final Cardapio CARDAPIO = new Cardapio();

       public static void main(String[] args) {
           javax.swing.SwingUtilities.invokeLater(new Runnable() {
               public void run() {
                   new SimpleGUI().setVisible(true);
               }
           });
       }
   }

   public class Cardapio {
       private Connection connection;

       public Cardapio() {
           // Configurar a conexão com o banco de dados
           String url = "jdbc:mysql://localhost:3306/cardapioautomatizado";
           String user = "root"; // substitua pelo seu usuário do MySQL
           String password = "senha"; // substitua pela sua senha do MySQL

           try {
               connection = DriverManager.getConnection(url, user, password);
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
       // métodos da classe Cardapio
   }

   public class Ingrediente {
       private Connection connection;

       public Ingrediente() {
           // Configurar a conexão com o banco de dados
           String url = "jdbc:mysql://localhost:3306/cardapioautomatizado";
           String user = "root"; // substitua pelo seu usuário do MySQL
           String password = "senha"; // substitua pela sua senha do MySQL

           try {
               connection = DriverManager.getConnection(url, user, password);
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
       // métodos da classe Ingrediente
   }
   ```

## Execução do Projeto

1. Certifique-se de que o MySQL está em execução.
2. Execute o método `main` na classe `App.java` através da sua IDE.
3. A interface gráfica será exibida, permitindo o gerenciamento de ingredientes e cardápios.

## Funcionalidades Implementadas

### Gestão de Ingredientes

- **Adicionar Ingrediente**: Adiciona novos ingredientes ao banco de dados.
- **Remover Ingrediente**: Remove ingredientes existentes, atualizando o JComboBox após a remoção.
- **Editar Ingrediente**: Edita os detalhes de ingredientes existentes.
- **Listar Ingredientes**: Exibe uma tabela com todos os ingredientes e suas quantidades.

### Gestão de Cardápios

- **Criar Cardápio**: Cria novos cardápios associando pratos aos ingredientes.
- **Executar Cardápio**: Atualiza as quantidades de ingredientes com base na execução de um prato.
- **Remover Cardápio**: Remove cardápios existentes, atualizando o JComboBox após a remoção.
- **Listar Cardápios**: Exibe uma tabela com todos os cardápios cadastrados.

## Testes

### Testes de Unidade

Foram realizados testes de unidade para verificar a funcionalidade de métodos individuais nas classes `Ingrediente` e `Cardapio`, garantindo que operações de banco de dados e lógica de negócios funcionassem corretamente.

### Testes de Integração

Testes de integração foram realizados para assegurar que as diferentes partes do sistema funcionassem bem juntas, especialmente a interação entre a GUI e a camada de persistência.

### Testes Manuais

Testes manuais foram realizados na interface gráfica para verificar a usabilidade e a correta execução das funcionalidades de gerenciamento de ingredientes e cardápios.

## Conclusão

Este sistema oferece uma solução eficiente para o gerenciamento de cardápios e estoques de ingredientes. Através de uma interface gráfica intuitiva e operações de banco de dados robustas, ele permite uma gestão completa e integrada de um estabelecimento culinário.
