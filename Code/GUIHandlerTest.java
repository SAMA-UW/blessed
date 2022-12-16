package Code;

import static org.junit.jupiter.api.Assertions.*;

class GUIHandlerTest {

    @org.junit.jupiter.api.Test
    void generateMenuPanel() {
    }

    @org.junit.jupiter.api.Test
    void setProfilePanel() {
    }

    @org.junit.jupiter.api.Test
    void aboutPanel() {
    }
    /* Admin Test
    *  @author Salahuddin Majed
    */
    @org.junit.jupiter.api.Test
    void AdminTest () {
         User newUser = new Admin ("salahuddinmajed@yahoo.com", "smajed");
         assertEquals(true, newUser.isAdmin());
    }
}
