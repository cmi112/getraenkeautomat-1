public class Product {
    // Name des Produkts (z.B. "Cola")
    private String name;
    // Preis in ganzen Euro (1..5)
    private int price;
    // aktueller Bestand (Anzahl verfügbarer Einheiten)
    private int stock;


    // Konstruktor
    public Product(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }


    // Getter-Methoden
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getStock() { return stock; }


    // Prüfen ob verfügbar
    public boolean isAvailable() {
        return stock > 0;
    }


    // Bei Verkauf Bestandsreduktion um 1
    public void reduceStock() throws OutOfStockException {
        if (stock <= 0) {
            throw new OutOfStockException("Produkt '" + name + "' ist nicht vorrätig.");
        }
        stock -= 1;
    }


    // Methoden zum Auffüllen (optional)
    public void refill(int amount) {
        if (amount > 0) {
            stock += amount;
        }
    }


    @Override
    public String toString() {
        return name + " - Preis: " + price + " € - Bestand: " + stock;
    }
}



/**
 * Exception: Ausgewähltes Produkt existiert nicht.
 */
class InvalidSelectionException extends Exception {
    public InvalidSelectionException(String message) {
        super(message);
    }
}