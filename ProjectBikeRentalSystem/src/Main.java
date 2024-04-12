import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Bike {
    private final String bikeIdRC;
    private final String brandName;
    private final String model;
    private final String varient;
    private final double basePricePerDay;
    private boolean isAvailable;

    public Bike(String bikeIdRC, String brandName, String model, String varient, double basePricePerDay) {
        this.bikeIdRC = bikeIdRC;
        this.brandName = brandName;
        this.model = model;
        this.varient = varient;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getBikeIdRC() {
        return bikeIdRC;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getModel() {
        return model;
    }

    public String getVarient() {
        return varient;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public double calculateTotalPrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnBike() {
        isAvailable = true;
    }
}

class Customer {
    private final String customerId;
    private final String name;
    private final String mobileNumber;
    private final String emailId;

    public Customer(String customerId, String name, String mobileNumber, String emailId) {
        this.customerId = customerId;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

}

class Rental {
    private final Bike bike;
    private final Customer customer;
    private final int days;

    public Rental(Bike bike, Customer customer, int days) {
        this.bike = bike;
        this.customer = customer;
        this.days = days;
    }

    public Bike getBike() {
        return bike;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class BikeRentalSystem {
    private final List<Bike> bikes;
    private final List<Customer> customers;
    private final List<Rental> rentals;

    public BikeRentalSystem() {
        bikes = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addBike(Bike bike) {
        bikes.add(bike);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentBike(Bike bike, Customer customer, int days) {
        if (bike.isAvailable()) {
            bike.rent();
            rentals.add(new Rental(bike, customer, days));
        } else {
            System.out.println("Bike is not available for rent.");
        }
    }

    public void returBike(Bike bike) {
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getBike() == bike) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
        } else {
            System.out.println("Car was not rented.");
        }
        bike.returnBike();
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("#==# Bike Rental System #==#");
            System.out.println("1. Rent a Bike");
            System.out.println("2. Return a Bike");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("\n== Rent a Bike ==\n");
                System.out.println("Enter your name :- ");
                String customerName = scanner.nextLine();
                System.out.println("Enter your mobileNumber last six digit:- ");
                int num = scanner.nextInt();
                System.out.println("Enter your Email :- ");
                String email = scanner.nextLine();
                System.out.println("\n Available Bikes :- ");
                for (Bike bike : bikes) {
                    if (bike.isAvailable()) {
                        System.out.println(bike.getBikeIdRC() + " - " + bike.getBrandName() + " - " + bike.getVarient() + " - " + bike.getModel());
                    }
                }
                System.out.print("\nEnter the bike RC ID you want to rent: ");
                String bikeId = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName, "+919466" + num, email);
                addCustomer(newCustomer);

                Bike selectedbike = null;
                for (Bike bike : bikes) {
                    if (bike.getBikeIdRC().equals(bikeId) && bike.isAvailable()) {
                        selectedbike = bike;
                        break;
                    }
                }

                if (selectedbike != null) {
                    double totalPrice = selectedbike.calculateTotalPrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Customer PhoneNumber: " + newCustomer.getMobileNumber());
                    System.out.println("Customer Email: " + newCustomer.getEmailId());
                    System.out.println("Car: " + selectedbike.getBrandName() + " " + selectedbike.getModel() + " " + selectedbike.getVarient());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentBike(selectedbike, newCustomer, rentalDays);
                        System.out.println("\nYour payment is successfully done");
                        System.out.println("\nBike rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                        System.out.println("\n Try again.");
                    }
                } else {
                    System.out.println("\nInvalid bike selection or bike not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n#== Return a Bike ==#\n");
                System.out.print("Enter the bike ID you want to return: ");
                String bikeId = scanner.nextLine();

                Bike bikeToReturn = null;
                for (Bike bike : bikes) {
                    if (bike.getBikeIdRC().equals(bikeId) && !bike.isAvailable()) {
                        bikeToReturn = bike;
                        break;
                    }
                }
                if (bikeToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getBike() == bikeToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        returBike(bikeToReturn);
                        System.out.println("Bike return successfully by " + customer.getName());
                    } else {
                        System.out.println("Bike was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid Bike ID or Bike is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        System.out.println("\n Thank you for using the Bike Rental System!\n Have a good day!");
    }
}

public class Main {
    public static void main(String[] args) {
        BikeRentalSystem rentalSystem = new BikeRentalSystem();

        Bike bike1 = new Bike("Z0001", "Hindustan Motar", "Rajdut", "240cc", 150);
        Bike bike2 = new Bike("Z0002", "Bajaj", "Pulsar", "180cc", 120);
        Bike bike3 = new Bike("Z0003", "RoyalEnfield", "Classic", "350cc", 200);
        Bike bike4 = new Bike("Z0004", "RoyalEnfield", "Standard", "500cc", 500);
        rentalSystem.addBike(bike1);
        rentalSystem.addBike(bike2);
        rentalSystem.addBike(bike3);
        rentalSystem.addBike(bike4);

        rentalSystem.menu();
    }
}