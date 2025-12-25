/**
 * Represents a product in the Customer Order System (COS).
 * Abstract Class - cant be instantiated directly
 * Super class for quantity/weighted product
 * Stores name, description, price, and sale price.
 *
 * @author Ty Anderson R11885063
 * @version 1.0
 */
public abstract class Product {
    private String productName;
    private String productDescription;
    private double productRegularPrice;
    private double productSalePrice;

    // ------------------- Constructors -------------------
    /**
     * Create a product object
     *
     * @param productName         the product name
     * @param productDescription  the product description
     * @param productRegularPrice the product regular price
     * @param productSalePrice    the product sale price
     */
    public Product(String productName, String productDescription, double productRegularPrice, double productSalePrice) {
        if (productRegularPrice < 0) {
            throw new IllegalArgumentException("Product regular price cannot be negative");
        }
        if (productSalePrice < 0) {
            throw new IllegalArgumentException("Product sale price cannot be negative");
        }
        this.productName = productName;
        this.productDescription = productDescription;
        this.productRegularPrice = productRegularPrice;
        this.productSalePrice = productSalePrice;
    }

    /**
     * Instantiates a new Product.
     *
     * @param product the product
     */
    public Product(Product product) {
        this.productName = product.getProductName();
        this.productDescription = product.getProductDescription();
        this.productRegularPrice = product.getProductRegularPrice();
        this.productSalePrice = product.getProductSalePrice();
    }

    /**
     * Instantiates a new Product.
     */
    public Product() {
        this.productName = "";
        this.productDescription = "";
        this.productRegularPrice = 0;
        this.productSalePrice = 0;
    }



// ------------------- Getters -------------------
    /**
     * Gets product name.
     *
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Gets product description.
     *
     * @return the product description
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * Gets product regular price.
     *
     * @return the product regular price
     */
    public double getProductRegularPrice() {
        return productRegularPrice;
    }

    /**
     * Gets product sale price.
     *
     * @return the product sale price
     */
    public double getProductSalePrice() {
        return productSalePrice;
    }



// ------------------- Setters -------------------
    /**
     * Sets product name.
     *
     * @param productName the product name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Sets product description.
     *
     * @param productDescription the product description
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    /**
     * Sets product regular price.
     *
     * @param productRegularPrice the product regular price
     */
    public void setProductRegularPrice(double productRegularPrice) {
        if (productRegularPrice < 0) {
            throw new IllegalArgumentException("Product regular price cannot be negative");
        }
        this.productRegularPrice = productRegularPrice;
    }

    /**
     * Sets product sale price.
     *
     * @param productSalePrice the product sale price
     */
    public void setProductSalePrice(double productSalePrice) {
        if (productSalePrice < 0) {
            throw new IllegalArgumentException("Product sale price cannot be negative");
        }
        this.productSalePrice = productSalePrice;
    }



// ------------------- Utilities -------------------
    /**
     * Equals product boolean.
     *
     * @param product the product
     * @return the boolean
     */
    public boolean equalsProduct(Product product) {
        return (this.productName.equals(product.getProductName()) &&
                this.productDescription.equals(product.getProductDescription()) &&
                this.productRegularPrice ==(product.getProductRegularPrice()) &&
                this.productSalePrice == (product.getProductSalePrice()));
    }



// ------------------- Abstract methods -------------------
    /**
     * Calculate total cost double.
     *
     * @return the double
     */
    public abstract double calculateTotalCost();



// ------------------- toString() -------------------
    @Override
    public String toString() {
        return "Product{" +
                "Name='" + productName + '\'' +
                ", Description='" + productDescription + '\'' +
                ", Regular Price='" + productRegularPrice +
                ", Sale Price='" + productSalePrice +
                '}';
    }

    /**
     * To file string.
     *
     * @return the string
     */
    public String toFileString() {
        return productName + "|" + productDescription + "|" + productRegularPrice + "|" + productSalePrice;
    }

    /**
     * To ui string string.
     *
     * @return the string
     */
    public String toUIString() {
        return String.format(
                "%-20s | %-35s | Regular: $%-8.2f | Sale: $%-8.2f",
                productName, productDescription, productRegularPrice, productSalePrice
        );
    }
}