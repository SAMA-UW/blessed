package Code;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FilesHandler {
    private Scanner fileReader;
    private ArrayList<File> files;
    private ArrayList<Package> categories;


    /**
     * Creates a list of users from pre-existing users.
     * @param folder a folder containing .txt files that has information necessary to create a new user
     * @return an ArrayList of pre-existing users of type User
     * @Author Mark Andrey Rubio
     */
    public ArrayList<User> generateUsersFromPackageOrFolder(File folder){
        ArrayList<User> users = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            try {
                User newUser;
                String name;
                String email;

                fileReader = new Scanner(fileEntry);
                while (fileReader.hasNext()) {
                    String word = fileReader.next();
                    if (word.equals("UserName:")) {
                        name = fileReader.next();
                        if(fileReader.next().equals("Email:")){
                            email = fileReader.next();
                            newUser = new User(email, name, false);
                            users.add(newUser);
                        }
                    } else System.out.println("File Does not Contain A User or Syntax is Wrong! FileName: " + fileEntry.getName());
                }
                fileReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return users;
    }


    /**
     * Creates .txt files for all users of the application.
     * @param users an ArrayList containing User objects.
     * @Author Mark Andrey Rubio
     */
    public void generateUserFilesAfterWindowClosed(ArrayList<User> users){
        for (User user: users) {
            generateNewFileUser(user);
        }
    }

    /**
     * A private helper method used to create new .txt files and parent it to the AccountFiles directory/folder
     * @param user a user
     * @Author Mark Andrey Rubio
     */
    public File generateNewFileUser(User user){
        try {
            File newFile = new File("Code/AccountFiles/"+user.getUserName() + ".txt");
            if (newFile.createNewFile()){
                FileWriter myWriter = new FileWriter(newFile);
                myWriter.write("UserName: " + user.getUserName() + " Email: " + user.getEmail());
                myWriter.close();
                System.out.println("File Created: " + newFile.getName());
                return newFile;
            } else {
                System.out.println("User: " + user.getUserName() + ". Already exists!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
