
import java.util.*;

class FoodItem {
    int id;
    String name;
    double price;

    FoodItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String toString() {
        return id + ". " + name + " - Rs." + price;
    }
}

class Order {
    int orderId;
    String customerName;
    String address;
    ArrayList<FoodItem> items;
    double total;

    Order(int orderId, String customerName,
          String address, ArrayList<FoodItem> items) {

        this.orderId = orderId;
        this.customerName = customerName;
        this.address = address;
        this.items = new ArrayList<>(items);

        total = 0;
        for (FoodItem item : items) {
            total += item.price;
        }
    }

    void displayOrder() {
        System.out.println("\nOrder ID: " + orderId);
        System.out.println("Customer: " + customerName);
        System.out.println("Address: " + address);

        for (FoodItem item : items) {
            System.out.println(item.name + " - Rs." + item.price);
        }

        System.out.println("Total Bill: Rs." + total);
    }
}

public class Main {

    static Scanner sc = new Scanner(System.in);

    // ArrayList stores food menu
    static ArrayList<FoodItem> menu = new ArrayList<>();

    // ArrayList stores selected food items
    static ArrayList<FoodItem> cart = new ArrayList<>();

    // Queue manages pending orders
    static Queue<Order> orderQueue = new LinkedList<>();

    // Stack stores completed orders
    static Stack<Order> orderHistory = new Stack<>();

    static int orderCounter = 1;

    // Add food items
    static void initializeMenu() {
        menu.add(new FoodItem(1, "Pizza", 200));
        menu.add(new FoodItem(2, "Burger", 100));
        menu.add(new FoodItem(3, "Sandwich", 80));
        menu.add(new FoodItem(4, "Pasta", 150));
        menu.add(new FoodItem(5, "French Fries", 90));
    }

    // Display food menu
    static void displayMenu() {
        System.out.println("\n------ FOOD MENU ------");

        for (FoodItem item : menu) {
            System.out.println(item);
        }
    }

    // Add food to cart
    static void addToCart() {
        displayMenu();

        System.out.print("\nEnter Food ID: ");
        int id = sc.nextInt();

        boolean found = false;

        for (FoodItem item : menu) {
            if (item.id == id) {
                cart.add(item);
                System.out.println(item.name +
                                   " added to cart!");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Invalid Food ID!");
        }
    }

    // View cart
    static void viewCart() {
        System.out.println("\n------ YOUR CART ------");

        if (cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        double total = 0;

        for (FoodItem item : cart) {
            System.out.println(item);
            total += item.price;
        }

        System.out.println("Total Amount: Rs." + total);
    }

    // Place order
    static void placeOrder() {

        if (cart.isEmpty()) {
            System.out.println("Please add food to cart first!");
            return;
        }

        sc.nextLine();

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Delivery Address: ");
        String address = sc.nextLine();

        Order order = new Order(
            orderCounter++, name, address, cart
        );

        // Add order to Queue
        orderQueue.add(order);

        cart.clear();

        System.out.println("\nOrder placed successfully!");
        System.out.println("Your Order ID: " + order.orderId);
    }

    // View pending orders
    static void viewPendingOrders() {

        System.out.println("\n------ PENDING ORDERS ------");

        if (orderQueue.isEmpty()) {
            System.out.println("No pending orders!");
            return;
        }

        for (Order order : orderQueue) {
            order.displayOrder();
        }
    }

    // Process order using Queue
    static void processOrder() {

        if (orderQueue.isEmpty()) {
            System.out.println("No orders to process!");
            return;
        }

        Order order = orderQueue.poll();

        System.out.println("\nProcessing Order...");

        order.displayOrder();

        // Push completed order into Stack
        orderHistory.push(order);

        System.out.println("\nOrder completed successfully!");
    }

    // Display order history using Stack
    static void viewOrderHistory() {

        System.out.println("\n------ ORDER HISTORY ------");

        if (orderHistory.isEmpty()) {
            System.out.println("No completed orders!");
            return;
        }

        for (int i = orderHistory.size() - 1; i >= 0; i--) {
            orderHistory.get(i).displayOrder();
        }
    }

    // Sort food items by price
    static void sortFoodByPrice() {

        Collections.sort(menu,
            Comparator.comparingDouble(item -> item.price));

        System.out.println("\nFood items sorted by price!");
        displayMenu();
    }

    // Main method
    public static void main(String[] args) {

        initializeMenu();

        int choice;

        do {
            System.out.println("\n================================");
            System.out.println("   ONLINE FOOD ORDERING SYSTEM");
            System.out.println("================================");

            System.out.println("1. Display Food Menu");
            System.out.println("2. Add Food to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Place Order");
            System.out.println("5. View Pending Orders");
            System.out.println("6. Process Order");
            System.out.println("7. View Order History");
            System.out.println("8. Sort Food by Price");
            System.out.println("9. Exit");

            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    displayMenu();
                    break;

                case 2:
                    addToCart();
                    break;

                case 3:
                    viewCart();
                    break;

                case 4:
                    placeOrder();
                    break;

                case 5:
                    viewPendingOrders();
                    break;

                case 6:
                    processOrder();
                    break;

                case 7:
                    viewOrderHistory();
                    break;

                case 8:
                    sortFoodByPrice();
                    break;

                case 9:
                    System.out.println(
                        "Thank you for using our system!"
                    );
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 9);

        sc.close();
    }
}
