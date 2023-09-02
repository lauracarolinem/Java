public class Rectangle {
    double altura;
    double largura;
    double area;
    double perimetro;

    void calcularArea(){
        area = this.altura * this.largura;
        System.out.println("\nArea do retangulo: " + area);
    }

    void calcularPerimetro(){
        perimetro = (this.altura *2) + (this.largura * 2);
        System.out.println("\nPerimetro do retangulo: " + perimetro);
    }

    Rectangle(double altura, double largura){
        this.altura = altura;
        this.largura = largura;
    }
}
