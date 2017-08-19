// numero 8
package br.com.senai.main;

// <editor-fold defaultstate="collapsed" desc="IMPORTAÇÕES">    
import br.com.senai.DAO.Load;
import br.com.senai.modeloCliente.Cliente;
import br.com.senai.modeloContas.Conta;
import javax.swing.JPasswordField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

// </editor-fold>  
public class BancoSSA extends javax.swing.JFrame {

    private Conta conta;

    public BancoSSA() {
        initialize();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="INICIAR JFRAME">
    private void initialize() {
        tituloLabel = new JLabel();
        tudoPanel = new JPanel();
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setIcon(new ImageIcon(getClass().getResource("/br/com/senai/main/loco SSA.png")));
        tudoPanel.add(tituloLabel, BorderLayout.NORTH);

        initStart();
    }
    // </editor-fold>   

    // <editor-fold defaultstate="collapsed" desc="LOGAR CODE">                          
    private void initStart() {
        try {
            Load.query();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
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
        tudoPanel.add(tituloLabel, BorderLayout.NORTH);
        logarPanel.setBorder(BorderFactory.createTitledBorder(null, "Logar:", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, new Font("sansserif", 1, 18)));
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

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean valido = false;

                try {
                    long numero = Long.parseLong(numeroTextField.getText());

                    char[] passChar = passwordField.getPassword();
                    String pass = "";
                    for (int x = 0; x < passChar.length; x++) {
                        pass = pass + passChar[x];
                    }

                    int senha = Integer.parseInt(pass);

                    conta = Conta.getConta(numero);
                    valido = conta.logIn(senha);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(getContentPane(), "Conta inexistente e/ou senha incorreta", "Erro de LogIn", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(getContentPane(), ex.getMessage(), "Erro de LogIn", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalStateException ex) {
                    JOptionPane.showMessageDialog(getContentPane(), ex.getMessage(), "Erro de LogIn", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(getContentPane(), ex.getStackTrace(), "Erro de LogIn", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                } finally {
                    numeroTextField.setText("");
                    passwordField.setText("");
                }
                if (valido) {
                    tudoPanel.remove(corpoLogInPanel);
                    initOperacoes();
                }
            }
        });

        logInPanel.add(logInButton);

        logarPanel.add(logInPanel);

        corpoLogInPanel.add(logarPanel);

        newContaButton.setText(
                "Nova Conta");
        newContaButton.setPreferredSize(
                new Dimension(150, 30));
        corpoLogInPanel.add(newContaButton);

        newClienteButton.setText(
                "Cadastrar Cliente");
        newClienteButton.setPreferredSize(
                new Dimension(150, 30));

        corpoLogInPanel.add(newClienteButton);

        tudoPanel.add(corpoLogInPanel, BorderLayout.CENTER);

        this.add(tudoPanel, BorderLayout.CENTER);

        pack();

        UIManager.LookAndFeelInfo[] looks;
        looks = UIManager.getInstalledLookAndFeels();

        try {
            UIManager.setLookAndFeel(looks[3].getClassName());
            SwingUtilities.updateComponentTreeUI(numeroTextField);
            SwingUtilities.updateComponentTreeUI(passwordField);
            SwingUtilities.updateComponentTreeUI(numeroTextField);
            UIManager.setLookAndFeel(looks[1].getClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BancoSSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(BancoSSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BancoSSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(BancoSSA.class.getName()).log(Level.SEVERE, null, ex);
        }

        newClienteButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e
                    ) {
                        tudoPanel.remove(corpoLogInPanel);
                        initCadastro();
                    }
                }
        );

        newContaButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e
                    ) {
                        tudoPanel.remove(corpoLogInPanel);
                        initNovaConta();
                    }
                }
        );
    }// </editor-fold>                        

    // <editor-fold defaultstate="collapsed" desc="NOVA CONTA CODE">
    private void initNovaConta() {

        tipoDeContaButtonGroup = new ButtonGroup();
        corpoNovaContaPanel = new JPanel();
        novaContaPanel = new JPanel();
        cpfLabel2 = new JLabel();
        cpfTextField2 = new JTextField();
        tipoContaLabel = new JLabel();
        tipoContaPanel = new JPanel();
        correnteRadioButton = new JRadioButton();
        poupancaRadioButton = new JRadioButton();
        depositoInicialLabel = new JLabel();
        depositoInicialTextField = new JTextField();
        vazioPanel4 = new JPanel();
        criaPanel = new JPanel();
        criaButton = new JButton();
        voltarButton2 = new JButton();
        vazioPanel5 = new JPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Banco SSA");
        setPreferredSize(new Dimension(350, 340));
        setResizable(false);

        tudoPanel.setLayout(new BorderLayout());

        tudoPanel.add(tituloLabel, BorderLayout.NORTH);

        novaContaPanel.setBorder(BorderFactory.createTitledBorder(null, "Nova Conta:", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, new Font("sansserif", 1, 18)));
        novaContaPanel.setPreferredSize(new Dimension(300, 200));
        novaContaPanel.setLayout(new GridLayout(4, 2, 5, 5));

        cpfLabel2.setText("CPF:");
        novaContaPanel.add(cpfLabel2);
        novaContaPanel.add(cpfTextField2);

        tipoContaLabel.setText("Tipo de Conta:");
        novaContaPanel.add(tipoContaLabel);

        tipoContaPanel.setLayout(new GridLayout(2, 1));

        tipoDeContaButtonGroup.add(correnteRadioButton);
        correnteRadioButton.setSelected(true);
        correnteRadioButton.setText("Conta Corrente");
        tipoContaPanel.add(correnteRadioButton);

        tipoDeContaButtonGroup.add(poupancaRadioButton);
        poupancaRadioButton.setText("Conta Poupança");
        tipoContaPanel.add(poupancaRadioButton);
        ContaRadioButton contaHandler = new ContaRadioButton();

        correnteRadioButton.addItemListener(contaHandler);
        poupancaRadioButton.addItemListener(contaHandler);

        novaContaPanel.add(tipoContaPanel);

        depositoInicialLabel.setText("Depósito Inicial:");
        novaContaPanel.add(depositoInicialLabel);

        depositoInicialTextField.setText("100");
        depositoInicialTextField.setEnabled(false);
        depositoInicialTextField.setToolTipText(">= 100 para Conta Poupança");
        novaContaPanel.add(depositoInicialTextField);
        novaContaPanel.add(vazioPanel4);

        criaButton.setText("Criar");
        criaButton.setPreferredSize(new Dimension(85, 30));
        criaButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = cpfTextField2.getText();
                if (!cpf.isEmpty()) {
                    try {
                        Cliente cliente = Cliente.getCliente(cpf);
                        if (correnteRadioButton.isSelected()) {
                            try {
                                int senha = cliente.createContaCorrente();

                                String msg = ("Conta criada com sucesso:\n");
                                msg = msg + ("\nCliente: " + cliente.getNome());
                                msg = msg + ("\nNumero da conta: " + cliente.getContaCorrente().getNumero());
                                msg = msg + ("\nSenha: " + senha);
                                msg = msg + ("\nTipo: Conta Corrente");

                                JOptionPane.showMessageDialog(getContentPane(), msg, "Nova Conta", JOptionPane.INFORMATION_MESSAGE);
                                voltarButton2.doClick();
                                numeroTextField.setText("" + cliente.getContaCorrente().getNumero());
                                passwordField.setText(senha + "");
                            } catch (IllegalStateException ex) {
                                JOptionPane.showMessageDialog(getContentPane(), "Este cliente já possui Conta Corrente", "Erro", JOptionPane.ERROR_MESSAGE);
                                cpfTextField2.setText("");
                            }
                        } else if (poupancaRadioButton.isSelected()) {
                            if (!depositoInicialTextField.getText().isEmpty()) {
                                try {
                                    double valor = Double.parseDouble(depositoInicialTextField.getText());
                                    int senha = cliente.createContaPoupanca(valor);

                                    String msg = ("Conta criada com sucesso:\n");
                                    msg = msg + ("\nCliente: " + cliente.getNome());
                                    msg = msg + ("\nNumero da conta: " + cliente.getContaPoupanca().getNumero());
                                    msg = msg + ("\nSenha: " + senha);
                                    msg = msg + ("\nTipo: Conta Poupança");

                                    JOptionPane.showMessageDialog(getContentPane(), msg, "Nova Conta", JOptionPane.INFORMATION_MESSAGE);
                                    voltarButton2.doClick();
                                    numeroTextField.setText("" + cliente.getContaPoupanca().getNumero());
                                    passwordField.setText(senha + "");
                                } catch (InputMismatchException ex) {
                                    JOptionPane.showMessageDialog(getContentPane(), "Depósito inicial inválido", "Erro", JOptionPane.ERROR_MESSAGE);
                                    depositoInicialTextField.setText("");
                                } catch (IllegalArgumentException ex) {
                                    JOptionPane.showMessageDialog(getContentPane(), "Depósito inicial insuficiente\nPor favor digite um valor a partir de R$ 100", "Erro", JOptionPane.ERROR_MESSAGE);
                                    depositoInicialTextField.setText("100");
                                } catch (IllegalStateException ex) {
                                    JOptionPane.showMessageDialog(getContentPane(), "Este cliente já possui Conta Poupança", "Erro", JOptionPane.ERROR_MESSAGE);
                                    cpfTextField2.setText("");
                                }
                            } else {
                                JOptionPane.showMessageDialog(getContentPane(), "Campo de depósito inicial vazio", "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (IllegalStateException ex) {
                        JOptionPane.showMessageDialog(getContentPane(), "Cliente não cadastrado", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(getContentPane(), "CPF em branco", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        criaPanel.add(criaButton);

        novaContaPanel.add(criaPanel);

        corpoNovaContaPanel.add(novaContaPanel);

        voltarButton2.setText("Voltar");
        voltarButton2.setPreferredSize(new Dimension(150, 30));
        voltarButton2.addActionListener(new BackToHome());
        corpoNovaContaPanel.add(voltarButton2);

        vazioPanel5.setPreferredSize(new Dimension(150, 10));
        corpoNovaContaPanel.add(vazioPanel5);

        tudoPanel.add(corpoNovaContaPanel, BorderLayout.CENTER);

        getContentPane().add(tudoPanel, BorderLayout.CENTER);

        pack();
    }// </editor-fold>     

    // <editor-fold defaultstate="collapsed" desc="CADASTRO CODE">   
    private void initCadastro() {

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

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Banco SSA");
        setPreferredSize(new Dimension(350, 300));
        setResizable(false);

        tudoPanel.setLayout(new BorderLayout());

        tudoPanel.add(tituloLabel, BorderLayout.NORTH);

        cadastroPanel.setBorder(BorderFactory.createTitledBorder(null, "Cadastro de Cliente:", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, new Font("sansserif", 1, 18)));
        cadastroPanel.setPreferredSize(new Dimension(300, 165));
        cadastroPanel.setLayout(new GridLayout(3, 2, 5, 5));

        nomeLabel.setText("Nome:");
        cadastroPanel.add(nomeLabel);
        cadastroPanel.add(nomeTextField);

        cpfLabel.setText("CPF:");
        cadastroPanel.add(cpfLabel);

        cadastroPanel.add(cpfTextField);
        cadastroPanel.add(vazioPanel3);

        cadastraButton.setText("Cadastrar");
        cadastraButton.setPreferredSize(new Dimension(85, 30));
        cadastraButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (nomeTextField.getText().isEmpty() || cpfTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(getContentPane(), "Nome e/ou CPF inválidos", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        String nome = nomeTextField.getText();
                        String cpf = cpfTextField.getText();
                        new Cliente(nome, cpf);
                        JOptionPane.showMessageDialog(getContentPane(), "Cliente cadastrado com sucesso", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
                        voltarButton.doClick();
                        newContaButton.doClick();
                        cpfTextField2.setText(cpf);
                    } catch (IllegalArgumentException ex) {
                        cpfTextField.setText("");
                        JOptionPane.showMessageDialog(getContentPane(), "CPF já cadastrado", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        cadastraPanel.add(cadastraButton);

        cadastroPanel.add(cadastraPanel);

        corpoCadastroPanel.add(cadastroPanel);

        voltarButton.setText("Voltar");
        voltarButton.setPreferredSize(new Dimension(150, 30));
        voltarButton.addActionListener(new BackToHome());
        corpoCadastroPanel.add(voltarButton);

        vazioPanel2.setPreferredSize(new Dimension(150, 10));
        corpoCadastroPanel.add(vazioPanel2);

        tudoPanel.add(corpoCadastroPanel, BorderLayout.CENTER);

        getContentPane().add(tudoPanel, BorderLayout.CENTER);

        pack();
    }
// </editor-fold>    

    // <editor-fold defaultstate="collapsed" desc="OPERAÇÕES CODE"> 
    private void initOperacoes() {

        operacaoButtonGroup = new ButtonGroup();
        corpoContaPanel = new JPanel();
        contaPanel = new JPanel();
        operacaoPanel = new JPanel();
        operacaoLabel = new JLabel();
        selectOperacaoPanel = new JPanel();
        depositoRadioButton = new JRadioButton();
        saqueRadioButton = new JRadioButton();
        saldoRadioButton = new JRadioButton();
        valorPanel = new JPanel();
        valorLabel = new JLabel();
        valorTextField = new JTextField();
        vazioPanel7 = new JPanel();
        okPanel = new JPanel();
        okButton = new JButton();
        sairButton = new JButton();
        vazioPanel6 = new JPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Banco SSA");
        setPreferredSize(new Dimension(350, 340));
        setResizable(false);

        tudoPanel.setLayout(new BorderLayout());

        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tituloLabel.setIcon(new ImageIcon(getClass().getResource("/br/com/senai/main/loco SSA.png")));
        tudoPanel.add(tituloLabel, BorderLayout.PAGE_START);

        contaPanel.setBorder(BorderFactory.createTitledBorder(null, "Conta:", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, new Font("sansserif", 1, 18)));
        contaPanel.setPreferredSize(new Dimension(300, 200));
        contaPanel.setLayout(new BorderLayout());

        operacaoPanel.setLayout(new GridLayout(1, 2));

        operacaoLabel.setText("Operação:");
        operacaoPanel.add(operacaoLabel);

        selectOperacaoPanel.setLayout(new GridLayout(3, 1));

        operacaoButtonGroup.add(depositoRadioButton);
        depositoRadioButton.setSelected(true);
        depositoRadioButton.setText("Depósito");
        selectOperacaoPanel.add(depositoRadioButton);

        operacaoButtonGroup.add(saqueRadioButton);
        saqueRadioButton.setText("Saque");
        selectOperacaoPanel.add(saqueRadioButton);

        operacaoButtonGroup.add(saldoRadioButton);
        saldoRadioButton.setText("Saldo");
        selectOperacaoPanel.add(saldoRadioButton);

        operacaoPanel.add(selectOperacaoPanel);

        contaPanel.add(operacaoPanel, BorderLayout.NORTH);

        valorPanel.setLayout(new GridLayout(2, 2, 5, 5));

        valorLabel.setText("Valor:");
        valorPanel.add(valorLabel);

        valorPanel.add(valorTextField);
        valorPanel.add(vazioPanel7);

        okButton.setText("OK");
        okButton.setPreferredSize(new Dimension(85, 30));
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (saldoRadioButton.isSelected()) {
                    JOptionPane.showMessageDialog(getContentPane(), String.format("Saldo: R$%.2f%n", conta.verSaldo()), "Saldo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    double valor;
                    if (valorTextField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(getContentPane(), "Valor não informado", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            valor = Double.parseDouble(valorTextField.getText());
                            if (valor > 0.0) {
                                if (saqueRadioButton.isSelected()) {
                                    conta.sacar(valor);
                                    JOptionPane.showMessageDialog(getContentPane(), "Sacado com sucesso", "Status", JOptionPane.INFORMATION_MESSAGE);
                                } else if (depositoRadioButton.isSelected()) {
                                    conta.depositar(valor);
                                    JOptionPane.showMessageDialog(getContentPane(), "Depositado com sucesso", "Status", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } else {
                                throw new NumberFormatException();
                            }
                        } catch (NumberFormatException ex) {
                            valorTextField.setText("");
                            JOptionPane.showMessageDialog(getContentPane(), "Valor inválido", "Erro", JOptionPane.ERROR_MESSAGE);
                        } catch (IllegalArgumentException ex) {
                            valorTextField.setText("");
                            JOptionPane.showMessageDialog(getContentPane(), "Saldo insuficiente", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        okPanel.add(okButton);

        valorPanel.add(okPanel);

        contaPanel.add(valorPanel, BorderLayout.SOUTH);

        corpoContaPanel.add(contaPanel);

        sairButton.setText("Sair");
        sairButton.setPreferredSize(new Dimension(150, 30));
        corpoContaPanel.add(sairButton);
        sairButton.addActionListener(new BackToHome());
        vazioPanel6.setPreferredSize(new Dimension(150, 10));
        corpoContaPanel.add(vazioPanel6);

        OperacoesRadioButton handlesOp = new OperacoesRadioButton();

        depositoRadioButton.addItemListener(handlesOp);
        saqueRadioButton.addItemListener(handlesOp);
        saldoRadioButton.addItemListener(handlesOp);

        tudoPanel.add(corpoContaPanel, BorderLayout.CENTER);

        getContentPane().add(tudoPanel, BorderLayout.CENTER);

        pack();
    }
    // </editor-fold>   

    // <editor-fold defaultstate="collapsed" desc="LISTENERS"> 
    private class OperacoesRadioButton implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            JRadioButton source = (JRadioButton) e.getSource();
            if (source.isSelected()) {
                if (source.equals(saldoRadioButton)) {
                    valorTextField.setEnabled(false);
                } else {
                    valorTextField.setEnabled(true);
                }
            }
        }
    }

    private class ContaRadioButton implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            JRadioButton source = (JRadioButton) e.getSource();
            if (source.isSelected()) {
                if (source.equals(correnteRadioButton)) {
                    depositoInicialTextField.setEnabled(false);
                } else {
                    depositoInicialTextField.setEnabled(true);
                }
            }
        }
    }

    private class BackToHome implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            tudoPanel.remove(1);
            if (!(conta == null)) {
                if (conta.isLogado()) {
                    conta.logOff();
                }
            }
            initStart();
        }
    }
    // </editor-fold>   

    // <editor-fold defaultstate="collapsed" desc="MAIN">    
    public static void main(String args[]) {
        BancoSSA b = null;
        try {
            try {
                Load.query(); //Carrega o banco de dados ao inicar o programa
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.exit(1);
            }

            setNimbusLookAndFeel(); //Muda o estilo da janela para Nimbus
            b = new BancoSSA(); //Chama o construtor do JFrame, que vai iniciar a janela
            b.setLocationRelativeTo(null); //Centraliza a janela
            b.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            //Os erros da classe main (a classe que estende JFrame) são exibidos numa tela de erro do JOptionPane:
            JOptionPane.showMessageDialog(b, ex.getMessage() + "\n" + ex.getStackTrace(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }
    // </editor-fold>   

    // <editor-fold defaultstate="collapsed" desc="Set Nimbus Look And Feel">   
    public static void setNimbusLookAndFeel() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BancoSSA.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BancoSSA.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BancoSSA.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BancoSSA.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
 // </editor-fold>   

    // <editor-fold defaultstate="collapsed" desc="VARIAVEIS CLASSE">                       
    private JLabel tituloLabel;
    private JPanel tudoPanel;

    //START
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
    private JPanel vazioPanel;

    //Logado
    private JPanel contaPanel;
    private JPanel corpoContaPanel;
    private JRadioButton depositoRadioButton;
    private JButton okButton;
    private JPanel okPanel;
    private ButtonGroup operacaoButtonGroup;
    private JLabel operacaoLabel;
    private JPanel operacaoPanel;
    private JButton sairButton;
    private JRadioButton saldoRadioButton;
    private JRadioButton saqueRadioButton;
    private JPanel selectOperacaoPanel;
    private JLabel valorLabel;
    private JPanel valorPanel;
    private JTextField valorTextField;
    private JPanel vazioPanel6;
    private JPanel vazioPanel7;

    //NOVA CONTA
    private JPanel corpoNovaContaPanel;
    private JRadioButton correnteRadioButton;
    private JLabel cpfLabel2;
    private JTextField cpfTextField2;
    private JButton criaButton;
    private JPanel criaPanel;
    private JLabel depositoInicialLabel;
    private JTextField depositoInicialTextField;
    private JPanel novaContaPanel;
    private JRadioButton poupancaRadioButton;
    private JLabel tipoContaLabel;
    private JPanel tipoContaPanel;
    private ButtonGroup tipoDeContaButtonGroup;
    private JPanel vazioPanel4;
    private JPanel vazioPanel5;
    private JButton voltarButton2;

    //CADASTRO
    private JButton cadastraButton;
    private JPanel cadastraPanel;
    private JPanel cadastroPanel;
    private JPanel corpoCadastroPanel;
    private JLabel cpfLabel;
    private JTextField cpfTextField;
    private JLabel nomeLabel;
    private JTextField nomeTextField;
    private JPanel vazioPanel2;
    private JPanel vazioPanel3;
    private JButton voltarButton;
    // </editor-fold>   

}
