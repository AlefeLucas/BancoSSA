// numero 6

package br.com.senai.modeloContas;

import br.com.senai.DAO.Write;

public class ContaCorrente extends Conta {

    //Construtor usado pela classe Load
    public ContaCorrente(long numero, int senha, double saldo, String cpfDono) {
        super(numero, senha, saldo, cpfDono); //chama o contrutor da super classe (Conta)
        contas.add(this); //Após o contrutor da super classe ter sido executado, adiciona a ContaCorrente na ArrayList<Conta>
    }

    //Construtor usado pelo main
    public ContaCorrente(int senha, String cpfDono) {
        super(senha, cpfDono);
        contas.add(this);
        Write.addConta(this); //Chama o método que dá o insert no BD, passando essa conta como parâmetro
    }

    @Override
    public void depositar(double valor) throws IllegalArgumentException, IllegalStateException {
        if (isLogado()) /*Só deposita se estiver logado. Como a variável logado é private, eu uso o método public isLogado, que retorna logado*/ {
            super.depositar(valor); //Chama o método depositar da super classe
            this.desconta();//chama o método que desconta 0,25 centavos
            this.update();//dá o update no BD novamente depois de descontado
        } else {
            throw new IllegalStateException("Conta não logada");
        }

    }

    @Override
    public void sacar(double valor) throws IllegalArgumentException, IllegalStateException {
        if (isLogado()) {
            super.sacar(valor);
            this.desconta();
            this.update();
        } else {
            throw new IllegalStateException("Conta não logada");
        }
    }

    private void desconta() {
        if (this.getSaldo() < 0.25 && this.getSaldo() > 0) { //Se o saldo for ENTRE 0 e 0.25, não poderá ser descontado 25 porque o saldo ficaria negativo, então desconta o que tiver
            this.setSaldo(0);//muda o saldo pra 0
        } else if (this.getSaldo() > 0.25) { //porém se o saldo for maior que 0.25, desconta 25 centavos
            this.setSaldo(this.getSaldo() - 0.25);
        }
    }
}
