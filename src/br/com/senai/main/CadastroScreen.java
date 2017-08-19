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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Alefe Lucas
 */
public class CadastroScreen extends javax.swing.JFrame {

    /**
     * Creates new form StartScreen
     */
    public CadastroScreen() {
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
        corpoCadastroPanel = new JPanel();
        cadastroPanel = new JPanel();
        nomeLabel = new JLabel();
        nomeTextField = new JTextField();
        cpfLabel = new JLabel();
        cpfTextField = new JTextField();
        vazioPanel3 = new JPanel();
        cadastraPanel = new JPanel();
        cadastraButton = new JButton();
        voltarButton = new JButton();
        vazioPanel2 = new JPanel();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Banco SSA");
        setPreferredSize(new Dimension(350, 300));
        setResizable(false);

        tudoPanel.setLayout(new BorderLayout());

        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setIcon(new ImageIcon(getClass().getResource("/br/com/senai/main/loco SSA.png"))); // NOI18N
        tudoPanel.add(tituloLabel, BorderLayout.PAGE_START);

        cadastroPanel.setBorder(BorderFactory.createTitledBorder(null, "Cadastro de Cliente:", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, new Font("sansserif", 1, 18))); // NOI18N
        cadastroPanel.setPreferredSize(new Dimension(300, 165));
        cadastroPanel.setLayout(new GridLayout(3, 2, 5, 5));

        nomeLabel.setText("Nome:");
        cadastroPanel.add(nomeLabel);
        cadastroPanel.add(nomeTextField);

        cpfLabel.setText("CPF:");
        cadastroPanel.add(cpfLabel);

        cpfTextField.addActionListener(formListener);
        cadastroPanel.add(cpfTextField);
        cadastroPanel.add(vazioPanel3);

        cadastraButton.setText("Cadastrar");
        cadastraButton.setPreferredSize(new Dimension(85, 30));
        cadastraPanel.add(cadastraButton);

        cadastroPanel.add(cadastraPanel);

        corpoCadastroPanel.add(cadastroPanel);

        voltarButton.setText("Voltar");
        voltarButton.setPreferredSize(new Dimension(150, 30));
        corpoCadastroPanel.add(voltarButton);

        vazioPanel2.setPreferredSize(new Dimension(150, 10));
        corpoCadastroPanel.add(vazioPanel2);

        tudoPanel.add(corpoCadastroPanel, BorderLayout.CENTER);

        getContentPane().add(tudoPanel, BorderLayout.CENTER);

        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements ActionListener {
        FormListener() {}
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == cpfTextField) {
                CadastroScreen.this.cpfTextFieldActionPerformed(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    private void cpfTextFieldActionPerformed(ActionEvent evt) {//GEN-FIRST:event_cpfTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cpfTextFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton cadastraButton;
    private JPanel cadastraPanel;
    private JPanel cadastroPanel;
    private JPanel corpoCadastroPanel;
    private JLabel cpfLabel;
    private JTextField cpfTextField;
    private JLabel nomeLabel;
    private JTextField nomeTextField;
    private JLabel tituloLabel;
    private JPanel tudoPanel;
    private JPanel vazioPanel2;
    private JPanel vazioPanel3;
    private JButton voltarButton;
    // End of variables declaration//GEN-END:variables
}
