public class Trabalho {
    double valor;
    double valorFinal;
    boolean qualTrabalho;

    public Trabalho(double valor, boolean qualTrabalho){
        this.valor = valor;
        this.qualTrabalho = qualTrabalho;
    }

    public void funcValorFinal(double iss, double icms, double ipi)
    {
        this.valorFinal = this.valor + iss + icms + ipi;
    }

}
