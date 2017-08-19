// numero 1

package br.com.senai.DAO;

import br.com.senai.jdbc.ConnectionFactory;
import br.com.senai.modeloCliente.Cliente;
import br.com.senai.modeloContas.Conta;
import br.com.senai.modeloContas.ContaCorrente;
import br.com.senai.modeloContas.ContaPoupanca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Load {

    /*Essa classe, a classe Write e a classe ConnectionFactory tem todos os métodos e variáveis marcadas como STATIC
     Isso significa que eu não preciso criar um objeto delas para chamar qualquer metodo ou variável delas.
     Como eu não preciso criar um objeto delas, isto é, instancia-las, elas também são classes abstract
     */
    private static Connection connection = null; //Conexão
    private static PreparedStatement selectAllClientes = null; //Seleciona todos os clientes do BD
    private static PreparedStatement selectAllContas = null; //Seleciona todas as contas do BD

    private static Statement createDatabase = null; //Cria database se não existir
    private static Statement useDatabase = null;//Use bdbancossa;
    private static Statement createCliente = null; //cria tabela clientes se não existir
    private static Statement createConta = null; //cria tabela conta se não existir

    //Campo static{ } é a primeira coisa que será executado sempre que você chamar qualquer metodo ou variável dessa classe
    static {
        try {
            //conecta no banco de dados (direto no mysql, sem conectar numa database específica)
            connection = ConnectionFactory.createConnection("jdbc:mysql://localhost:3306/", "root", "admin");

            //cria a o bd se não existir
            // se existir, só vai executar o "use bdbancossa"
            createDatabase();

            //depois que deu o use bdbancossa, dá um select nas duas tabela
            selectAllClientes = connection.prepareStatement("SELECT * FROM cliente");
            selectAllContas = connection.prepareStatement("SELECT * FROM conta");

            //processa o resultado dos dois selects
            query();
        } catch (SQLException ex) {
            //printa o erro em vermelho na linha de comando
            ex.printStackTrace();
            //encerra o programa com erro
            System.exit(1);
        }
    }

    public static void createDatabase() throws SQLException {
        //Cria os Statement
        createDatabase = connection.createStatement();
        useDatabase = connection.createStatement();
        createCliente = connection.createStatement();
        createConta = connection.createStatement();
        /*  /\ Acima eu usei Statement em vez de PreparedStatement porque
         Statement é quando não precisa de inserir parâmetros (os ponto de interrogação)*/

        //Executa os Statements
        createDatabase.execute("CREATE DATABASE IF NOT EXISTS BDBANCOSSA");
        useDatabase.execute("USE BDBANCOSSA");
        createCliente.execute("CREATE TABLE IF NOT EXISTS cliente (nome VARCHAR(255) NOT NULL, cpf VARCHAR(20) NOT NULL PRIMARY KEY)");
        createConta.execute("CREATE TABLE IF NOT EXISTS conta ( numero BIGINT NOT NULL PRIMARY KEY, senha INT NOT NULL, saldo DOUBLE NOT NULL DEFAULT 0, cpf_cliente VARCHAR(20) NOT NULL, is_corrente BOOLEAN NOT NULL, CONSTRAINT fk_cliente_conta FOREIGN KEY (cpf_cliente) REFERENCES cliente (cpf), CONSTRAINT kunique UNIQUE (is_corrente , cpf_cliente))");
        //Acima eu uso o execute em vex do executeQuery porque eu não preciso retornar o resultado desses comandos (ResultSet)
    }

    public static void query() throws SQLException {
        //Abaixo eu crio os dois ResultSet que vão armazenar os resultados de Select cliente e select conta respectivamente
        ResultSet resultSetCliente = null;
        ResultSet resultSetConta = null;

        /*Eu criei uma static ArrayList<Cliente> e uma static ArrayList<Conta> nas
         classes Cliente e Conta respectivamente. Essas ArrayLists armazenam todos os objetos Cliente e Conta do BD.
         */
        /*Como a ArrayList está com o modificador static, ela é uma variável da classe, e não do objeto
         Sendo assim, todos os objetos da classe Cliente compartilham a mesma ArrayList, e o mesmo com a classe Conta.
         */
        /*Como a ArrayList está com o modificador static, posso acessa-las direto pelo nome da classe:
         Cliente.clientes;
         Conta.contas;
         Não preciso criar um objeto para acessar essas ArrayLists
         */
        //Apago todos os objetos Cliente que estejam previamente armazenados na ArrayList
        while (!Cliente.clientes.isEmpty())/*Enquanto a ArrayList cliente NÃO estiver vazia*/ {
            Cliente.clientes.remove(0); //Remove cliente
        }

        //Apago todos os objetos Conta que estejam previamente armazenados na ArrayList
        while (!Conta.contas.isEmpty()) {
            Conta.contas.remove(0);
        }

        try {
            //Executa os dois selects e armazena os resultados nos ResultSets
            resultSetCliente = selectAllClientes.executeQuery();
            resultSetConta = selectAllContas.executeQuery();

            //Cria um objeto Conta para cada linha da tabela conta no BD
            while (resultSetConta.next()) {
                long numero = resultSetConta.getLong("numero");
                int senha = resultSetConta.getInt("senha");
                double saldo = resultSetConta.getDouble("saldo");
                String cpf = resultSetConta.getString("cpf_cliente");
                if (resultSetConta.getBoolean("is_corrente"))/*Tem um campo boolean na tabela conta do BD, se for TRUE é conta corrente, se for FALSE é conta poupança*/ {
                    new ContaCorrente(numero, senha, saldo, cpf); //Ao criar um objeto Conta, ele é automaticamente adicionado à ArrayList<Conta> (Ver no construtor)
                } else {
                    new ContaPoupanca(numero, senha, saldo, cpf);
                }
            }

            //Cria um objeto Cliente para cada linha da tabela conta no BD
            while (resultSetCliente.next()) {
                String nome = resultSetCliente.getString("nome");
                String cpf = resultSetCliente.getString("cpf");

                //Ao criar um objeto Cliente, ele é automaticamente adicionado à ArrayList<Cliente> (Ver no construtor)
                new Cliente(nome, cpf, (ContaPoupanca) Conta.getConta(cpf, false), (ContaCorrente) Conta.getConta(cpf, true));
            }

        } catch (SQLException ex) {
            //Caso algo dê errado, ele captura a excessão, e re-lança ela com a mensagem "Erro ao Carregar"
            throw new SQLException("Erro ao carregar.", ex);
        } finally {
            //Fecha os resultSets independente do que aconteceu antes.
            if (!(resultSetCliente == null))/*Verifica se há algum objeto ResultSet na variável resultSetCliente (Se resultSetCliente não for nulo). Não há como fechar o ResultSet se não houver um ResultSet*/ {
                if (!resultSetCliente.isClosed())/*Verifica se não está fechado (Se não estiver fechado). Não tem como fechar algo que já está fechado*/ {
                    resultSetCliente.close(); //Fecha
                }
            }
            if (!(resultSetConta == null)) {
                if (!resultSetConta.isClosed()) {
                    resultSetConta.close();
                }
            }
        }
    }

}
