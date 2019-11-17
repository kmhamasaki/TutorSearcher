package tutor.searcher.TutorSearcher;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.Socket;
import java.util.HashMap;

import static org.mockito.Mockito.*;

@SpringBootTest
class ControllerTest {
    public ControllerTest() {

    }

    @Test
    void processRequestTest_Signup() {
        System.out.println("Testing successful signup");

        String email = "chunghas@usc.edu";
        String passwordHash = "0CC175B9C0F1B6A831C399E269772661";
        String firstName = "Chungha";
        String lastName = "Kim";
        String phoneNumber = "2135052883";
        Boolean accountType = true;

        Controller c = new Controller();

        DBConnect dbConnect = mock(DBConnect.class);
        when(dbConnect.addUser(email, passwordHash, firstName, lastName, phoneNumber, accountType)).thenReturn(1);

        c.setDbConnect(dbConnect);

        Socket s = new Socket();

        String requestType = "signup";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("email", email);
        respAttr.put("passwordHash", passwordHash);
        respAttr.put("firstName", firstName);
        respAttr.put("lastName", lastName);
        respAttr.put("phoneNumber", phoneNumber);
        respAttr.put("accountType", accountType);

        Request request = new Request(requestType, respAttr);
        RequestThread requestThread = mock(RequestThread.class);

        Request response = c.processRequest(request, requestThread);
        assertEquals("Success", response.getRequestType());
    }
}
