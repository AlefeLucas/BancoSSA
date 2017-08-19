// numero 2

package br.com.senai.DAO;

import br.com.senai.jdbc.ConnectionFactory;
import br.com.senai.modeloCliente.Cliente;
import br.com.senai.modeloContas.Conta;
import br.com.senai.modeloContas.ContaCorrente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Write {

    private static Connection connection = null;//Conexão

    private static PreparedStatement insertCliente = null; //INSERT INTO CLIENTE
    private static PreparedStatement insertConta = null; // INSERT INTO CONTA
    private static PreparedStatement updateConta = null; // UPDATE CONTA

    static {
        try {
            //Conecta direto a database bdbancossa
            connection = ConnectionFactory.createConnection();

            insertCliente = connection.prepareStatement("INSERT INTO "
                    + "cliente (nome, cpf) VALUES"
                    + " ( ?, ?)");

            insertConta = connection.prepareStatement("INSERT INTO conta "
                    + "(numero, senha, saldo, cpf_cliente, is_corrente) VALUES "
                    + "( ?, ?, ?, ?, ?)");

            updateConta = connection.prepareStatement("UPDATE conta SET saldo = ? WHERE numero = ?");
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    //metodo que insere cliente
    public static int addCliente(Cliente cliente) {

        try {
            //substitui os pontos de interrogação pelo nome e o cpf do objeto Cliente passado como parâmetro
            insertCliente.setString(1, cliente.getNome());
            insertCliente.setString(2, cliente.getCpf());

            //Executa e retorna o numero de linhas afetadas
            return insertCliente.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
            //Chama o metodo close que eu criei lá em baixo
            close();
        }
        //Só vai chegar aqui caso não tenha retornado la em cima, ou seja, só vai retornar 0 se der algum erro nesse metodo
        return 0;
    }

    //metodo que dá update em conta
    public static int updateConta(Conta conta) {

        try {
            updateConta.setDouble(1, conta.getSaldo());
            updateConta.setLong(2, conta.getNumero());

            return updateConta.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            close();
        }
        return 0;
    }

    //metodo que insere conta conta
    public static int addConta(Conta conta) {

        try {
            insertConta.setLong(1, conta.getNumero());
            insertConta.setInt(2, conta.getSenha());
            insertConta.setDouble(3, conta.getSaldo());
            insertConta.setString(4, conta.getCpfDono());
            insertConta.setBoolean(5, (conta instanceof ContaCorrente ? true : false)); //verifica se o objeto conta passado como parâmetro é uma instância da classe ContaCorrente, se for, retorna true para o campo "is_corrente" no BD, se não for, retorna false

            return insertConta.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            close();
        }

        return 0;
    }

    //fecha a conexão
    public static void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

}
