import java.util.*;

/**
 * Klasse, die die Automatenlogik kapselt: Produkte verwalten, Kaufvorgang abwickeln,
 * Eingaben validieren und Quittung erstellen.
 */
public class VendingMachine {
    // Produkte werden in einer Map gehalten: Schlüssel ist eine Nummer (1..n)
    private Map<Integer, Product> products;
    // Zulässige Geldwerte in Euro
    private final Set<Integer> allowedCoins = Set.of(1, 2, 5, 10);

    /**
     * Konstruktor erstellt die Map und befüllt erste Beispielprodukte.
     */
    public VendingMachine() {
        products = new LinkedHashMap<>(); // LinkedHashMap für stabile Anzeige-Reihenfolge
        // Beispielprodukte (können im Projekt ergänzt/angepasst werden)
        products.put(1, new Product("Wasser", 1, 5));
        products.put(2, new Product("Cola", 2, 5));
        products.put(3, new Product("Saft", 3, 3));
        products.put(4, new Product("Limonade", 2, 2));
    }

    /**
     * Gibt eine formatierte Anzeige aller Produkte auf der Konsole aus.
     */
    public void displayProducts() {
        System.out.println("\nVerfügbare Getränke:");
        for (Map.Entry<Integer, Product> entry : products.entrySet()) {
            System.out.println(entry.getKey() + ") " + entry.getValue().toString());
        }
    }

    /**
     * Validiert die Auswahl-Nummer und gibt das Produkt zurück oder wirft Exception.
     */
    public Product selectProduct(int selection) throws InvalidSelectionException {
        if (!products.containsKey(selection)) {
            throw new InvalidSelectionException("Auswahl '" + selection + "' ist ungültig.");
        }
        return products.get(selection);
    }

    /**
     * Validiert, ob eingezahlter Betrag gültig ist (erlaubte Münzen) und gibt Summe zurück.
     */
    public int validateMoney(int money) throws InvalidMoneyException {
        if (!allowedCoins.contains(money)) {
            throw new InvalidMoneyException("Ungültiger Geldbetrag: " + money + " €. Zulässig: 1,2,5,10 €.");
        }
        return money;
    }

    /**
     * Führt den Kauf mit einfacher Logik aus: prüft Bestand, Preis, reduziert Bestand,
     * berechnet Rückgeld und gibt eine Quittung als String zurück.
     */
    public String purchase(Product product, int paidAmount) throws OutOfStockException, InvalidMoneyException {
        // Validierung des Geldes
        validateMoney(paidAmount);

        // Ist das Produkt verfügbar?
        if (!product.isAvailable()) {
            throw new OutOfStockException("Das Produkt '" + product.getName() + "' ist nicht vorrätig.");
        }

        int price = product.getPrice();

        // Ist der bezahlte Betrag ausreichend?
        if (paidAmount < price) {
            // Hier keine Exception, sondern einfache Rückmeldung als Text
            return "Zu wenig Geld gezahlt. Preis: " + price + " €, gezahlt: " + paidAmount + " €.";
        }

        // Alles in Ordnung: Bestand reduzieren
        product.reduceStock();

        int change = paidAmount - price;

        // Erstelle Quittung
        StringBuilder receipt = new StringBuilder();
        receipt.append("--- Quittung ---\n");
        receipt.append("Produkt: ").append(product.getName()).append("\n");
        receipt.append("Preis: ").append(price).append(" €\n");
        receipt.append("Gezahlt: ").append(paidAmount).append(" €\n");
        receipt.append("Rückgeld: ").append(change).append(" €\n");
        receipt.append("----------------\n");

        return receipt.toString();
    }

    /**
     * Optional: Methode um Produkte hinzuzufügen (Admin/Erweiterung).
     */
    public void addProduct(int id, Product p) {
        products.put(id, p);
    }

    /**
     * Getter für Produkte (z.B. zum Anzeigen/Tests).
     */
    public Map<Integer, Product> getProducts() {
        return Collections.unmodifiableMap(products);
    }
}
