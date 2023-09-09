// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Trabalho produtoTeste = new Trabalho(200.80, true);
        ISS issProduto = new ISS(produtoTeste);
        ICMS icmsProduto = new ICMS(produtoTeste);
        IPI ipiProduto = new IPI(produtoTeste);
        produtoTeste.funcValorFinal(issProduto.funcIssFinal(), icmsProduto.funcIcmsFinal(), ipiProduto.funcIpiFinal());

        Trabalho servicoTeste = new Trabalho(332.99F, false);
        ISS issServ1 = new ISS(servicoTeste);
        ICMS icmsServ1 = new ICMS(servicoTeste);
        IPI ipiServ1 = new IPI(servicoTeste);
        servicoTeste.funcValorFinal(issServ1.funcIssFinal(), icmsServ1.funcIcmsFinal(), ipiServ1.funcIpiFinal());

        System.out.println("Produto :");
        System.out.println("Valor do produto sem taxa: " + produtoTeste.valor);
        System.out.println("ISS: " + issProduto.funcIssFinal());
        System.out.println("ICMS: " + icmsProduto.funcIcmsFinal());
        System.out.println("IPI: " + ipiProduto.funcIpiFinal());
        System.out.println("Valor incluindo taxas: " + produtoTeste.valorFinal + "\n");

        System.out.println("Servico :");
        System.out.println("Valor do produto sem taxa: " + servicoTeste.valor);
        System.out.println("ISS: " + issServ1.funcIssFinal());
        System.out.println("ICMS: " + icmsServ1.funcIcmsFinal());
        System.out.println("IPI: " + ipiServ1.funcIpiFinal());
        System.out.println("Valor incluindo taxas: " + servicoTeste.valorFinal);
    }
}