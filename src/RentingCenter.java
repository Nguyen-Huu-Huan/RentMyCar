import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class RentingCenter {
    private VehicleList vehicles = new VehicleList();
    private MenuHelper menuHelper = new MenuHelper();

    public void run() {
        System.out.println("Welcome to MyCar!");
        boolean isExit = false;
        while (!isExit) {
            this.menuHelper = new MenuHelper();
            // Load data from the csv and print out the instruction
            this.vehicles.loadDataFromCSV();
            this.menuHelper.printMenuInstruction("Select from main menu");
            // Prompt the customers for options
            int option = menuHelper.promptMenuOptions();
            switch (option) {
                case 1:
                    isExit = this.searchByBrand();
                    break;
                case 2:
                    isExit = this.browseVehicleByType();
                    break;
                case 3:
                    isExit = this.filterByNoOfPassengers();
                    break;
                case 4:
                    // When the customer chooses 4 (exit), set exit to true and break the loop
                    isExit = true;
                    break;
                default:
                    break;
            }
        }
        this.exit();
    }

    private boolean filterByNoOfPassengers() {
        while (true) {
            // Prompt the customer for the desired number of passengers
            System.out.print("Please provide the number of passengers: ");
            ValidationHelper validationHelper = new ValidationHelper();
            int noOfPassengers = validationHelper.validateInteger();

            // Add all vehicles' information from the search result to the menu and set the menu options.
            this.menuHelper.printMenuInstruction("Select from matching list");
            ArrayList<Vehicle> matchingVehicles = vehicles.getMatchingNoOfPassengers(noOfPassengers);
            ArrayList<String> newMenuOptions = new ArrayList<String>();
            for (Vehicle matchingVehicle : matchingVehicles) {
                newMenuOptions.add(matchingVehicle.getVehicleInfo());
            }
            newMenuOptions.add("Go to main menu");
            this.menuHelper.setMenuOptions(newMenuOptions);
            // Prompt the customer for his/her vehicle choice
            int vehicleChoice = this.menuHelper.promptMenuOptions();
            // if the search choice is equal to the last option, back to the main menu
            if (vehicleChoice == this.menuHelper.getMenuOptions().size()) return false;
            // Get the vehicle object
            Vehicle matchingVehicle = this.vehicles.getVehicleList().get(matchingVehicles.get(vehicleChoice - 1).getVehicleID());
            Customer customer = new Customer();
            // Proceed the customers to reservation and payment
            return this.menuHelper.promptReserveVehicle(customer, matchingVehicle);
        }
    }

    private boolean browseVehicleByType() {
        while (true) {
            this.menuHelper.printMenuInstruction("Browse by type of vehicle");
            this.menuHelper.setMenuOptions(this.vehicles.getAllVehicleTypes());
            int vehicleTypeChoice = this.menuHelper.promptMenuOptions();
            // if the search choice is equal to the last option, back to the main menu
            if (vehicleTypeChoice == this.menuHelper.getMenuOptions().size()) return false;
            this.menuHelper.printMenuInstruction("Browse by type of vehicle");
            ArrayList<Vehicle> matchingVehicles = vehicles.getMatchingType(this.menuHelper.getMenuOptions().get(vehicleTypeChoice - 1));
            ArrayList<String> newMenuOptions = new ArrayList<String>();
            for (Vehicle matchingVehicle : matchingVehicles) {
                newMenuOptions.add(matchingVehicle.getVehicleInfo());
            }
            newMenuOptions.add("Go to main menu");
            this.menuHelper.setMenuOptions(newMenuOptions);
            int searchChoice = this.menuHelper.promptMenuOptions();
            // if the search choice is equal to the last option, back to the main menu
            if (searchChoice == this.menuHelper.getMenuOptions().size()) return false;
            // Get the vehicle object
            Vehicle matchingVehicle = this.vehicles.getVehicleList().get(matchingVehicles.get(searchChoice - 1).getVehicleID());
            Customer customer = new Customer();
            // Proceed the customers to reservation and payment
            return this.menuHelper.promptReserveVehicle(customer, matchingVehicle);
        }

    }

    private boolean searchByBrand() {
        // Ask the customer for their desired brand
        System.out.print("Please provide a brand: ");
        ValidationHelper validationHelper = new ValidationHelper();
        String brandName = validationHelper.validateStringInput();
        menuHelper.printMenuInstruction("Select from matching list");
        // Find vehicles from the data source with matching brand
        ArrayList<Vehicle> matchingVehicles = vehicles.getMatchingBrand(brandName);
        // Add the search result to the menu option list
        ArrayList<String> newMenuOptions = new ArrayList<String>();
        for (Vehicle matchingVehicle : matchingVehicles) {
            newMenuOptions.add(matchingVehicle.getVehicleInfo());
        }
        newMenuOptions.add("Go to main menu");
        this.menuHelper.setMenuOptions(newMenuOptions);
        // Prompt the customer for his/her option and back to the menu if the customer chooses the last option
        int searchChoice = menuHelper.promptMenuOptions();
        if (searchChoice == this.menuHelper.getMenuOptions().size()) return false;
        // Get the vehicle object from the customer's option
        Vehicle matchingVehicle = this.vehicles.getVehicleList().get(matchingVehicles.get(searchChoice - 1).getVehicleID());
        Customer customer = new Customer();
        // Proceed the customers to reservation and payment
        return this.menuHelper.promptReserveVehicle(customer, matchingVehicle);
    }

    private void exit() {
        System.out.println("Thank you for shopping with us! Have a fantastic day ^.^.");
    }
}
