/**
 * Exception: Produkt ist nicht auf Lager.
 */
class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}