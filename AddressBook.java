import java.io.Serializable;

public class Contact implements Serializable{
    private String name;
    private String email;
    private Integer age;
    private String phone;

    public Contact(String name, String email, int age, String phone) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "Name='" + name + '\'' +
                ", Email='" + email + '\'' +
                ", Age=" + age +
                ", Phone='" + phone + '\'' +
                '}';
    }
}
import java.io.*;
import java.util.HashMap;

public class AddressBook{
    private HashMap<String, Contact> contacts;
    private final String FILE_NAME = "contacts.ser";

    public AddressBook() {
        this.contacts = new HashMap<>();
        loadContacts(); 
    }

    public void addContact(Contact contact) {
        if (contacts.containsKey(contact.getEmail())) {
            System.out.println("A contact with this email already exists.");
        } else {
            contacts.put(contact.getEmail(), contact);
            System.out.println("Contact added successfully.");
            storeContacts(); 
        }
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("The address book is empty.");
        } else {
            contacts.values().forEach(System.out::println);
        }
    }

    public void searchContact(String email) {
        Contact contact = contacts.get(email);
        if (contact != null) {
            System.out.println(contact);
        } else {
            System.out.println("Contact not found.");
        }
    }

    public void deleteContact(String email) {
        if (contacts.containsKey(email)) {
            System.out.println("Are you sure you want to delete this contact? (yes/no)");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String confirmation = reader.readLine();
                if (confirmation.equalsIgnoreCase("yes")) {
                    contacts.remove(email);
                    System.out.println("Contact deleted.");
                    storeContacts(); // Save changes to file
                } else {
                    System.out.println("Contact not deleted.");
                }
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        } else {
            System.out.println("No contact found with the provided email.");
        }
    }

    private void storeContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadContacts() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                contacts = (HashMap<String, Contact>) ois.readObject();
                System.out.println("Contacts loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading contacts: " + e.getMessage());
            }
        }
    }
}
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n*** Contact Address Book ***");
            System.out.println("1. Add contact");
            System.out.println("2. View contacts");
            System.out.println("3. Search for contact");
            System.out.println("4. Delete contact");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            option = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (option) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); // Clear buffer
                    System.out.print("Enter phone number: ");
                    String phone = scanner.nextLine();
                    Contact contact = new Contact(name, email, age, phone);
                    addressBook.addContact(contact);
                    break;
                case 2:
                    addressBook.viewContacts();
                    break;
                case 3:
                    System.out.print("Enter the email of the contact to search: ");
                    email = scanner.nextLine();
                    addressBook.searchContact(email);
                    break;
                case 4:
                    System.out.print("Enter the email of the contact to delete: ");
                    email = scanner.nextLine();
                    addressBook.deleteContact(email);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 5);

        scanner.close();
    }
}
