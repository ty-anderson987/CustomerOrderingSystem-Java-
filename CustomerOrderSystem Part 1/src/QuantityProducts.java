/**
 * Represents a quantityProduct in the Customer Order System (COS).
 * Stores name, description, price, salePrice, and quantity
 *
 * @author Ty Anderson R11885063
 * @version 1.0
 */
public class QuantityProducts extends Product {
    private final int quantityPerPackage;

// ------------------- Constructors -------------------
    /**
     * Constructs a quantity product using a Product super constructor
     *
     * @param productName         the product name
     * @param productDescription  the product description
     * @param productRegularPrice the product regular price
     * @param productSalePrice    the product sale price
     * @param quantityPerPackage  the quantity per package
     */
    public QuantityProducts(String productName, String productDescription, double productRegularPrice, double productSalePrice, int quantityPerPackage) {
        super(productName, productDescription, productRegularPrice, productSalePrice);
        if (quantityPerPackage < 0) {
            throw new IllegalArgumentException("Quantity per package must be greater than zero.");
        }
        this.quantityPerPackage = quantityPerPackage;
    }

    /**
     * Instantiates a new Quantity products.
     *
     * @param product  the product
     * @param quantity the quantity
     */
    public QuantityProducts(Product product, int quantity) {
        super(product);
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.quantityPerPackage = quantity;
    }



// ------------------- Getters -------------------
    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantityPerPackage;
    }



// ------------------- Abstract methods -------------------
    @Override
    public double calculateTotalCost() {
        return getProductRegularPrice() *  quantityPerPackage;
    }



// ------------------- toString() -------------------
    @Override
    public String toString() {
        return "QuantityProduct{" +
                "Name='" + getProductName() + '\'' +
                ", Description='" + getProductDescription() + '\'' +
                ", Regular Price=" + getProductRegularPrice() +
                ", Sale Price=" + getProductSalePrice() +
                ", Quantity per package=" + quantityPerPackage +
                '}';
    }
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + quantityPerPackage;
    }
    @Override
    public String toUIString() {
        return String.format(
                "%s | Quantity per item: %d",
                super.toUIString(),  // calls Product.toUIString() for name, description, prices
                quantityPerPackage
        );
    }
}