package Code;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;

class GUIHandlerTest {
    //********TESTING ADMIN AND USER Classes
    /**
     * Testing if a user is generated with the right properties.
     * @author Mark Andrey Rubio
     * */
    @Test
    void createUser(){
        User newUser = new User("TestUser@email.test", "TestUser2", false);
        assertEquals("TestUser@email.test", newUser.getEmail());
        assertEquals("TestUser2", newUser.getUserName());
        assertEquals(false, newUser.isAdmin());
    }

    //*******TESTING FilesHandler Class
    /**
     * Testing if File user Generates an actual File
     * @author Mark Andrey Rubio
     * */
    @Test
    void generateFileUser(){
        //NOTE: WILL RESULT IN AN ERROR IF THE FILE ALREADY EXISTS!  THIS IS TESTING WHETHER IT CREATES ONE, NOT IF IT ALREADY EXISTS.
        FilesHandler filesHandler = new FilesHandler();
        User newUser = new User("Test@uw.edu", "TestUser",false);
        assertTrue(filesHandler.generateNewFileUser(newUser) instanceof File);
    }

    /**
     * Testing if a list of users is generated from a folder.
     * @author Mark Andrey Rubio
     * */
    @Test
    void generateUsersFromExistingUsersInAPackage(){
        FilesHandler filesHandler = new FilesHandler();
        assertTrue(filesHandler.generateUsersFromPackageOrFolder(new File("Code/AccountFiles")) instanceof ArrayList<User>);
    }
}