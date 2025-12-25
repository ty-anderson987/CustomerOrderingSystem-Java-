import java.util.ArrayList;

/**
 * Represents a shopping cart containing selected items and taxes.
 * Each CartItem includes a Product and its quantity.
 *
 * @author Ty Anderson R11885063
 * @version 1.0
 */
public class Cart {
    private ArrayList<CartItem> items;
    private double taxes;

// ------------------- Constructors -------------------
    /**
     * Creates a new cart object with item arrayList to store new items in cart
     */
    public Cart() {
        this.items = new ArrayList<>();
        this.taxes = 0.0;
    }



// ------------------- Getters -------------------
    /**
     * Gets items.
     *
     * @return the items
     */
    public ArrayList<CartItem> getItems() {
        return items;
    }

    /**
     * Gets taxes.
     *
     * @return the taxes
     */
    public double getTaxes() {
        return taxes;
    }



// ------------------- Setters -------------------
    /**
     * Sets items.
     *
     * @param items the items
     */
    public void setItems(ArrayList<CartItem> items) {
        this.items = items;
    }

    /**
     * Sets taxes.
     *
     * @param taxes the taxes
     */
    public void setTaxes(double taxes) {
        if (taxes < 0.0) {
            throw new IllegalArgumentException("taxes should be >= 0");
        }
        this.taxes = taxes;
    }



// ------------------- Utilities -------------------
    /**
     * Add item to cart
     *
     * @param item the item
     */
    public void addItem(CartItem item) {
        this.items.add(item);
    }

    /**
     * Calculate total before taxes
     *
     * @return the double
     */
    public double calculateSubtotal() {
        double subtotal = 0.0;
        // Go through all the items in the cart
        for (CartItem item : items) {
            Product product = item.getProduct();
            // Check if the item is a weightedProduct or quantityProduct/Product
            if (product instanceof WeightedProducts) {
                subtotal += product.getProductRegularPrice() * item.getWeight();
            } else {
                subtotal += product.getProductRegularPrice() * item.getQuantity();
            }
        }
        return subtotal;
    }

    /**
     * Calculate total after taxes
     *
     * @param taxes the taxes
     * @return the double
     */
    public double calculateTotal(double taxes) {
        return (calculateSubtotal() + calculateSubtotal()*taxes);
    }



// ------------------- toString(). -------------------
    /**
     * Return a formatted toString for the text file.
     * Make sure that || separates the items in the cart.
     * Also make sure that || doesn't appear at the end of the cart
     *
     * @return the string
     */
    public String toFileString() {
        String fileString = "";
        for (int i = 0; i < items.size(); i++) {
            fileString += items.get(i).toFileString();
            if (i < items.size() - 1) {
                fileString += "||"; // only between items
            }
        }
        return fileString;
    }
}
