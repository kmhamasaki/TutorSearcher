package tutor.searcher.TutorSearcher;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import static org.mockito.Mockito.*;

@SpringBootTest
class ControllerTest {
    public ControllerTest() {
    }

    @Test
    void SignUpSuccessThread() throws InterruptedException, IOException, ClassNotFoundException {
        // Testing duplicate sign ups
        System.out.println("Testing successful sign ups");
        System.out.println();

        // Creating new ConnectionThread
        ControllerThread ct = new ControllerThread();
        // Setting test parameters
        String email = "chunghas@usc.edu";
        String passwordHash = "0CC175B9C0F1B6A831C399E269772661";
        String firstName = "Chungha";
        String lastName = "Kim";
        String phoneNumber = "2135052883";
        Boolean accountType = true;

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        when(dbConnect.addUser(email, passwordHash, firstName, lastName, phoneNumber, accountType)).thenReturn(1);
        ct.setDbConnect(dbConnect);

        // Starting Server
        Thread thread = new Thread(ct);
        thread.start();
        Thread.sleep(2000);

        // Client connect
        DummyTestClient dc = new DummyTestClient();

        // Preparing Request object parameters
        String requestType = "signup";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("email", email);
        respAttr.put("passwordHash", passwordHash);
        respAttr.put("firstName", firstName);
        respAttr.put("lastName", lastName);
        respAttr.put("phoneNumber", phoneNumber);
        respAttr.put("accountType", accountType);

        // Creating Request object
        Request request = new Request(requestType, respAttr);
        dc.sendRequest(request);
        Request response = dc.getResponse();
        System.out.println("ASDSADAS");
        System.out.println(response.getRequestType());
    }

    @Test
    void SignUpSuccess() {
        // Testing duplicate sign ups
        System.out.println("Testing successful sign ups");

        // Instantiating controller
        Controller c = new Controller();

        // Setting test parameters
        String email = "chunghas@usc.edu";
        String passwordHash = "0CC175B9C0F1B6A831C399E269772661";
        String firstName = "Chungha";
        String lastName = "Kim";
        String phoneNumber = "2135052883";
        Boolean accountType = true;

        // Preparing Request object parameters
        String requestType = "signup";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("email", email);
        respAttr.put("passwordHash", passwordHash);
        respAttr.put("firstName", firstName);
        respAttr.put("lastName", lastName);
        respAttr.put("phoneNumber", phoneNumber);
        respAttr.put("accountType", accountType);

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        when(dbConnect.addUser(email, passwordHash, firstName, lastName, phoneNumber, accountType)).thenReturn(1);
//        c.setDbConnect(dbConnect);

        // Creating Request object
        Request request = new Request(requestType, respAttr);

        // Mocking RequestThread object
        RequestThread requestThread = mock(RequestThread.class);

        // Process Request
        Request response = c.processRequest(request, requestThread);

        // Tests
        assertEquals("Success", response.getRequestType());
        assertEquals("1", response.getAttributes().get("userID"));
    }

    @Test
    void SignUpDuplicate() {
        // Testing duplicate sign ups
        System.out.println("Testing duplicate sign ups");

        // Instantiating controller
        Controller c = new Controller();

        // Setting test parameters
        String email = "chunghas@usc.edu";
        String passwordHash = "0CC175B9C0F1B6A831C399E269772661";
        String firstName = "Chungha";
        String lastName = "Kim";
        String phoneNumber = "2135052883";
        Boolean accountType = true;

        // Preparing Request object parameters
        String requestType = "signup";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("email", email);
        respAttr.put("passwordHash", passwordHash);
        respAttr.put("firstName", firstName);
        respAttr.put("lastName", lastName);
        respAttr.put("phoneNumber", phoneNumber);
        respAttr.put("accountType", accountType);

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        when(dbConnect.addUser(email, passwordHash, firstName, lastName, phoneNumber, accountType)).thenReturn(-1);
//        c.setDbConnect(dbConnect);

        // Creating Request object
        Request request = new Request(requestType, respAttr);

        // Mocking RequestThread object
        RequestThread requestThread = mock(RequestThread.class);
        c.processRequest(request, requestThread);

        System.out.println(request.getRequestType());

        // Process Request
        Request response = c.processRequest(request, requestThread);

        // Tests
        assertEquals("Error: email exists", response.getRequestType());
        assertEquals(null, response.getAttributes().get("userID"));
    }

}

class ControllerThread extends Controller implements Runnable {
    public void setDbConnect(DBConnect dbConnect) {
        this.dbConnect = dbConnect;
    }

    @Override
    public void run() {
        System.out.println("# Starting Multi-Threaded Controller");
        startController();
    }
}

class DummyTestClient {
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public DummyTestClient() throws IOException {
        System.out.println("# Generating Dummy Client Socket");

        Socket s = new Socket("localhost", 6789);
        ois = new ObjectInputStream(s.getInputStream());
        oos = new ObjectOutputStream(s.getOutputStream());
    }

    public void sendRequest(Request request) throws IOException {
        oos.writeObject(request);
        oos.flush();
    }

    public Request getResponse() throws IOException, ClassNotFoundException {
        Request res = (Request) ois.readObject();
        return res;
    }
}