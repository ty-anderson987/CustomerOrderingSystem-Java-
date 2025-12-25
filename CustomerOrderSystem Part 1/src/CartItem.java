/**
 * Represents a cart item in the Customer Order System (COS).
 * Stores product and quantity.
 *
 * @author Ty Anderson R11885063
 * @version 1.0
 */
public class CartItem {
    private Product product;
    private int quantity;
    private double weight;

// ------------------- Constructors -------------------
    /**
     * Creates a cartItem object for quantityProduct
     *
     * @param product  the product
     * @param quantity the quantity
     */
    public CartItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity is negative");
        }
        this.product = product;
        this.quantity = quantity;
        this.weight = 0;
    }

    /**
     * Creates a cartItem object for weightedProduct
     *
     * @param product the product
     * @param weight  the weight
     */
    public CartItem(Product product, double weight) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        this.product = product;
        this.weight = weight;
        this.quantity = 0;
    }



// ------------------- Getters -------------------
    /**
     * Gets product.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets weight.
     *
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }



// ------------------- Setters -------------------
    /**
     * Sets product.
     *
     * @param product the product
     */
    public void setProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        this.product = product;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    /**
     * Sets weight.
     *
     * @param weight the weight
     */
    public void setWeight(double weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        this.weight = weight;
    }



// ------------------- toString() -------------------
    @Override
    public String toString() {
        return toFileString();
    }

    /**
     * To file string string.
     *
     * @return the string
     */
    public String toFileString() {
        // Checks the instanceType of the product to be put in the cart Item
        if (product instanceof WeightedProducts weightedProduct) {
            return weightedProduct.toFileString() + "|" + weight;
        } else if (product instanceof QuantityProducts quantityProducts) {
            return quantityProducts.toFileString() + "|" + quantity;
        } else {
            return product.toFileString() + "|" + quantity;
        }
    }

    /**
     * To ui string string.
     *
     * @return the string
     */
    public String toUIString() {
        if (product instanceof WeightedProducts weightedProduct) {
            return weightedProduct.toUIString() + "| Cart Item Weight " + weight;
        } else if (product instanceof QuantityProducts quantityProducts) {
            return quantityProducts.toUIString() + "| Cart Item Quantity " + quantity;
        } else {
            return product.toUIString() + "| Cart Item Quantity " + quantity;
        }
    }
}