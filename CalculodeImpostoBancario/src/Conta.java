public abstract class Conta {
    double saldo;

    public double sacar(double valorParaSacar){
        if(this.saldo<valorParaSacar)
        {
            return 0;
        }
        double valorTotal;
        valorTotal = this.saldo - valorParaSacar;
        return valorTotal;
    }

    public double depositar(double valorParaDepositar){
        double valorTotal;
        valorTotal = this.saldo + valorParaDepositar;
        return valorTotal;
    }
}
