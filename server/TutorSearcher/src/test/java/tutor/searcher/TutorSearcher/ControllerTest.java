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
    ControllerThread ct = new ControllerThread();

    public ControllerTest() throws InterruptedException {
        // Starting Server
        System.out.println("# Starting Multi-Threaded Controller");
        Thread thread = new Thread(ct);
        thread.start();
        System.out.println("## Starting 2s Delay");
        Thread.sleep(2000);

    }

    @Test
    void SignUpSuccessThread() throws InterruptedException, IOException, ClassNotFoundException {
        // Testing duplicate sign ups
        System.out.println("Testing successful sign ups");
        System.out.println();

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

        // Start Controller and DummyTestClient
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

        // Tests
        assertEquals("Success", response.getRequestType());
        assertEquals("1", response.getAttributes().get("userID"));
    }

    @Test
    void SignUpDuplicate() throws InterruptedException, IOException, ClassNotFoundException {
        // Testing duplicate sign ups
        System.out.println("Testing duplicate sign ups");
        System.out.println();

        // Setting test parameters
        String email = "chunghas@usc.edu";
        String passwordHash = "0CC175B9C0F1B6A831C399E269772661";
        String firstName = "Chungha";
        String lastName = "Kim";
        String phoneNumber = "2135052883";
        Boolean accountType = true;

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        when(dbConnect.addUser(email, passwordHash, firstName, lastName, phoneNumber, accountType)).thenReturn(-1);
        ct.setDbConnect(dbConnect);

        // Start Controller and DummyTestClient
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

        // Tests
        assertEquals("Error: email exists", response.getRequestType());
        assertEquals(null, response.getAttributes().get("userID"));
    }

    @Test
    void UpdateInfoThread() throws InterruptedException, IOException, ClassNotFoundException {
        // Testing duplicate sign ups
        System.out.println("Testing update info");
        System.out.println();

        // Setting test parameters
        int userId = 1;
        String firstName = "Chungha";
        String lastName = "Kim";
        String email = "chunghas@usc.edu";
        String phoneNumber = "2135052883";
        String passwordHash = "0CC175B9C0F1B6A831C399E269772661";
        boolean accountType = true;
        double rating = -1;

        // Preparing Request object parameters
        String requestType = "updateinfo";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        User user = new User(userId, firstName, lastName, email, phoneNumber, accountType, rating);

        // User
        respAttr.put("user", user);
        Request request = new Request(requestType, respAttr);

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        when(dbConnect.addUser(email, passwordHash, firstName, lastName, phoneNumber, accountType)).thenReturn(-1);
        ct.setDbConnect(dbConnect);

        // Start Controller and DummyTestClient
        DummyTestClient dc = new DummyTestClient();

        // Creating Request object
        dc.sendRequest(request);
        Request response = dc.getResponse();

        // Tests
        assertEquals("Success", response.getRequestType());
        assertEquals(null, response.getAttributes().get("userID"));

    }

    class ControllerThread extends Controller implements Runnable {
        public void setDbConnect(DBConnect dbConnect) {
            this.dbConnect = dbConnect;
        }

        @Override
        public void run() {
            startController();
        }
    }

    class DummyTestClient {
        ObjectInputStream ois;
        ObjectOutputStream oos;

        public DummyTestClient() throws IOException {
            System.out.println("# Starting Dummy Test Client");

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
}