public class Triangle {
    double lado1, lado2, lado3;
    double perimetro;
    void calcularArea(){
        double semiperimetro = (this.lado1 + this.lado2 + this.lado3)/2;
        double area = Math.sqrt(semiperimetro*(semiperimetro - lado1) * (semiperimetro - lado2) * (semiperimetro - lado3));
        System.out.println("\nArea do triangulo: " + area);
    }//formula de Heron
    void calcularPerimetro(){
        double perimetro = this.lado1 + this.lado2 + this.lado3;
        System.out.println("\nPerimetro do triangulo: " + perimetro);
    }

    Triangle(double lado1, double lado2, double lado3){
        this.lado1 = lado1;
        this.lado2 = lado2;
        this.lado3 = lado3;
    }
}
