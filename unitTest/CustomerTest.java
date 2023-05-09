import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setRentedVehicle(new Vehicle("C002", "Toyota", "Corolla", "Hatch", 2020, 4, "White", 45, 20, 10, 10));
    }

    void simulateUserInput(String userInput) {
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);
    }

    @org.junit.jupiter.api.Test
    void testGetCustomerInformation() {
        assertAll("test get customer information",
                () -> {
                    // Correct test case
                    String userInput = "Huan\nNguyen\nhuannguyen@gmail.com\n3\n";
                    simulateUserInput(userInput);
                    customer.getCustomerInformation();
                    assertEquals(customer.getGivenName(), "Huan");
                    assertEquals(customer.getSurName(), "Nguyen");
                    assertEquals(customer.getEmail(), "huannguyen@gmail.com");
                    assertEquals(customer.getNoOfPassengers(), 3);
                },
                () -> {
                    // Missing given name
                    String userInput = "\nHuan\nNguyen\nhuannguyen@gmail.com\n3\n";
                    simulateUserInput(userInput);
                    customer.getCustomerInformation();
                    assertEquals(customer.getGivenName(), "Huan");
                    assertEquals(customer.getSurName(), "Nguyen");
                    assertEquals(customer.getEmail(), "huannguyen@gmail.com");
                    assertEquals(customer.getNoOfPassengers(), 3);
                },
                () -> {
                    // Missing surname
                    String userInput = "Huan\n\nNguyen\nhuannguyen@gmail.com\n3\n";
                    simulateUserInput(userInput);
                    customer.getCustomerInformation();
                    assertEquals(customer.getGivenName(), "Huan");
                    assertEquals(customer.getSurName(), "Nguyen");
                    assertEquals(customer.getEmail(), "huannguyen@gmail.com");
                    assertEquals(customer.getNoOfPassengers(), 3);
                },
                () -> {
                    // Missing email
                    String userInput = "Huan\nNguyen\n\nhuannguyen@gmail.com\n3\n";
                    simulateUserInput(userInput);
                    customer.getCustomerInformation();
                    assertEquals(customer.getGivenName(), "Huan");
                    assertEquals(customer.getSurName(), "Nguyen");
                    assertEquals(customer.getEmail(), "huannguyen@gmail.com");
                    assertEquals(customer.getNoOfPassengers(), 3);
                },
                () -> {
                    // Email with incorrect format (missing @ or .)
                    String userInput = "Huan\nNguyen\nfwefewfwe\nhuannguyen@gmail.com\n3\n";
                    simulateUserInput(userInput);
                    customer.getCustomerInformation();
                    assertEquals(customer.getGivenName(), "Huan");
                    assertEquals(customer.getSurName(), "Nguyen");
                    assertEquals(customer.getEmail(), "huannguyen@gmail.com");
                    assertEquals(customer.getNoOfPassengers(), 3);
                },
                () -> {
                    //Missing number of passengers
                    String userInput = "Huan\nNguyen\nhuannguyen@gmail.com\n\n3\n";
                    simulateUserInput(userInput);
                    customer.getCustomerInformation();
                    assertEquals(customer.getGivenName(), "Huan");
                    assertEquals(customer.getSurName(), "Nguyen");
                    assertEquals(customer.getEmail(), "huannguyen@gmail.com");
                    assertEquals(customer.getNoOfPassengers(), 3);
                },
                () -> {
                    // Input string for number of passengers
                    String userInput = "Huan\nNguyen\nhuannguyen@gmail.com\nhuannguyen\n3\n";
                    simulateUserInput(userInput);
                    customer.getCustomerInformation();
                    assertEquals(customer.getGivenName(), "Huan");
                    assertEquals(customer.getSurName(), "Nguyen");
                    assertEquals(customer.getEmail(), "huannguyen@gmail.com");
                    assertEquals(customer.getNoOfPassengers(), 3);
                },
                () -> {
                    // Out of range number of passengers
                    String userInput = "Huan\nNguyen\nhuannguyen@gmail.com\n5\n-1\n3\n";
                    simulateUserInput(userInput);
                    customer.getCustomerInformation();
                    assertEquals(customer.getGivenName(), "Huan");
                    assertEquals(customer.getSurName(), "Nguyen");
                    assertEquals(customer.getEmail(), "huannguyen@gmail.com");
                    assertEquals(customer.getNoOfPassengers(), 3);
                }
        );

    }

    @org.junit.jupiter.api.Test
    void showCustomerInformation() {

    }

    @org.junit.jupiter.api.Test
    void testRentalPeriod() {
        assertAll("test get rental period",
                () -> {
                    // Correct test case
                    String userInput = "12/12/2022\n15/12/2022\n";
                    simulateUserInput(userInput);
                    int noOfRentalDays = customer.rentalPeriod();
                    assertEquals(customer.getPickupDate(), "12/12/2022");
                    assertEquals(customer.getReturnDate(), "15/12/2022");
                    assertEquals(noOfRentalDays, 4);
                },
                () -> {
                    // Invalid pickup date
                    String userInput = "Huan\n14/12/2022\n16/12/2022\n";
                    simulateUserInput(userInput);
                    int noOfRentalDays = customer.rentalPeriod();
                    assertEquals(customer.getPickupDate(), "14/12/2022");
                    assertEquals(customer.getReturnDate(), "16/12/2022");
                    assertEquals(noOfRentalDays, 3);
                },
                () -> {
                    // Pickup date is empty
                    String userInput = "\n15/12/2022\n20/12/2022\n";
                    simulateUserInput(userInput);
                    int noOfRentalDays = customer.rentalPeriod();
                    assertEquals(customer.getPickupDate(), "15/12/2022");
                    assertEquals(customer.getReturnDate(), "20/12/2022");
                    assertEquals(noOfRentalDays, 6);
                },
                () -> {
                    // Invalid return date
                    String userInput = "14/12/2022\nHuan\n16/12/2022";
                    simulateUserInput(userInput);
                    int noOfRentalDays = customer.rentalPeriod();
                    assertEquals(customer.getPickupDate(), "14/12/2022");
                    assertEquals(customer.getReturnDate(), "16/12/2022");
                    assertEquals(noOfRentalDays, 3);
                },
                () -> {
                    // Return date is empty
                    String userInput = "15/12/2022\n\n20/12/2022\n";
                    simulateUserInput(userInput);
                    int noOfRentalDays = customer.rentalPeriod();
                    assertEquals(customer.getPickupDate(), "15/12/2022");
                    assertEquals(customer.getReturnDate(), "20/12/2022");
                    assertEquals(noOfRentalDays, 6);
                },
                () -> {
                    // Return date is prior to the pickup date
                    String userInput = "15/12/2022\n\n14/12/2022\n16/12/2022";
                    simulateUserInput(userInput);
                    int noOfRentalDays = customer.rentalPeriod();
                    assertEquals(customer.getPickupDate(), "15/12/2022");
                    assertEquals(customer.getReturnDate(), "16/12/2022");
                    assertEquals(noOfRentalDays, 2);
                }
        );
    }
}