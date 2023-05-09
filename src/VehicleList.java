import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.*;

public class VehicleList {
    private HashMap<String, Vehicle> vehicleList = new HashMap<String, Vehicle>();
    private String dataSourcePath = "Fleet.csv";

    public HashMap<String, Vehicle> getVehicleList() {
        return vehicleList;
    }

    public ArrayList<String> getAllVehicleTypes() {
        // All vehicle types set is used to display different types of vehicle when looping through the loop
        LinkedHashSet<String> allVehicleTypesSet = new LinkedHashSet<String>();
        // All vehicle types array is used to store all types of vehicle for fast index retrieval in the menu
        ArrayList<String> allVehicleTypesArray = new ArrayList<>();
        // Loop through each vehicle in the CSV file and add the vehicle types to the linked hash set
        for (Vehicle vehicle : this.vehicleList.values()) {
            allVehicleTypesSet.add(vehicle.getType());
        }
        allVehicleTypesArray.addAll(allVehicleTypesSet);
        allVehicleTypesArray.add("Go to main menu");
        return allVehicleTypesArray;
    }

    public ArrayList<Vehicle> getMatchingBrand(String brandName) {
        // Search result array is used to store all matching vehicles for fast index retrieval in the menu
        ArrayList<Vehicle> searchResult = new ArrayList<Vehicle>();
        // The hashmap is used to store the vehicle objects which can be quickly indexed by the vehicles' ids
        HashMap<String, Vehicle> newVehicleList = new HashMap<String,Vehicle>();
        // Loop through each vehicle in the CSV file and find vehicles with the same brand name specified by the customer
        for (Vehicle vehicle : this.vehicleList.values()) {
            if (vehicle.getBrand().equals(brandName)) {
                newVehicleList.put(vehicle.getVehicleID(), vehicle);
                searchResult.add(vehicle);
            }
        }
        this.vehicleList = newVehicleList;
        return searchResult;
    }
    public ArrayList<Vehicle> getMatchingType(String vehicleType) {
        // Search result array is used to store all matching vehicles for fast index retrieval in the menu
        ArrayList<Vehicle> searchResult = new ArrayList<Vehicle>();
        // The hashmap is used to store the vehicle objects which can be quickly indexed by the vehicles' ids
        HashMap<String, Vehicle> newVehicleList = new HashMap<String, Vehicle>();
        // Loop through each vehicle in the CSV file and find vehicles with the same type specified by the customer
        for (Vehicle vehicle : this.vehicleList.values()) {
            if (vehicle.getType().equals(vehicleType)) {
                searchResult.add(vehicle);
                newVehicleList.put(vehicle.getVehicleID(), vehicle);
            }
        }
        this.vehicleList = newVehicleList;
        return searchResult;
    }

    public ArrayList<Vehicle> getMatchingNoOfPassengers(int noOfPassengers) {
        // Search result array is used to store all matching vehicles for fast index retrieval in the menu
        ArrayList<Vehicle> searchResult = new ArrayList<>();
        // The hashmap is used to store the vehicle objects which can be quickly indexed by the vehicles' ids
        HashMap<String, Vehicle> newVehicleList = new HashMap<String, Vehicle>();
        // Loop through each vehicle in the CSV file and find vehicles with the matching number of passengers specified by the customer
        for (Vehicle vehicle : this.vehicleList.values()) {
            if (vehicle.getNoOfSeats() >= noOfPassengers) {
                searchResult.add(vehicle);
                newVehicleList.put(vehicle.getVehicleID(), vehicle);
            }
        }
        this.vehicleList = newVehicleList;
        return searchResult;
    }

    public void loadDataFromCSV() {
        // The hashmap is used to store the vehicle objects which can be quickly indexed by the vehicles' ids
        this.vehicleList = new HashMap<String, Vehicle>();
        Scanner scanner;
        try {
            // Load data from the source path
            scanner = new Scanner(new File(this.dataSourcePath));
            // Skip the table header
            scanner.nextLine();
            // For each row, get the column data by splitting the comma
            while (scanner.hasNextLine()) {
                String[] record = scanner.nextLine().split(",");
                Vehicle vehicle;
                String vehicleID = record[0];
                String brand = record[1];
                String model = record[2];
                String type = record[3];
                String color = record[6];
                // Assign all columns with string type to the vehicle
                vehicle = new Vehicle(vehicleID, brand, model, type, color);
                // Try catch to catch error when converting non-string columns to numbers
                // If any of the fields is inconvertible, assign the field to -1
                try {
                    vehicle.setYearOfManufacture(Integer.parseInt(record[4]));
                } catch (NumberFormatException e) {
                    vehicle.setYearOfManufacture(-1);
                }
                try {
                    vehicle.setNoOfSeats(Integer.parseInt(record[5]));
                } catch (NumberFormatException e) {
                    vehicle.setNoOfSeats(-1);
                }
                try {
                    vehicle.setRentalPerDay(Integer.parseInt(record[7]));
                } catch (NumberFormatException e) {
                    vehicle.setRentalPerDay(0);
                }
                try {
                    vehicle.setInsurancePerDay(Integer.parseInt(record[8]));
                } catch (NumberFormatException e) {
                    vehicle.setInsurancePerDay(0);
                }
                try {
                    vehicle.setServiceFee(Integer.parseInt(record[9]));
                } catch (NumberFormatException e) {
                    vehicle.setServiceFee(0);
                }
                try {
                    vehicle.setDiscount(Integer.parseInt(record[10]));
                } catch (NumberFormatException e) {
                    vehicle.setDiscount(0);
                }
                // Add the vehicle object to vehicle list
                this.vehicleList.put(vehicle.getVehicleID(), vehicle);
            }
            // Close the scanner
            scanner.close();
        } catch (FileNotFoundException e) {
            // If the file does not exist, print out the message
            System.out.println("Data could not be loaded. The data source or file is missing!");
        }
    }
}
