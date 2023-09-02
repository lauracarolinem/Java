public class Circle {
    double area;
    double raio;
    double perimetro;
    void calcularArea() {
        area = 3.14 * raio * raio;
        System.out.println("\nArea do circulo: " + area);
    }

    void calcularPerimetro(){
        perimetro = 2 * 3.14 * raio;
        System.out.println("\nPerimetro do circulo: " + perimetro);
    }
    Circle(double raio){
        this.raio = raio;
    }
}
