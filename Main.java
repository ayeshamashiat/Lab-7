import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) {
        System.out.println("Running Tests...");
        runTests();
        System.out.println("All Tests Passed!\n");

        System.out.println("Starting User Management System...");
        UserManagementSystem system = new UserManagementSystem();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Username and Password:");
        String username = sc.next();
        String password = sc.next();
        system.authenticate(username, password);
    }

    private static void runTests() {
        testUserCreation();
        testAddUser();
        testModifyUser();
        testRenameFile();
    }

    private static void testUserCreation() {
        User user = new User(1, "testuser", "test@example.com", "password", "Regular");
        assert user.userId == 1;
        assert user.username.equals("testuser");
        assert user.email.equals("test@example.com");
        assert user.password.equals("password");
        assert user.userType.equals("Regular");
    }

    private static void testAddUser() {
        UserManagementSystem system = new UserManagementSystem();
        int initialSize = system.users.size();
        system.users.add(new User(3, "newUser", "new@example.com", "newpass", "Regular"));
        assert system.users.size() == initialSize + 1;
    }

    private static void testModifyUser() {
        UserManagementSystem system = new UserManagementSystem();
        User user = new User(4, "modifyUser", "modify@example.com", "modifypass", "Regular");
        system.users.add(user);
        user.update("modifiedUser", "modified@example.com", "newpass", "Power");
        assert user.username.equals("modifiedUser");
        assert user.email.equals("modified@example.com");
        assert user.password.equals("newpass");
        assert user.userType.equals("Power");
    }

    private static void testRenameFile() {
        String oldFile = "testFile.txt";
        String newFile = "renamedTestFile.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(oldFile))) {
            writer.write("Test Content");
        } catch (IOException e) {
            assert false : "File setup failed.";
        }
        File old = new File(oldFile);
        File renamed = new File(newFile);
        if (old.renameTo(renamed)) {
            assert renamed.exists() : "Renamed file not found.";
        } else {
            assert false : "File rename failed.";
        }
        renamed.delete();
    }
}