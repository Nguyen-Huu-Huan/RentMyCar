import java.util.ArrayList;

public class MenuHelper {

    private ArrayList<String> menuOptions = new ArrayList<String>();
    public MenuHelper() {
        // Initialize the constructure with default option (main menu)
        ArrayList<String> mainMenu = new ArrayList<String>() {{
            add("Search by brand");
            add("Browse by vehicle type");
            add("Filter by number of passengers");
            add("Exit");
        }};
        this.menuOptions = mainMenu;
    }

    public int promptMenuOptions() {
        // When the menu option is 1, the only option is to go back to the main menu
        if (this.menuOptions.size() == 1){
            System.out.println("No option available. Back to the main menu");
            return this.menuOptions.size();
        }
        // Otherwise, print out all options
        for (int i = 0; i < this.menuOptions.size(); i++) {
            System.out.println("   " + (i + 1) + ") " + this.menuOptions.get(i));
        }
        System.out.print("Please select: ");
        // Prompt the customer to select options from the menu
        ValidationHelper validationHelper = new ValidationHelper();
        // Custom error message in case the customer may choose an out-of-range option
        String errorMessage = "The input must be between " + 1 + " and " + (this.menuOptions.size());
        return validationHelper.validateIntegerWithRange(1, this.menuOptions.size(), errorMessage);
    }

    public boolean promptReserveVehicle(Customer customer, Vehicle vehicle) {
        // Show the vehicle and the customer details
        vehicle.showVehicleDetails(customer);
        ValidationHelper validationHelper = new ValidationHelper();
        System.out.print("Would you like to reserve the vehicle (Y/N)? ");
        // Ask if the customer wants to reserve the vehicle (Y/N)
        if (validationHelper.validateConfirmation()){
            // Assign the vehicle to the customer's rented list and get the customer's information
            customer.setRentedVehicle(vehicle);
            customer.getCustomerInformation();
            System.out.print("Confirm and pay (Y/N): ");
            // Ask if the customer wants to pay, show the customer's information if payment is confirmed
            if (validationHelper.validateConfirmation()){
                this.printMenuInstruction("Congratulations! Your vehicle is booked. A receipt has been sent to your email. We will soon be in touch before your pick-up date");
                customer.showCustomerInformation();
                return true;
            }
        }
        return false;
    }

    public void setMenuOptions(ArrayList<String> optionData) {
        this.menuOptions = optionData;
    }

    public ArrayList<String> getMenuOptions() {
        return menuOptions;
    }

    public void printMenuInstruction(String instruction) {
        // The method is used to print out the instruction layout with custom instruction
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("> " + instruction);
        System.out.println("--------------------------------------------------------------------------------");
    }
}
