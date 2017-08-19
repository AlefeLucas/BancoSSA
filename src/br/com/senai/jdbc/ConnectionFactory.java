// numero 3

package br.com.senai.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionFactory {

    //O modificador final indica que a variável não pode ter o valor alterado, uma vez atribuído
    //Combinar os modificadores static e final transforma a variável numa constante
    private static final String URL = "jdbc:mysql://localhost:3306/bdbancossa"; //URL do bdbancossa
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    //metodo que retorna uma conexão usando as constantes acima
    public static Connection createConnection() throws SQLException {
        try {

            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Impossível conectar", ex);
        }
    }

    //metodo que retorna uma conexão usando os parâmetros fornecidos
    public static Connection createConnection(String url, String user, String password) throws SQLException {
        try {

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException("Impossível conectar", ex);
        }
    }    

}
