public class ICMS {
    double icmsFinal;

    public ICMS(Trabalho trabalho){
        this.icmsFinal = trabalho.valor * 0.17;
    }

    public double funcIcmsFinal(){
        return this.icmsFinal;
    }
}
