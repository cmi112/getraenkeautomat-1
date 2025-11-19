import java.util.Scanner;

/**
 * Steuerungsklasse mit main-Methode: verwaltet die Benutzerinteraktion (Konsolen-I/O)
 * und nutzt die VendingMachine-Klasse, um die Business-Logik auszuführen.
 */
public class VendingMachineApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VendingMachine vm = new VendingMachine();

        System.out.println("Willkommen zum Getränkeautomaten - Simulation");

        boolean weiter = true;
        while (weiter) {
            // 1. Zeige alle Produkte
            vm.displayProducts();

            try {
                // 2. Nutzer wählt ein Getränk
                System.out.print("\nBitte Auswahlnummer eingeben (z.B. 1) oder 0 zum Abbrechen: ");
                int auswahl = Integer.parseInt(scanner.nextLine().trim());

                if (auswahl == 0) {
                    System.out.println("Vorgang abgebrochen.");
                } else {
                    Product produkt = vm.selectProduct(auswahl); // kann InvalidSelectionException werfen

                    // 3. Nutzer zahlt einen Betrag ein
                    System.out.print("Betrag eingeben (gültig: 1,2,5,10 €): ");
                    int bezahlt = Integer.parseInt(scanner.nextLine().trim());

                    // 4. Kaufprüfung & Ausführung
                    String result = vm.purchase(produkt, bezahlt);

                    // Wenn result mit "Zu wenig Geld" beginnt, ist es ein Fehlertext
                    if (result.startsWith("Zu wenig Geld")) {
                        System.out.println(result);
                        // Biete dem Nutzer an erneut zu wählen
                        System.out.print("Möchten Sie erneut wählen? (j/n): ");
                        String r = scanner.nextLine().trim().toLowerCase();
                        if (!r.equals("j") && !r.equals("y")) {
                            System.out.println("Vorgang abgebrochen. Rückgabe des Betrags: " + bezahlt + " €");
                        }
                    } else {
                        // Gültiger Kauf: Quittung ausgeben
                        System.out.println(result);
                    }
                }
            } catch (InvalidSelectionException e) {
                System.out.println("Fehler: " + e.getMessage());
            } catch (InvalidMoneyException e) {
                System.out.println("Fehler: " + e.getMessage());
            } catch (OutOfStockException e) {
                System.out.println("Fehler: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe: Bitte eine Zahl eingeben.");
            } catch (Exception e) {
                // Allgemeiner Fallback
                System.out.println("Unerwarteter Fehler: " + e.getMessage());
            }

            // Nach jedem Durchlauf fragen, ob weiter bedient werden soll
            System.out.print("\nWeiteren Kunden bedienen? (j/n): ");
            String antwort = scanner.nextLine().trim().toLowerCase();
            if (!antwort.equals("j") && !antwort.equals("y")) {
                weiter = false;
            }
        }

        System.out.println("Programm beendet. Auf Wiedersehen!");
        scanner.close();
    }
}
