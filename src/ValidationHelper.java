import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {
    Scanner scanner = new Scanner(System.in);
    public int validateIntegerWithRange(int lowerBound, int upperBound, String errorMessage) {
        // Keep Asking the user input until the input is a number and it is between the lower and upper bound
        while (true) {
            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                if (option < lowerBound || option > upperBound) {
                    System.out.println(errorMessage);
                    throw new InputOutOfRangeException();
                }
                return option;
            } catch (InputOutOfRangeException e) {
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("input must be a number! Please try again.");
            }
        }
    }

    public int validateInteger() {
        // Keep asking the user to type integer input
        while (true) {
            try {
                int option = scanner.nextInt();
                scanner.nextLine();
                return option;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("input must be a number! Please try again.");
            }
        }
    }

    public String validateEmail() {
        // Keep asking the user to type valid email address
        while (true) {
            String userEmail = scanner.nextLine();
            // The email address must contain @ and . symbols
            String regex = "^[A-Za-z0-9]+@[A-Za-z0-9]+[.]+([A-Za-z0-9].+)+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(userEmail);
            if (matcher.matches()) {
                return userEmail;
            } else {
                System.out.println("Invalid email address. Email should contain at least a @, and . symbols. Please try again");
            }
        }
    }

    public boolean validateConfirmation() {
        // Keep asking the user until "Y" or "N" is chosen
        while (true) {
            String reserveOption = scanner.nextLine();
            if (reserveOption.equals("Y")) {
                return true;
            } else if (reserveOption.equals("N")) {
                return false;
            } else {
                System.out.println("Invalid option. Please try again");
            }
        }
    }

    public String validateStringInput() {
        // Keep asking the user to type non-empty string input
        while (true) {
            String inputOption = scanner.nextLine();
            if (!inputOption.isEmpty()) {
                return inputOption;
            } else {
                System.out.println("Input must not be empty. Please try again");
            }
        }
    }
    public String validatePickupDate(){
        // Keep asking the user to type valid (parsable) pickup date with format dd/MM/yyyy
        while (true) {
            String pickupDate = scanner.nextLine();
            try {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
                dateFormatter.setLenient(false);
                dateFormatter.parse(pickupDate);
                return pickupDate;
            } catch (ParseException e) {
                System.out.println("Please input a valid date.");
            }
        }
    }
    public String validateReturnDate(Date pickupDate) {
        // Keep asking the user to type valid (parsable) return date with format dd/MM/yyyy
        // Check if the return date is chronologically later than the pickup date.
        // If not, the return date is invalid
        while (true) {
            String returnDate = scanner.nextLine();
            try {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
                dateFormatter.setLenient(false);
                Date returnDateReformat = dateFormatter.parse(returnDate);
                long diffInMillies = returnDateReformat.getTime() - pickupDate.getTime();
                if (diffInMillies < 0) {
                    System.out.println("The return date must not be prior to the pickup date. Please try again.");
                    continue;
                }
                return returnDate;
            } catch (ParseException e) {
                System.out.println("Please input a valid date");
            }
        }
    }
}
