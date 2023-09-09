public class ISS {
    double issFinal;

    public ISS(Trabalho trabalho){
        if(trabalho.qualTrabalho){
            return;
        }
        this.issFinal = trabalho.valor * 0.046;
    }

    public double funcIssFinal(){
        return this.issFinal;
    }
}
