// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Circle circulo = new Circle(5);
        circulo.calcularArea();
        circulo.calcularPerimetro();

        Square quadrado = new Square(6);
        quadrado.calcularArea();
        quadrado.calcularPerimetro();

        Rectangle retangulo = new Rectangle(2,3);
        retangulo.calcularArea();
        retangulo.calcularPerimetro();

        Triangle triangulo = new Triangle(3,4,5);
        triangulo.calcularArea();
        triangulo.calcularPerimetro();
    }
}