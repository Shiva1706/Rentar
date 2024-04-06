import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay ){
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId(){
        return carId;
    }

    public String getBrand(){
        return brand;
    }
    public String getModel(){
        return model;
    }
    public double calculatePrice(int rentalDays){
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void rent(){
        isAvailable = false; 
    }

    public void returnCar(){
        isAvailable = true;
    }
}

class Customer{
    private String customerId;
    private String customerName;

    public Customer(String customerId, String customerName){
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public String getCustomerId(){
        return customerId;
    }
    public String getCustomerName(){
        return customerName;
    }
}

class Rental{
    private Car car;
    private Customer customer;
    private int days;


    public Rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer = customer; 
        this.days = days;
    }

    public Car getCar(){
        return car;
    }
    
    public Customer getCustomer(){
        return customer;
    }
    public int getDays(){
        return days;
    }
}

class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days){
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        }else{
            System.out.println("Car is not available for rent!");
        }
    }

    public void returnCar(Car car){
        car.returnCar();
        Rental rentalToRemove = null;
        for(Rental rental:rentals){
            if(rental.getCar()==car){
                rentalToRemove =rental;
                break;
            }
        }
        if(rentalToRemove != null){
            rentals.remove(rentalToRemove);
        }else{
            System.out.println("Car was not rented.");
        }
    }

    public void menu(){
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("===== Rentar - Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.println("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.println("\n== Rent a car ==\n");
                System.out.println("Enter your name: ");
                String customerName = sc.nextLine();

                System.out.println("\nAvailable Cars: ");
                for(Car car: cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCarId()+" - "+ car.getBrand()+" - "+ car.getModel());
                    }
                }

                System.out.println("\nEnter the car ID you want to rent: ");
                String carId =sc.nextLine();


                System.out.println("Enter the number of days for rental: ");
                int rentalDays = sc.nextInt();
                sc.nextLine();

                Customer newCustomer = new Customer("CUS"+(customers.size()), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for(Car car:cars){
                    if(car.getCarId().equals(carId) && car.isAvailable()){
                        selectedCar = car;
                        break;
                    }
                }

                if(selectedCar != null){
                    double totalprice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: "+ newCustomer.getCustomerId());
                    System.out.println("Customer Name: "+ newCustomer.getCustomerName());
                    System.out.println("Car: "+selectedCar.getBrand()+" "+selectedCar.getModel() );
                    System.out.println("Rental Days: "+rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalprice );

                    System.out.println("\nConfirm Rental(Y/N): ");
                    String confirm = sc.nextLine();

                    if(confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                    }else{
                        System.out.println("\nRental cancelled.");
                    }
                }else{
                    System.out.println("\nInvalid Car selection or car not available for rent.");
                }
            }
            else if(choice ==2){
                System.out.println("\n== Return a Car ==\n");
                System.out.println("Enter the car ID you want to return: ");
                String carId = sc.nextLine();

                Car carToReturn = null;
                for(Car car:cars){
                    if(car.getCarId().equals(carId) && !car.isAvailable()){
                        carToReturn = car;
                        break;
                    }
                }
                if(carToReturn != null){
                    Customer customer=null;
                    for(Rental rental: rentals){
                        if(rental.getCar() == carToReturn){
                            customer =rental.getCustomer();
                            break;
                        }
                    }

                    if(customer != null){
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by "+ customer.getCustomerName());
                    }else{
                        System.out.println("Car was not rented or rental information is missing. ");
                    }
                }else{
                    System.out.println("Invalid car ID or car is not rented.");
                }
            }else if(choice == 3){
                break;
            }else{
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        System.out.println("\nThank you for using the Rentar - Car Rental System!");
        sc.close();
    }
}

public class App {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();
        Car car1 = new Car("C001", "Toyota", "Camry", 60.0);
        Car car2 = new Car("C002", "Tata", "Thar", 80.0);
        Car car3 = new Car("C003", "Tata", "Safari", 100.0);
        Car car4 = new Car("C004", "Honda", "City", 120.0);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.addCar(car4);
        rentalSystem.menu();
    }
}
