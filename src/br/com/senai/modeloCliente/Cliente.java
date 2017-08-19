// numero 4

package br.com.senai.modeloCliente;

import br.com.senai.DAO.Write;
import br.com.senai.modeloContas.ContaCorrente;
import br.com.senai.modeloContas.ContaPoupanca;
import java.util.ArrayList;
import java.util.Random;

public class Cliente {

    private static ArrayList<String> cpfsCadastrados = new ArrayList<String>(); //lista que armazena todos os CPFs cadastrados. Ao cadastrar Cliente novo, o programa verifica se o novo CPF já está nessa lista, caso não, adiciona o novo CPF a lista, caso sim, retorna "CPF já cadastrado". Essa lista é static porque precisa ser acessada por qualquer objeto Cliente (a mesma lista pra todos os objetos)
    public static ArrayList<Cliente> clientes = new ArrayList<Cliente>(); //static ArrayList<Cliente> que armazena todos os objetos Cliente (tudo que tiver no BD)

    private Random r = new Random(); //A classe Random possui metodos que geram números aleatórios. Usei ela para gerar o numero da conta e a senha

    private String nome;
    private String cpf;
    private ContaPoupanca contaPoupanca; //Um Cliente TEM-UMA ContaPoupanca, portanto a classe Cliente precisa ter uma variável do tipo ContaPoupanca
    private ContaCorrente contaCorrente;
    private boolean temContaPoupanca; //Quando você cria uma ContaPoupanca para esse cliente, isto é, cria um objeto ContaPoupanca e o armazena na variável contaPoupanca acima, essa variável temContaPoupanca muda para true (ver método createContaPoupanca)
    private boolean temContaCorrente;

    //metodo static (metodo da classe) que retorna um objeto Cliente da ArrayList<Cliente>
    public static Cliente getCliente(String cpf) throws IllegalStateException {
        //Ele pega o CPF passado como parâmetro
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) { //Confere se algum dos objetos Cliente armazenado na ArrayList possui esse CPF
                return cliente; //Se sim, retorna esse objeto cliente
            }
        }
        //Caso a ArrayList não tenha um objeto Cliente com o CPF fornecido, lança exceção "Cliente inexistente"        
        throw new IllegalStateException("Cliente inexistente");

    }

    //Construtor Cliente usado pela classe Load.
    public Cliente(String nome, String cpf, ContaPoupanca contaPoupanca, ContaCorrente contaCorrente) {
        this.nome = nome;
        this.cpf = cpf;
        cpfsCadastrados.add(cpf);
        this.contaPoupanca = contaPoupanca;
        this.contaCorrente = contaCorrente;
        this.temContaPoupanca = (contaPoupanca == null ? false : true); //Se a variavel contaPoupanca for null, isso é, não tem nada armazenado nela, isso retorna false, (o cliente não tem conta poupança). Se tiver algum objeto, retorna true (o cliente tem conta poupança)
        this.temContaCorrente = (contaCorrente == null ? false : true);
        clientes.add(this); //Adiciona esse objeto cliente à ArrayList<Cliente> no momento da criação do objeto Cliente

    }

    //Construtor Cliente usado pelo main
    public Cliente(String nome, String cpf) throws IllegalArgumentException {
        if (!cpfsCadastrados.contains(cpf))/*Verifica se o CPF não está cadastrado*/ {
            this.cpf = cpf;
            cpfsCadastrados.add(cpf); //cadastra CPF (adiciona o CPF na lista de CPFs cadastrados)
            this.nome = nome;
            clientes.add(this); //Adiciona esse objeto cliente à ArrayList<Cliente> no momento da criação do objeto Cliente
            Write.addCliente(this); //Insere esse objeto cliente no Banco de Dados.
        } else {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    // metodo para criar ContaPoupanca para este Cliente específico
    public int createContaPoupanca(double depositoInicial) throws IllegalStateException, IllegalArgumentException {
        if (!temContaPoupanca)/*Antes de criar conta poupança, ele verifica de esse cliente já possui uma*/ {
            int senha = r.nextInt(9999); //Gera uma senha aleatória
            this.contaPoupanca = new ContaPoupanca(senha, depositoInicial, this.cpf); //Cria um objeto ContaPoupanca e o armazena na variavel contaPoupanca
            temContaPoupanca = true; //muda pra true
            return senha; //retorna a senha gerada para exibir na tela
        } else {
            throw new IllegalStateException("Este cliente já possui Conta Poupança");
        }
    }

    public int createContaCorrente() throws IllegalStateException {
        if (!temContaCorrente) {
            int senha = r.nextInt(9999);
            this.contaCorrente = new ContaCorrente(senha, this.cpf);
            temContaCorrente = true;
            return senha;
        } else {
            throw new IllegalStateException("Este cliente já possui Conta Corrente");
        }
    }
    
    //metodo que retorna a ContaPoupanca desse Cliente, caso ele tenha
    public ContaPoupanca getContaPoupanca() throws IllegalStateException {
        if (temContaPoupanca) {
            return contaPoupanca; //retorna se tiver
        } else {
            throw new IllegalStateException("Este cliente não possui Conta Poupança");
        }
    }

    public ContaCorrente getContaCorrente() throws IllegalStateException {
        if (temContaCorrente) {
            return contaCorrente;
        } else {
            throw new IllegalStateException("Este cliente não possui Conta Corrente");
        }
    }

}
