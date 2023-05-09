import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationHelperTest {

    ValidationHelper validationHelper = new ValidationHelper();

    @BeforeEach
    public void setUp() {
        validationHelper = new ValidationHelper();
    }

    public void simulateUserInput(String userInput) {
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);
        validationHelper.scanner = new Scanner(System.in);
    }

    @Test
    public void validateIntegerWithRange() {
        assertAll("test input with range validation:",
                () -> {
                    // Correct test case
                    String userInput = "3\n";
                    simulateUserInput(userInput);
                    int inputInteger = validationHelper.validateIntegerWithRange(1, 5, "");
                    System.out.println("input: "+ inputInteger);
                    assertTrue(inputInteger <= 5 && inputInteger >= 1);
                },
                () -> {
                    // Less than lower bound input
                    String userInput = "0\n3\n";
                    simulateUserInput(userInput);
                    int inputInteger = validationHelper.validateIntegerWithRange(1, 5, "");
                    assertTrue(inputInteger <= 5 && inputInteger >= 1);
                },
                () -> {
                    // More than upper bound input
                    String userInput = "6\n3\n";
                    simulateUserInput(userInput);
                    int inputInteger = validationHelper.validateIntegerWithRange(1, 5, "");
                    assertTrue(inputInteger <= 5 && inputInteger >= 1);
                },
                () -> {
                    // Input is not an integer
                    String userInput = "fawfew\n3\n";
                    simulateUserInput(userInput);
                    int inputInteger = validationHelper.validateIntegerWithRange(1, 5, "oh no");
                    assertTrue(inputInteger <= 5 && inputInteger >= 1);
                }
        );
    }

    @Test
    public void validateInteger() {
        assertAll("test integer input:",
                () -> {
                    // Correct test case
                    String userInput = "3\n";
                    simulateUserInput(userInput);
                    int inputInteger = validationHelper.validateInteger();
                    assertTrue(inputInteger == 3);
                },
                () -> {
                    // Input is not an integer
                    String userInput = "fawfew\n3\n";
                    simulateUserInput(userInput);
                    int inputInteger = validationHelper.validateInteger();
                    assertTrue(inputInteger == 3);
                }
        );

    }

    @Test
    public void validateEmail() {
        assertAll("test email input:",
                () -> {
                    // Correct test case
                    String userInput = "huannguyen@gmail.com\n";
                    simulateUserInput(userInput);
                    String inputEmail = validationHelper.validateEmail();
                    assertTrue(inputEmail.equals("huannguyen@gmail.com"));
                },
                () -> {
                    // Invalid email (email does not contain @ symbol)
                    String userInput = "huannguyen.com\nhuannguyen@gmail.com\n";
                    simulateUserInput(userInput);
                    String inputEmail = validationHelper.validateEmail();
                    assertTrue(inputEmail.equals("huannguyen@gmail.com"));
                },
                () -> {
                    // Invalid email (email does not contain . symbol)
                    String userInput = "huannguyen@gmail\nhuannguyen@gmail.com\n";
                    simulateUserInput(userInput);
                    String inputEmail = validationHelper.validateEmail();
                    assertTrue(inputEmail.equals("huannguyen@gmail.com"));
                }
        );

    }

    @Test
    public void validateConfirmation() {
        assertAll("test yes no question:",
                () -> {
                    // Correct test case (Y)
                    String userInput = "Y\n";
                    simulateUserInput(userInput);
                    boolean isConfirm = validationHelper.validateConfirmation();
                    assertTrue(isConfirm);
                },
                () -> {
                    // Correct test case (N)
                    String userInput = "N\n";
                    simulateUserInput(userInput);
                    boolean isConfirm = validationHelper.validateConfirmation();
                    assertTrue(!isConfirm);
                },
                () -> {
                    // Invalid input (other than "Y" or "N")
                    String userInput = "fewfwefw\nY\n";
                    simulateUserInput(userInput);
                    boolean isConfirm = validationHelper.validateConfirmation();
                    assertTrue(isConfirm);
                }

        );
    }

    @Test
    public void validateStringInput() {
        assertAll("test string input:",
                () -> {
                    // Correct test case
                    String userInput = "huan nguyen\n";
                    simulateUserInput(userInput);
                    String stringInput = validationHelper.validateStringInput();
                    assertTrue(stringInput.equals("huan nguyen"));
                },
                () -> {
                    // Input is empty
                    String userInput = "\nhuan nguyen\n";
                    simulateUserInput(userInput);
                    String stringInput = validationHelper.validateStringInput();
                    assertTrue(stringInput.equals("huan nguyen"));
                }
        );

    }

    @Test
    public void validatePickupDate() {
        assertAll("test pickup date:",
                () -> {
                    // Correct test case
                    String userInput = "12/12/2022\n";
                    simulateUserInput(userInput);
                    String stringInput = validationHelper.validatePickupDate();
                    assertTrue(stringInput.equals("12/12/2022"));
                },
                () -> {
                    // Invalid date
                    String userInput = "huan nguyen\n20/12/2022\n";
                    simulateUserInput(userInput);
                    String stringInput = validationHelper.validatePickupDate();
                    assertTrue(stringInput.equals("20/12/2022"));
                },
                () -> {
                    // Input is empty
                    String userInput = "\n20/12/2022\n";
                    simulateUserInput(userInput);
                    String stringInput = validationHelper.validatePickupDate();
                    assertTrue(stringInput.equals("20/12/2022"));
                }
        );

    }

    @Test
    public void validateReturnDate() {

        assertAll("test return input:",
                () -> {
                    // Correct test case
                    String userInput = "15/12/2022\n";
                    simulateUserInput(userInput);
                    Date pickupDateReformat;
                    try {
                         pickupDateReformat = new SimpleDateFormat("dd/MM/yyyy").parse("12/12/2022");
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    String stringInput = validationHelper.validateReturnDate(pickupDateReformat);
                    assertTrue(stringInput.equals("15/12/2022"));
                },
                () -> {
                    // Invalid date
                    String userInput = "huan nguyen\n20/12/2022\n";
                    Date pickupDateReformat;
                    try {
                        pickupDateReformat = new SimpleDateFormat("dd/MM/yyyy").parse("12/12/2022");
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    simulateUserInput(userInput);
                    String stringInput = validationHelper.validateReturnDate(pickupDateReformat);
                    assertTrue(stringInput.equals("20/12/2022"));
                },
                () -> {
                    // Input is empty
                    String userInput = "\n20/12/2022\n";
                    simulateUserInput(userInput);
                    Date pickupDateReformat;
                    try {
                        pickupDateReformat = new SimpleDateFormat("dd/MM/yyyy").parse("12/12/2022");
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    String stringInput = validationHelper.validateReturnDate(pickupDateReformat);
                    assertTrue(stringInput.equals("20/12/2022"));
                },
                () -> {
                    // Return date is prior to the pickup date
                    String userInput = "10/12/2022\n14/12/2022\n";
                    simulateUserInput(userInput);
                    Date pickupDateReformat;
                    try {
                        pickupDateReformat = new SimpleDateFormat("dd/MM/yyyy").parse("12/12/2022");
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    String stringInput = validationHelper.validateReturnDate(pickupDateReformat);
                    assertTrue(stringInput.equals("14/12/2022"));
                }
        );

    }
}