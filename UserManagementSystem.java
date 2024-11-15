import java.io.*;
import java.util.*;

public class UserManagementSystem {
    private static final String USER_FILE = "User.csv";
    private static final String ADMIN_FILE = "Admin.csv";
    final List<User> users = new ArrayList<>();
    final List<User> admins = new ArrayList<>();

    public UserManagementSystem() {
        loadUsers();
        loadAdmins();
    }

    public void authenticate(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                handleUserActions(user);
                return;
            }
        }
        for (User admin : admins) {
            if (admin.username.equals(username) && admin.password.equals(password)) {
                handleAdminActions(admin);
                return;
            }
        }
        throw new RuntimeException("Authentication failed");
    }

    private void loadUsers() {
        loadFromFile(USER_FILE, users, false);
    }

    private void loadAdmins() {
        loadFromFile(ADMIN_FILE, admins, true);
    }

    private void loadFromFile(String file, List<User> list, boolean isAdmin) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                list.add(new User(Integer.parseInt(data[0]), data[1], data[2], data[3], isAdmin ? "Admin" : data[4]));
            }
        } catch (IOException e) {
            System.out.println("Failed to load file: " + file);
        }
    }

    private void saveToFile(String file, List<User> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (User user : list) {
                bw.write(user.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + file);
        }
    }

    private void handleUserActions(User user) {
        if (user.userType.equals("Regular")) {
            displayFile(USER_FILE);
        } else if (user.userType.equals("Power")) {
            displayFile(USER_FILE);
            addUser();
        }
    }

    private void handleAdminActions(User admin) {
        displayFile(USER_FILE);
        addUser();
        modifyUser();
        renameFile(USER_FILE, "New_" + USER_FILE);
    }

    private void displayFile(String file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to display file: " + file);
        }
    }

    private void addUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Username, Email, Password, UserType:");
        String username = sc.next();
        String email = sc.next();
        String password = sc.next();
        String userType = sc.next();
        int id = users.size() + admins.size() + 1;
        users.add(new User(id, username, email, password, userType));
        saveToFile(USER_FILE, users);
    }

    private void modifyUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter User ID to modify:");
        int id = sc.nextInt();
        System.out.println("Enter new Username, Email, Password, UserType:");
        String username = sc.next();
        String email = sc.next();
        String password = sc.next();
        String userType = sc.next();
        for (User user : users) {
            if (user.userId == id) {
                user.update(username, email, password, userType);
                saveToFile(USER_FILE, users);
                return;
            }
        }
    }

    private void renameFile(String oldName, String newName) {
        File oldFile = new File(oldName);
        File newFile = new File(newName);
        if (!oldFile.renameTo(newFile)) {
            throw new RuntimeException("File rename failed");
        }
    }
}