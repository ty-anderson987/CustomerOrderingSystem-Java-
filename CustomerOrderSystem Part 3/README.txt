Customer Order System (COS) with ChaseBank Integration
Author: Ty Anderson
Language: Java
Date: October 2025

--------------------------------------------------------
                1. Project Overview
--------------------------------------------------------
    The Customer Order System (COS) allows customers to manage their accounts, link Chase bank accounts, manage credit cards, and place orders through a GUI-based Java application.
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
    Running the GUI Application:
        1. Open the project in IntelliJ.
        2. Ensure JavaFX is properly configured in Project Structure → Libraries.
        3. Open the GUI entry point class (e.g., CustomerOrderSystemGUI.java).
        4. Run the file:
             Right-click → Run
        5. The GUI window will open. Use the buttons on-screen to create accounts,
           log in, select items, and place orders.

--------------------------------------------------------
            4. Demonstration of Use Cases
--------------------------------------------------------
Below are all implemented use cases and example console interactions:

    --------------------------------------------------------
    Use Case: Create Account (GUI)
    --------------------------------------------------------
    Precondition: Customer has no existing account.

    Main Sequence:
        Customer clicks "Create Account" on the login screen.
        A form appears requesting:
            Customer ID
            Password
            Name
            Address
            Credit card number
        The system checks:
            Whether the Customer ID is already taken
            Whether the password meets requirements
            That no fields are empty
        Customer selects a security question from a dropdown and enters an answer.
        System displays: "Account Created Successfully."

    Alternative Paths:
        If Customer ID exists, the GUI shows: "ID already taken."
        If password is invalid, the password field is highlighted red.
        If a required field is empty, the GUI displays: "Please fill out all fields."

    --------------------------------------------------------
    Use Case: Log On (GUI)
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
    Use Case: Log Out (GUI)
    --------------------------------------------------------
    Precondition: The customer is logged in.

    Main sequence:
    1. The customer clicks “Log Out.”
    2. The system logs the customer out and returns to the login screen.

    Alternative sequence: None

    --------------------------------------------------------
    Use Case: Select Items (GUI)
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
    Use Case: Make Order (GUI)
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
    Use Case: View Order (GUI)
    --------------------------------------------------------
    Precondition: Customer is logged in.

    Main sequence:
    1. The customer requests all order information.
    2. The system displays each order with date, products, quantities, and total.

    Alternative sequence: None

--------------------------------------------------------
                5. Notes and Limitations
--------------------------------------------------------
    - Only Customer login and create account are functional at the moment.
    - There might be slight UI issues and scaling depending on screen size. I have a 3440x1440p monitor so everything looks big enough. Not sure how it looks on a laptop.
    - Next time if I have a project that has console based application then a GUI. I will just have one class have all the System.out.print and Scanner, so I don't have to look everywhere for the prints and scanners.
    - Also separating the bank causes another issue that it is separate from the COS so meaning, every instance of the ChaseBank needs to be get everytime I need to have a transaction.
    - Also the scale of the classes and relationships between the classes is a lot of working redesigning every class with scanner or system.out.print statements in a timeframe of 2-3 weeks, plus finals and midterms and other projects.
    - Optionally choose Console based application to create ChaseBank customer accounts if needed.
    - Design tradeoff due to timing and other midterms/finals and projects.
    - Pretty sure all the rubric use cases are fulfilled successfully.

--------------------------------------------------------
                    End of README
--------------------------------------------------------