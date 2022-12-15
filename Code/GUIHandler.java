package Code;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class GUIHandler extends JFrame {
    //Window Default Sizes
    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 600;
    //Developers
    private final ArrayList<String> developers = new ArrayList<>();
    //Main Window Main Panel
    private static final JPanel mainPanel = new JPanel();

    //Current User
    private static User CurrentUser;
    //Users
    private static ArrayList<User> users;

    //FileHandler- handles all file exporting and importing
    FilesHandler FileHandler = new FilesHandler();

    /**
     * Creates the main window, and add the main panel to the frame.
     * It also adds all the names of the developers into an ArrayList
     * @Author Mark Andrey Rubio
     **/

    GUIHandler(){
        //Adding the Developers of the app
        developers.add("Mark Andrey Rubio - Mark");
        developers.add("Salahuddin Majed - Salahuddin");
        developers.add("Alay Kidane - Alay");
        developers.add("Arshdeep Singh - Singh");

        //adding the main panel to the frame
        getContentPane().add(mainPanel);

        generateSignInPanel();

        //setting the size and visibility of the main window
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setVisible(true);

        //when the window is closed when want to run this method

    }


    /**
     * Generates a sign in panel and checks whether they exist or not already
     * @Author Mark Andrey Rubio
     * */
    public void generateSignInPanel(){
        //remove any pre-existing GUI components
        mainPanel.removeAll();
        users = FileHandler.generateUsersFromPackageOrFolder(new File("Code/AccountFiles"));

        //generate UI components
        var SignInDisplay = new TextField(20);
        SignInDisplay.setPreferredSize(new Dimension(20,50));
        var signInButton = new JButton("User Name");

        mainPanel.setBackground(Color.lightGray);
        mainPanel.add(SignInDisplay);
        mainPanel.add(signInButton);
        System.out.println(users.size());
        signInButton.addActionListener(e -> {
            //check if the user already exists or not
            boolean userExists = false;
            for (User user: users) {
                if (user.getUserName().equals(SignInDisplay.getText())) {
                    System.out.println("User Exists");
                    userExists = true;
                    CurrentUser = user;
                    generateMenuPanel();
                }
            }
            //if the user does not exist then ask them to set their account/user settings
            if (!userExists) {
                //if there is no other users the new user becomes an admin
                if (users.size() == 0) {
                    CurrentUser = new Admin("","");
                }
                //if there are other users then the new user becomes a regular user
                else {
                    CurrentUser = new User("","", false);
                }
                users.add(CurrentUser);
                SetProfilePanel();

            }
        });
    }

    /***
     * Adds all the buttons of the main menu to the main panel and their events.
     * @Author Mark Andrey Rubio
     **/
    public void generateMenuPanel(){
        //remove any pre-existing GUI components
        mainPanel.removeAll();

        //creates the About and Profile Buttons
        var AboutButton = new JButton("About");
        AboutButton.setPreferredSize(new Dimension(100,60));
        AboutButton.setVisible(true);
        var SetProfileButton = new JButton("Profile");
        SetProfileButton.setPreferredSize(new Dimension(100,60));
        SetProfileButton.setVisible(true);
        var ImportButton = new JButton("Import");
        ImportButton.setPreferredSize(new Dimension(100,60));
        ImportButton.setVisible(true);
        var ExportButton = new JButton("Export");
        ExportButton.setPreferredSize(new Dimension(100,60));
        ExportButton.setVisible(true);

        var Filesbutton = new JButton("Files");
        Filesbutton.setPreferredSize(new Dimension(100,60));
        Filesbutton.setVisible(true);



        mainPanel.setBackground(Color.lightGray);
        mainPanel.add(AboutButton);
        mainPanel.add(SetProfileButton);
        mainPanel.add(ImportButton);
        mainPanel.add(ExportButton);
        mainPanel.add(Filesbutton);



        //About Button event
        AboutButton.addActionListener(e -> AboutPanel());
        //Profile Button event
        SetProfileButton.addActionListener(e -> SetProfilePanel());
        ImportButton.addActionListener(e -> ImportPanel());
        ExportButton.addActionListener(e -> ExportPanel());
        Filesbutton.addActionListener(e -> FilesPanel());




        //refresh the GUI/Window
        revalidate();
        repaint();
    }

    /**
     * Resets the main panel and adds the set profile gui components
     * @Author Mark Andrey Rubio
     * **/
    public void SetProfilePanel(){
        //remove any pre-existing GUI components
        mainPanel.removeAll();
        var firstNameDisplay = new TextField(55);
        firstNameDisplay.setPreferredSize(new Dimension(30,50));
        var emailDisplay = new TextField(55);
        emailDisplay.setPreferredSize(new Dimension(20,50));
        var firstNameButton = new JLabel("Set User Name");
        var emailButton = new JLabel("Set Email");
        var signup = new JButton("SignUP");

        var backButton = new JButton("Back");
        mainPanel.setBackground(Color.lightGray);
        mainPanel.add(firstNameButton);
        mainPanel.add(firstNameDisplay);
        mainPanel.add(emailButton);
        mainPanel.add(emailDisplay);
        mainPanel.add(signup);
        mainPanel.add(backButton);

        signup.addActionListener(e -> {
            CurrentUser.setUserName(firstNameDisplay.getText());
            CurrentUser.setEmail(emailDisplay.getText());
            generateMenuPanel();
        });
        backButton.addActionListener(e -> generateMenuPanel());

        //refresh the GUI/Window
        revalidate();
        repaint();
    }

    /**
     * Resets the main panel and adds about panel components
     * @Author Mark Andrey Rubio
     * **/
    public void AboutPanel(){
        //remove any pre-existing GUI components
        mainPanel.removeAll();
        var text = new JTextArea();
        if (CurrentUser.getUserName() == null) {
            text.setText("This is registered to no one\n");
            addDevelopers(text, developers);
        } else {
            text.setText("This app is registered to: " + CurrentUser.getUserName()+ "\n");
            addDevelopers(text, developers);
        }
        var backButton = new JButton("Back");
        mainPanel.setBackground(Color.lightGray);

        mainPanel.add(text);
        mainPanel.add(backButton);
        backButton.addActionListener(e -> generateMenuPanel());

        //refresh the GUI/Window
        revalidate();
        repaint();
    }




    /**
     * Creates a new JFileChooser that imports a .txt file into the account files directory/folder.
     * @author Mark Andrey Rubio
     * @author Arshdeep Singh
     * */
    public void ImportPanel(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Import File");

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION){
            try{
                File copied = new File("Code/AccountFiles/" + fileChooser.getSelectedFile().getName());
                jfile(fileChooser, copied);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * Creates a new JFileChooser that creates a .txt file containing the credentials of the current user to a desired location.
     * @author Mark Andrey Rubio
     * @author Arshdeep Singh
     * @author Alay Kidane
     * */
    public void ExportPanel(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export File");
        fileChooser.setCurrentDirectory(new File("Code/AccountFiles"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showSaveDialog(this);
        new File(String.valueOf(fileChooser.getSelectedFile()));
        files2(fileChooser, result);

    }



    /**
     * Creates a new JFileChooser that export a file user previously uploaded to the users home directory location.
     * @author Arshdeep Singh
     * @author Alay Kidane
     * */
    public void ExportP(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export File");
        fileChooser.setCurrentDirectory(new File("Code/Files"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(this);
        files2(fileChooser, result);

    }



    /**
     * Creates a JFileChooser that will import a file.
     /**
     * Creates a file panel that displays the file list.
     * @author Arshdeep Singh
     * */
    public void FilesPanel() {
        mainPanel.removeAll();
        ArrayList<String> FileList = new ArrayList<>();

        var text = new JTextArea();
        try {

            // Create a file object
            File f = new File("Code/Files");

            // Get all the names of the files present
            // in the given directory
            String[] files = f.list();
            text.setPreferredSize(new Dimension(200,300));
            text.setLineWrap(true);

            // Display the names of the files
            assert files != null;
            Collections.addAll(FileList, files);
            for (String text1: FileList) {
                text.append(text1 + "\n");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        var backButton = new JButton("Back");
        var Import = new JButton("Upload Files");
        var Export = new JButton("Export Files");
        mainPanel.setBackground(Color.lightGray);
        mainPanel.add(text);
        mainPanel.add(Export);
        mainPanel.add(Import);
        mainPanel.add(backButton);
        backButton.addActionListener(e -> generateMenuPanel());
        Import.addActionListener(e -> UploadPanel());
        Export.addActionListener(e -> ExportP());
        //refresh the GUI/Window
        revalidate();
        repaint();
    }


    /**
     * Creates a JFileChooser that will import a file.
     /**
     * Creates a new JFileChooser that can take in pdf and save into the file directory of the program to be displayed as file list.
     * @author Arshdeep Singh
     * @Alay
     * */
    public void UploadPanel(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Upload File");

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION){
            try{
                File copied = new File("Code/Files/" + fileChooser.getSelectedFile().getName());
                try (
                        InputStream in = new BufferedInputStream(
                                new FileInputStream(fileChooser.getSelectedFile()));
                        OutputStream out = new BufferedOutputStream(
                                new FileOutputStream(copied))) {

                    byte[] buffer = new byte[1024];
                    int lengthRead;
                    while ((lengthRead = in.read(buffer)) > 0) {
                        out.write(buffer, 0, lengthRead);
                        out.flush();
                    }
                    FilesPanel();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * A helper method to append the names of the developers to a JTextArea
     * @author Mark Andrey Rubio
     * **/
    private void addDevelopers(JTextArea textArea, ArrayList<String> developers){
        textArea.append("This app is provided by: \n");
        for (String text: developers) {
            textArea.append(text + "\n");
        }
    }

    /**
    Helper method to deal with jfiles
    this decrease the reduandecy in the code because
     this method have two uses
     @author Arshdeep and ALay

    **/
    private void jfile(JFileChooser fileChooser, File copied) throws IOException {
        try (
                InputStream in = new BufferedInputStream(
                        new FileInputStream(fileChooser.getSelectedFile()));
                OutputStream out = new BufferedOutputStream(
                        new FileOutputStream(copied))) {

            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        }
        mainPanel.setBackground(Color.lightGray);

    }


    /**
     Helper method to deal with jfiles
     this decrease the reduandecy in the code because
     this method have two uses
     @author Alay and Arshdeep
     **/
    private void files2(JFileChooser fileChooser, int result) {
        FileSystemView view = FileSystemView.getFileSystemView();
        File file = view.getHomeDirectory();
        String path = file.getPath();
        if (result == JFileChooser.APPROVE_OPTION){
            try{
                File copied = new File( path  + "/" + fileChooser.getSelectedFile().getName());
                jfile(fileChooser, copied);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mainPanel.setBackground(Color.lightGray);
    }
}

