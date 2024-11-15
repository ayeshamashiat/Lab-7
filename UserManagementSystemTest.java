import java.io.*;
import java.util.*;

public class UserManagementSystemTest {
    public static void main(String[] args) {
        testUserCreation();
        testUserAuthentication();
        testAddUser();
        testModifyUser();
        testRenameFile();
        System.out.println("All tests passed!");
    }

    private static void testUserCreation() {
        User user = new User(1, "testuser", "test@example.com", "password", "Regular");
        assert user.userId == 1;
        assert user.username.equals("testuser");
        assert user.email.equals("test@example.com");
        assert user.password.equals("password");
        assert user.userType.equals("Regular");
    }

    private static void testUserAuthentication() {
        UserManagementSystem system = new UserManagementSystem();
        User user = new User(2, "authUser", "auth@example.com", "pass123", "Power");
        system.users.add(user);
        try {
            system.authenticate("authUser", "pass123");
            System.out.println("Authentication test passed for Power user.");
        } catch (Exception e) {
            assert false : "Authentication failed for valid user.";
        }
    }

    private static void testAddUser() {
        UserManagementSystem system = new UserManagementSystem();
        int initialSize = system.users.size();
        system.users.add(new User(3, "newUser", "new@example.com", "newpass", "Regular"));
        assert system.users.size() == initialSize + 1 : "User addition failed.";
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
        String oldFile = "oldFile.txt";
        String newFile = "newFile.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(oldFile))) {
            writer.write("Test file");
        } catch (IOException e) {
            assert false : "Setup for rename file test failed.";
        }

        File old = new File(oldFile);
        File newF = new File(newFile);
        if (old.renameTo(newF)) {
            assert newF.exists() : "Renamed file not found.";
        } else {
            assert false : "Rename file operation failed.";
        }
        newF.delete();
    }
}
