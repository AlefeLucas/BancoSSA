/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senai.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Alefe Lucas
 */
public class StartScreen extends javax.swing.JFrame {

    /**
     * Creates new form StartScreen
     */
    public StartScreen() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tudoPanel = new JPanel();
        tituloLabel = new JLabel();
        corpoLogInPanel = new JPanel();
        logarPanel = new JPanel();
        numeroLabel = new JLabel();
        numeroTextField = new JTextField();
        passwordLabel = new JLabel();
        passwordField = new JPasswordField();
        vazioPanel = new JPanel();
        logInPanel = new JPanel();
        logInButton = new JButton();
        newContaButton = new JButton();
        newClienteButton = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Banco SSA");
        setPreferredSize(new Dimension(350, 300));
        setResizable(false);

        tudoPanel.setLayout(new BorderLayout());

        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setIcon(new ImageIcon(getClass().getResource("/br/com/senai/main/loco SSA.png"))); // NOI18N
        tudoPanel.add(tituloLabel, BorderLayout.PAGE_START);

        logarPanel.setBorder(BorderFactory.createTitledBorder(null, "Logar:", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, new Font("sansserif", 1, 18))); // NOI18N
        logarPanel.setPreferredSize(new Dimension(300, 165));
        logarPanel.setLayout(new GridLayout(3, 2, 5, 5));

        numeroLabel.setText("Numero da Conta:");
        logarPanel.add(numeroLabel);
        logarPanel.add(numeroTextField);

        passwordLabel.setText("Senha:");
        logarPanel.add(passwordLabel);
        logarPanel.add(passwordField);
        logarPanel.add(vazioPanel);

        logInButton.setText("Logar");
        logInButton.setPreferredSize(new Dimension(85, 30));
        logInPanel.add(logInButton);

        logarPanel.add(logInPanel);

        corpoLogInPanel.add(logarPanel);

        newContaButton.setText("Nova Conta");
        newContaButton.setPreferredSize(new Dimension(150, 30));
        corpoLogInPanel.add(newContaButton);

        newClienteButton.setText("Cadastrar Cliente");
        newClienteButton.setPreferredSize(new Dimension(150, 30));
        corpoLogInPanel.add(newClienteButton);

        tudoPanel.add(corpoLogInPanel, BorderLayout.CENTER);

        getContentPane().add(tudoPanel, BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
    /**
     * @param args the command line arguments
    /**
     * @param args the command line arguments
    /**
     * @param args the command line arguments
    /**
     * @param args the command line arguments
    /**
     * @param args the command line arguments
    /**
     * @param args the command line arguments
    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel corpoLogInPanel;
    private JButton logInButton;
    private JPanel logInPanel;
    private JPanel logarPanel;
    private JButton newClienteButton;
    private JButton newContaButton;
    private JLabel numeroLabel;
    private JTextField numeroTextField;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JLabel tituloLabel;
    private JPanel tudoPanel;
    private JPanel vazioPanel;
    // End of variables declaration//GEN-END:variables
}
