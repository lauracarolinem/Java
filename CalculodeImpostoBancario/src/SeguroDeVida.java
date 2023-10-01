public class SeguroDeVida implements Tributavel{
    double valorSeguro;
    public double calculaTributos(){
        double valorTributo = 42;
        return valorTributo;
    };
    public SeguroDeVida(double valorSeguro){
        this.valorSeguro = valorSeguro;
    }
}
