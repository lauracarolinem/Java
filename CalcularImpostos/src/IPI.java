public class IPI {
    double ipiFinal;

    public IPI(Trabalho trabalho){
        if(!trabalho.qualTrabalho){
            return;
        }
        this.ipiFinal = trabalho.valor * 0.25;
    }

    public double funcIpiFinal(){
        return this.ipiFinal;
    }
}
