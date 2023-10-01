// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        ContaPoupanca pou = new ContaPoupanca();
        pou.saldo = 3000;

        ContaCorrente corr = new ContaCorrente(5000);
        double tributoCorrente = corr.calculaTributos();
        System.out.println("Taxa da conta corrente: " + tributoCorrente);

        SeguroDeVida segu = new SeguroDeVida(2000);
        double tributoSeguro = segu.calculaTributos();
        System.out.println("Taxa do Seguro de vida: "+ tributoSeguro);
    }
}