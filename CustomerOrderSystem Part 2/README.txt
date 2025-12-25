Customer Order System (COS) with ChaseBank Integration
Author: Ty Anderson
Language: Java
Date: October 2025

--------------------------------------------------------
                1. Project Overview
--------------------------------------------------------
    The Customer Order System (COS) allows customers to manage their accounts, link Chase bank accounts, manage credit cards, and place orders through a console-based Java application.
    Key features include:
        - Customer account creation and login
        - Linking and unlinking Chase bank accounts
        - Managing credit cards, including adding authorized users
        - Placing and viewing orders
        - Input validation and error handling for smooth operation

--------------------------------------------------------
            2. System Requirements
--------------------------------------------------------
    - Java SDK: Amazon Corretto 21.0.8 (aarch64)
    - Command-line terminal or IDE: IntelliJ IDEA, Eclipse, VS Code
    - Operating System: Windows, macOS, Linux

--------------------------------------------------------
            3. How to Run the Program
--------------------------------------------------------
    Running in IntelliJ IDEA:
        1. Open the project folder in IntelliJ.
        2. Ensure all .java files are in the same package or default package.
        3. Open Main.java.
        4. Compile all Java files using:
               javac *.java
        5. Run the program:
               Right-click Main.java → Run 'Main.main()'
        6. Follow the console prompts to log in, create accounts, or place orders.

--------------------------------------------------------
            4. Demonstration of Use Cases
--------------------------------------------------------
Below are all implemented use cases and example console interactions:

    --------------------------------------------------------
    Use Case: Create Account
    --------------------------------------------------------
    Precondition: None

    Main sequence:
    1. The customer enters a desired Customer ID.
    2. The system checks if the Customer ID already exists.
    3. If the ID is available, the system prompts the customer to create a password.
    4. The system validates the password (minimum 6 characters, at least one digit, one special character (@, #, $, %, &, *), and one uppercase letter).
    5. The customer enters their name, address, and credit card number.
    6. The system confirms that none of the fields are null and displays an account creation confirmation.
    7. The customer selects a security question from a list and enters its answer.
    8. The system stores the customer’s security question and answer.

    Alternative sequence:
    - Step 2: If the Customer ID exists, the system prompts for a different ID.
    - Step 4: If the password is invalid, the system prompts for a new password.
    - Step 6: If name, address, or credit card number is null, the system prompts the customer to re-enter the information.

    --------------------------------------------------------
    Use Case: Log On
    --------------------------------------------------------
    Precondition: The customer has created an account with a Customer ID, password, security question, and answer.

    Main sequence:
    1. The customer enters their Customer ID and password.
    2. The system validates the Customer ID and password.
    3. If valid, the system displays the customer’s security question.
    4. The customer enters the answer to the security question.
    5. If correct, the system displays a welcome message.

    Alternative sequence:
    - Step 2: If the Customer ID does not exist, the system displays an error and terminates.
    - Step 2: If the password is incorrect, the system prompts the customer to re-enter ID and password (max 3 attempts).
    - Step 4: If the answer is incorrect, the system displays an error and terminates.

    --------------------------------------------------------
    Use Case: Log Out
    --------------------------------------------------------
    Precondition: The customer is logged in.

    Main sequence:
    1. The customer selects “Log Out.”
    2. The system logs the customer out.

    Alternative sequence: None

    --------------------------------------------------------
    Use Case: Select Items
    --------------------------------------------------------
    Precondition: None

    Main sequence:
    1. The customer requests the product catalog.
    2. The system displays product names, descriptions, regular prices, and sale prices.
    3. The customer selects one or more products and quantities.
    4. The system adds the selected products to a cart.
    5. The system displays the cart contents, including quantities, taxes, and total price.

    Alternative sequence:
    - Step 3: If the customer selects no product, they can exit the catalog.

    --------------------------------------------------------
    Use Case: Make Order
    --------------------------------------------------------
    Precondition: The customer has selected products and is logged in.

    Main sequence:
    1. The customer places an order for selected products.
    2. The system displays delivery options: (a) mail with a fee (e.g., $3 per order) or (b) in-store pickup for free.
    3. The customer selects a delivery method.
    4. The system calculates the total including mailing fees if applicable.
    5. The system retrieves the customer’s credit card from their account.
    6. The system requests a bank to charge the total amount.
    7. If approved, the bank returns a 4-digit authorization number.
    8. The system stores the order, including date, customer ID, products, quantities, total, and authorization number.
    9. The system displays order confirmation.

    Alternative sequence:
    - Step 3: Customer does not select a delivery method and exits.
    - Step 6-7: If the bank denies the charge, the system prompts the customer to enter a new credit card or exit. If valid, the system updates the account and processes the payment.

    --------------------------------------------------------
    Use Case: View Order
    --------------------------------------------------------
    Precondition: Customer is logged in.

    Main sequence:
    1. The customer requests all order information.
    2. The system displays each order with date, products, quantities, and total.

    Alternative sequence: None

--------------------------------------------------------
                5. Notes and Limitations
--------------------------------------------------------
    - A customer can have 0 or 1 Chase bank account, but multiple credit cards.
    - Due to the size of the entire program, I have limited testing time to check every edge case.
    - Also in part 1. Customer and CustomerBank is 1-1 relationship, but in IRL it is a 1-many, so I completely redid the banking system.
    - Every method and UI and UI Controllers that uses the Bank had to be redone.
    - I think the new Bank called ChaseBank is a more accurate representation of how the open-ended this test case was.
    - Also like in real life a credit card can be used on multiple accounts like how dad's credit card can be used for each of his children Amazon account. Think of it like an authorized user.
    - If you follow the use cases, it works.
    - I got too busy to test the Admin every method so be warned.
    - Also, you must completely quit the program if you want the data saved to text files.

--------------------------------------------------------
                    End of README
--------------------------------------------------------