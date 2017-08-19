// numero 7

package br.com.senai.modeloContas;

import br.com.senai.DAO.Write;

public class ContaPoupanca extends Conta {

    //Construtor usado pela classe Load
    public ContaPoupanca(long numero, int senha, double saldo, String cpfDono) {
        super(numero, senha, saldo, cpfDono);
        contas.add(this);

    }

    //Construtor usado pelo main
    public ContaPoupanca(int senha, double depositoInicial, String cpfDono) throws IllegalArgumentException {
        super(senha, cpfDono);
        if (depositoInicial < 100) { //Só constrói se o depósito inicial for maior que 100
            throw new IllegalArgumentException("Deposito inicial insuficiente");
        } else {
            this.setSaldo(depositoInicial);
            contas.add(this);
            Write.addConta(this);
        }
    }

    @Override
    public void depositar(double valor) throws IllegalArgumentException {
        if (isLogado()) {
            super.depositar(valor);//Chama o método depositar da super classe
            this.setSaldo(this.getSaldo() + this.getSaldo() * 0.05); //Acrescenta os 5%
            this.update();//dá o update no BD novamente depois de descontado
        }
    }

}
