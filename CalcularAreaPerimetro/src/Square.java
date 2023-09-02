public class Square {
    double lado;
    double area;
    double perimetro;
    void calcularArea(){
        area = lado *lado;
        System.out.println("\nArea do quadrado: " + area);
    }
    void calcularPerimetro(){
        perimetro = lado * 4 ;
        System.out.println("\nPerimetro do quadrado:" + perimetro);
    }

    Square(double lado){
        this.lado = lado;
    }
}
