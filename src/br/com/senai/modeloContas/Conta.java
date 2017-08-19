// numero 5

package br.com.senai.modeloContas;

import br.com.senai.DAO.Write;
import java.util.ArrayList;
import java.util.Random;

//Essa classe é abstrata porque ela não pode ser instanciada. Toda conta tem um tipo, Corrente ou Poupanca, portanto eu tenho que instanciar ContaCorrente ou ContaPoupanca, e não apenas Conta (teria um tipo indefinido se pudesse)
public abstract class Conta {

    private static ArrayList<Long> numerosExistentes = new ArrayList<Long>(); //lista que armazena todos os Numeros cadastrados. Ao cadastrar Conta nova, o programa verifica se o novo Numero já está nessa lista, caso não, adiciona o novo Numero a lista, caso sim, gera outro numero e verifica de novo. Essa lista é static porque precisa ser acessada por qualquer objeto Conta (a mesma lista pra todos os objetos)
    public static ArrayList<Conta> contas = new ArrayList<Conta>();//static ArrayList<Conta> que armazena todos os objetos Conta (tudo que tiver no BD)

    private Random r = new Random(); //A classe Random possui metodos que geram números aleatórios. Usei ela para gerar o numero da conta e a senha

    private long numero;
    private int senha;
    private double saldo = 0;
    private String cpfDono;

    private boolean logado; //ao logar nessa conta, (chamando o metodo logIn), esse boolean muda pra true. Só é possivel depositar, sacar e ver  saldo se essa variavel for true

    //metodo static (metodo da classe) que retorna um objeto Conta da ArrayList<Conta> de acordo com o numero fornecido
    public static Conta getConta(long numero) throws IllegalStateException {
        //Ele pega o numero passado como parâmetro
        for (Conta conta : contas) {
            if (conta.getNumero() == numero) { //Confere se algum dos objetos Conta armazenado na ArrayList possui esse numero
                return conta; //Se sim, retorna o objeto conta
            }
        }
        //Caso a ArrayList não tenha um objeto Conta com o numero fornecido, lança exceção "Conta inexistente"    
        throw new IllegalStateException("Conta inexistente");
    }

    //metodo static (metodo da classe) que retorna um objeto Conta da ArrayList<Conta> de acordo com cpf fornecido
    public static Conta getConta(String cpf, boolean isCorrente) {
        //Como um cliente pode ter conta corrente e poupança, além do CPF eu passa um boolean isCorrente como parâmetro
        //Sendo assim, esse metodo retorna uma ContaCorrente se isCorrente for true, e retorna uma ContaPoupanca se isCorrente for false
        for (Conta conta : contas) {
            if (conta.getCpfDono().equals(cpf)) { //Confere se algum dos objetos Conta armazenado na ArrayList possui esse CPF
                if (conta instanceof ContaCorrente && isCorrente == true) { //Verifica de a conta encontrada na ArrayList é um objeto ContaCorrente E verifica se é isCorrente é true
                    return conta;
                } else if (conta instanceof ContaPoupanca && isCorrente == false) {  //Verifica de a conta encontrada na ArrayList é um objeto ContaPoupanca E verifica se é isCorrente é false
                    return conta;
                }
            }
        }

        return null;
    }
    //O metodo acima é usado na hora em que a classe Load cria um objeto Cliente. (Ver linha 107 e 109 da classe Load)

    //Construtor de Conta usado pela classe Load
    protected Conta(long numero, int senha, double saldo, String cpfDono) {
        this.numero = numero;
        numerosExistentes.add(numero); //Adiciona o numero a lista de numeros existentes
        this.senha = senha;
        this.saldo = saldo;
        this.cpfDono = cpfDono;
        //OBS: esse construtor é chamado pelo construtor da subclasse
        //O construtor da subclasse que fica com a tarefa de adicionar o objeto Conta à ArrayList<Conta>, já que ele executa depois deste
    }

    //Construtor Conta usado pelo main
    protected Conta(int senha, String cpfDono) {
        while (true) {
            long numero = r.nextInt(99999); //Gera um numero aleatório para a Conta
            if (!numerosExistentes.contains(numero)) { //Verifica se esse numero já não está cadastrado
                numerosExistentes.add(numero); // se não estiver cadastrado, cadastra ele (adiciona na arraylist)
                this.numero = numero;
                break;
            }
        }

        this.cpfDono = cpfDono;
        this.senha = senha;
    }

    //metodo que loga na conta
    public boolean logIn(int senha) throws IllegalArgumentException {
        //pega a senha passada como parametro (o usuario digitou) e compara com a senha da conta
        if (this.senha == senha) {
            logado = true; //Se a senha tiver certa, muda logado para true
            return true; //O metodo tbm retorna true se tiver logado
        }
        throw new IllegalArgumentException("Senha incorreta"); //Caso a senha não for igual
    }

    //metodo que desloga
    public void logOff() {
        logado = false;
    }

    public long getNumero() {
        return numero;
    }

    //metodo que deposita
    public void depositar(double valor) throws IllegalArgumentException, IllegalStateException {
        if (logado) { //Só deposita se estiver logado
            if (valor <= 0) {//Só deposita valores maiores que 0
                throw new IllegalArgumentException("Valor inválido");
            } else {
                this.setSaldo(valor + this.getSaldo()); //soma o valor do deposito ao saldo, e salva ele
                this.update(); //metodo que eu criei la em baixo, que dá o update na conta no BD. Observe que eu só chama dou update depois que eu terminei a operação dentro do programa
            }
        } else {
            throw new IllegalStateException("Conta não logada"); //se logado for false
        }
    }

    public void sacar(double valor) throws IllegalArgumentException, IllegalStateException {
        if (logado) {
            if (valor <= 0) {
                throw new IllegalArgumentException("Valor inválido");
            } else if (valor > this.getSaldo()) { //Não pode sacar um valor maior que o saldo
                throw new IllegalArgumentException("Saldo insuficiente");
            } else {
                this.setSaldo(this.getSaldo() - valor);
                this.update();
            }
        } else {
            throw new IllegalStateException("Conta não logada");
        }
    }

    public double getSaldo() {
        return saldo;
    }

    public double verSaldo() throws IllegalStateException {
        if (logado) {
            return saldo;
        } else {
            throw new IllegalStateException("Conta não logada");
        }
    }

    protected void setSaldo(double saldo) throws IllegalArgumentException {
        if (saldo < 0) { //Não deixa configurar o saldo com valor negativo
            throw new IllegalArgumentException("Saldo negativo");
        } else {
            this.saldo = saldo;
        }
    }

    
    public String getCpfDono() {
        return cpfDono;
    }

    public int getSenha() {
        return senha;
    }

    //metodo que dá update no BD
    protected void update() {
        Write.updateConta(this); //chamo o metodo da classe Write que dá update na conta, passo ESSA (this) conta como parâmetro
    }

    public boolean isLogado() {
        return logado;
    }

}
