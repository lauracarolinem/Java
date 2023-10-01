public class ContaCorrente extends Conta implements Tributavel{
    double saldo;
    public double calculaTributos(){
        double valorTributo;
        valorTributo = this.saldo*1/100;
        return valorTributo;
    };
    public ContaCorrente(double valor){
        this.saldo = saldo;
    }
}
