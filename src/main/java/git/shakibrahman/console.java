package git.shakibrahman;

import git.shakibrahman.model.IOPackage.LucyIO;
import git.shakibrahman.model.generator.CoreGenerator;
import git.shakibrahman.model.person.Person;
import git.shakibrahman.model.utils.LucyUtils;

import java.io.Console;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * The terminal UI for the password manager.
 */
public class console {
    private static int takeNumIn(String prompt) {
        Scanner in = new Scanner(System.in);
        int i;
        try {
            i = in.nextInt();
            in.nextLine();
        } catch(Exception e) {
            in.nextLine();
            System.out.print(prompt);
            return takeNumIn(prompt);
        }
        return i;
    }

    private static boolean takeBool(String prompt) {
        String inString;
        Scanner in = new Scanner(System.in);
        do {
            System.out.print(prompt);
            inString = in.nextLine();
        } while(inString.equals(""));
        return (inString.charAt(0) == 'y' || inString.charAt(0) == 'Y');
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        LucyIO lucyIO = new LucyIO();
        CoreGenerator generator;
        Scanner in = new Scanner(System.in);

        if (!lucyIO.startUpCheck()) {
            Person user;

            System.out.println("Accessing...");

            do {
                System.out.print("Username: ");
                user = new Person(in.nextLine());
            } while (user.getName().equals(""));

            // comment out these if you're using a real terminal
            // System.out.print("Password: ");
            // user.setPassword(in.nextLine());

            // comment out this if you're using an IDE
            Console console = System.console();

            do {
                char[] password = console.readPassword("Password: ");
                StringBuilder pw = new StringBuilder();
                for (char c : password) {
                    pw.append(c);
                }
                user.setPassword(pw.toString());
            } while (user.getPassword().equals(""));

            do {
                System.out.print("PIN: ");
                user.setPin(takeNumIn("PIN must be numeric and between 1 and 8 characters long! Try again! > "));
            } while (user.getPin() < 0);

            System.out.println("Access granted! Logged in as: " + user.getName());

            boolean c = takeBool("Remember Me? (y or n): ");

            if (c)
                lucyIO.rememberMe(user);

            generator = new CoreGenerator(user);

            System.out.println();
        } else {
            generator = lucyIO.getGenerator();
        }

        int len = 16;
        byte key = (byte) 15;
        String service;
        int passNum = 1;

        boolean exit = false;

        while(!exit) {
            System.out.println("To generating a password, press: 1\nFor advanced options, press: 2\n" +
                    "To exit program, press: 3\nTo log out and exit, press: 4");
            System.out.print("> ");
            int choice = takeNumIn("Invalid input. Try again! > ");

            switch(choice) {
                case 1: System.out.print("Service name: ");
                    service = in.nextLine();
                    String pass = generator.getPassword(service, passNum, len, key);
                    boolean c = takeBool("Copy password to clipboard? (y or n) > ");
                    if (c) {
                        LucyUtils.copyText(pass);
                        System.out.println("Copied to clipboard!\n");
                    }
                    System.out.println("Your password is:\n" + pass + "\n");
                    break;
                case 2: System.out.print("\nChange advanced options:\n1. Password length\n2. Password number\n" +
                        "3. Legal characters\n4. Go back\n5. Exit program\n> ");
                    int choice2 = takeNumIn("Invalid input. Try again! > ");
                    switch (choice2) {
                        case 4:
                            break;
                        case 1: do {
                                System.out.print("Set new length (4-64): ");
                                len = takeNumIn("Invalid character! Try again > ");
                            } while (len < 4 || len > 64);
                            break;
                        case 2: System.out.print("Previous passNum: "+passNum+"\tSet new passNum: > ");
                            passNum = takeNumIn("Invalid input. Try again! > ");
                            break;
                        case 3: key = (byte) 0;
                            c = takeBool("Include lowercase? (y or n) > ");
                            if (c) key |= 0b0001;
                            c = takeBool("Include uppercase? (y or n) > ");
                            if (c) key |= 0b0010;
                            c = takeBool("Include numbers? (y or n) > ");
                            if (c) key |= 0b0100;
                            c = takeBool("Include symbols? (y or n) > ");
                            if (c) key |= 0b1000;
                            if (key == 0) {
                                System.out.println("Error! Cannot uncheck all. Setting lowercase only");
                                key = 1;
                            }
                            break;
                        case 5: exit = true;
                                break;
                        default: System.out.println("Unsupported input");
                        }
                    break;
                case 3: exit = true;
                    break;
                case 4: lucyIO.logOut();
                    System.exit(0);
                    break;
                default: System.out.println("Unsupported input");

            }
        }
    }
}
