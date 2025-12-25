/**
 * Represents a weightedProduct in the Customer Order System (COS).
 * Stores name, description, price, salePrice, and weight
 *
 * @author Ty Anderson R11885063
 * @version 1.0
 */
public class WeightedProducts extends Product {
    private final double weightPerItem;

// ------------------- Constructors -------------------
    /**
     * Constructs a weighted product using a Product super constructor
     *
     * @param productName         the product name
     * @param productDescription  the product description
     * @param productRegularPrice the product regular price
     * @param productSalePrice    the product sale price
     * @param weightPerItem       the weight per item
     */
    public WeightedProducts(String productName, String productDescription, double productRegularPrice, double productSalePrice, double weightPerItem) {
        super(productName, productDescription, productRegularPrice, productSalePrice);
        if (weightPerItem < 0) {
            throw new IllegalArgumentException("Weights cannot be negative");
        }
        this.weightPerItem = weightPerItem;
    }

    /**
     * Instantiates a new Weighted products.
     *
     * @param product the product
     * @param weight  the weight
     */
    public WeightedProducts(Product product, double weight) {
        super(product);
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        this.weightPerItem = weight;
    }



// ------------------- Getters -------------------
    /**
     * Gets weight per item.
     *
     * @return the weight per item
     */
    public double getWeightPerItem() {
        return weightPerItem;
    }



// ------------------- Abstract methods -------------------
    @Override
    public double calculateTotalCost() {
        return getProductRegularPrice() * weightPerItem;
    }



    // ------------------- toString() -------------------
    @Override
    public String toString() {
        return "WeightedProducts{" +
                "Name='" + getProductName() + '\'' +
                ", Description='" + getProductDescription() + '\'' +
                ", Regular Price=" + getProductRegularPrice() +
                ", Sale Price=" + getProductSalePrice() +
                ", Weight per item=" + weightPerItem +
                '}';
    }
    @Override
    public String toFileString() {
        return super.toFileString() + "|" + weightPerItem;
    }
    @Override
    public String toUIString() {
        return String.format(
                "%s | Weight per item: %.2f",
                super.toUIString(),  // calls Product.toUIString() for name, description, prices
                weightPerItem
        );
    }
}