package tutor.searcher.TutorSearcher;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.AfterTestMethod;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class ControllerTest {
//
//    public ControllerTest() throws InterruptedException {
//
//    }

    public ControllerThread generateControllerThread() throws InterruptedException {
        ControllerThread ct = new ControllerThread();

        // Starting Server
        System.out.println("# Starting Multi-Threaded Controller");
        Thread thread = new Thread(ct);
        thread.start();
//        System.out.println("## Starting 2s Delay");
//        Thread.sleep(2000);

        return ct;
    }

    public void closeServer(ControllerThread ct) throws IOException {
        System.out.println("# Closing Multi-Threaded Controller");
//        ct.closeServer();
        ct = null;
    }

    @Test
    void InvalidRequestTest() throws InterruptedException, IOException, ClassNotFoundException {
        ControllerThread ct = generateControllerThread();

        // Testing duplicate sign ups
        System.out.println("Testing invalid request");
        System.out.println();

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        ct.setDbConnect(dbConnect);

        // Start Controller and DummyTestClient
        DummyTestClient dc = new DummyTestClient();

        // Preparing Request object parameters
        String requestType = "invalid";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();

        // Creating Request object
        Request request = new Request(requestType, respAttr);
        dc.sendRequest(request);
        Request response = dc.getResponse();
        closeServer(ct);
        Thread.sleep(2000);

        // Tests
        assertEquals("", response.getRequestType());
    }

    @Test
    void SignUpSuccessTest() throws InterruptedException, IOException, ClassNotFoundException {
        ControllerThread ct = generateControllerThread();

        // Testing duplicate sign ups
        System.out.println("Testing successful sign ups");
        System.out.println();

        // Setting test parameters
        String email = "alex@usc.edu";
        String passwordHash = "0CC175B9C0F1B6A831C399E269772661";
        String firstName = "Alex";
        String lastName = "Kim";
        String phoneNumber = "2135052883";
        Boolean accountType = true;

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        when(dbConnect.addUser(email, passwordHash, firstName, lastName, phoneNumber, accountType, "")).thenReturn(1);
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
        closeServer(ct);
        Thread.sleep(2000);

        // Tests
        assertEquals("Success", response.getRequestType());
//        assertEquals("1", response.getAttributes().get("userID"));
    }

    @Test
    void SignUpDuplicateTest() throws InterruptedException, IOException, ClassNotFoundException {
        ControllerThread ct = generateControllerThread();

        // Testing duplicate sign ups
        System.out.println("Testing duplicate sign ups");
        System.out.println();

        // Setting test parameters
        String email = "alex@usc.edu";
        String passwordHash = "0CC175B9C0F1B6A831C399E269772661";
        String firstName = "Alex";
        String lastName = "Kim";
        String phoneNumber = "2135052883";
        Boolean accountType = true;

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        when(dbConnect.addUser(email, passwordHash, firstName, lastName, phoneNumber, accountType, "")).thenReturn(-1);
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
        closeServer(ct);
        Thread.sleep(2000);

        // Tests
//        assertEquals("Error: email exists", response.getRequestType());
        assertEquals("0", response.getAttributes().get("userID"));
    }

    @Test
    void UpdateInfoThreadTest() throws InterruptedException, IOException, ClassNotFoundException {
        ControllerThread ct = generateControllerThread();

        // Testing duplicate sign ups
        System.out.println("Testing update info");
        System.out.println();

        // Setting test parameters
        int userId = 1;
        String firstName = "Alex";
        String lastName = "Kim";
        String email = "123";
        String phoneNumber = "1112223333";
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
        String bio = "";
        when(dbConnect.addUser(email, passwordHash, firstName, lastName, phoneNumber, accountType, bio)).thenReturn(-1);
        ct.setDbConnect(dbConnect);

        // Start Controller and DummyTestClient
        DummyTestClient dc = new DummyTestClient();

        // Creating Request object
        dc.sendRequest(request);
        Request response = dc.getResponse();
        closeServer(ct);
        Thread.sleep(2000);

        // Tests
        assertEquals("Success", response.getRequestType());
        assertEquals(null, response.getAttributes().get("userID"));
    }

    @Test
    void SearchTest() throws InterruptedException, IOException, ClassNotFoundException {
        ControllerThread ct = generateControllerThread();

        // Tests
        System.out.println("Testing search");
        System.out.println();

        // Setting test parameters
        int userId = 1;
        String className = "CSCI 104";
        ArrayList<Integer> times = new ArrayList<Integer>();
        times.add(0);
        times.add(1);
        times.add(2);
        List<Tutor> tutors = new ArrayList<Tutor>();

        // Preparing Request object parameters
        String requestType = "search";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("userID", userId);
        respAttr.put("availability", times);
        respAttr.put("className", className);

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        when(dbConnect.searchTutors(userId, times, className)).thenReturn(tutors);
        ct.setDbConnect(dbConnect);

        // Start Controller and DummyTestClient
        DummyTestClient dc = new DummyTestClient();

        // Creating Request object
        Request request = new Request(requestType, respAttr);
        dc.sendRequest(request);
        Request response = dc.getResponse();
        closeServer(ct);
        Thread.sleep(2000);

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    void NewRequestTest() throws InterruptedException, IOException, ClassNotFoundException {
        ControllerThread ct = generateControllerThread();

        // Tests
        System.out.println("Testing new request");
        System.out.println();

        // Setting test parameters
        int tuteeID = 1;
        int tutorID = 2;
        String className = "CSCI 104";
        String time = "1300";

        // Preparing Request object parameters
        String requestType = "newrequest";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("tuteeID", tuteeID);
        respAttr.put("tutorID", tutorID);
        respAttr.put("className", className);
        respAttr.put("time", time);

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        when(dbConnect.addRequest(tuteeID, tutorID, className, time, 0)).thenReturn(0);
        ct.setDbConnect(dbConnect);

        // Start Controller and DummyTestClient
        DummyTestClient dc = new DummyTestClient();

        // Creating Request object
        Request request = new Request(requestType, respAttr);
        dc.sendRequest(request);
        Request response = dc.getResponse();
        closeServer(ct);
        Thread.sleep(2000);

        // Tests
        assertEquals("Success", response.getRequestType());
        assertEquals(0, response.get("requestID"));
    }

    @Test
    void ViewRequestsTest() throws InterruptedException, IOException, ClassNotFoundException {
        ControllerThread ct = generateControllerThread();

        // Tests
        System.out.println("Testing view requests");
        System.out.println();

        // Setting test parameters
        int userID = 1;
        String viewrequeststype = "tuteeapproved";

        // Preparing Request object parameters
        String requestType = "viewrequests";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("userID", userID);
        respAttr.put("viewrequeststype", viewrequeststype);

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        when(dbConnect.getRequestsTuteeApproved(userID)).thenReturn(null);
        ct.setDbConnect(dbConnect);

        // Start Controller and DummyTestClient
        DummyTestClient dc = new DummyTestClient();

        // Creating Request object
        Request request = new Request(requestType, respAttr);
        dc.sendRequest(request);
        Request response = dc.getResponse();
        closeServer(ct);
        Thread.sleep(2000);

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    void UpdateRequestStatusTest() throws InterruptedException, IOException, ClassNotFoundException {
        ControllerThread ct = generateControllerThread();

        // Tests
        System.out.println("Testing update request status");
        System.out.println();

        // Setting test parameters
        int requestID = 1;
        int newStatus = 2;

        // Preparing Request object parameters
        String requestType = "updaterequeststatus";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("requestID", requestID);
        respAttr.put("newStatus", newStatus);

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        when(dbConnect.updateRequestStatus(requestID, newStatus)).thenReturn(true);
        ct.setDbConnect(dbConnect);

        // Start Controller and DummyTestClient
        DummyTestClient dc = new DummyTestClient();

        // Creating Request object
        Request request = new Request(requestType, respAttr);
        dc.sendRequest(request);
        Request response = dc.getResponse();
        closeServer(ct);
        Thread.sleep(2000);

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    void UpdateAvailabilityTest() throws InterruptedException, IOException, ClassNotFoundException {
        ControllerThread ct = generateControllerThread();

        // Tests
        System.out.println("Testing update availability");
        System.out.println();

        // Setting test parameters
        int tutorID = 1;
        List<Integer> availability = new ArrayList<Integer>();

        // Preparing Request object parameters
        String requestType = "updateavailability";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("tutorID", tutorID);
        respAttr.put("availability", availability);

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
//        when(dbConnect.updateTutorAvailability(tutorID, availability)).thenReturn(true);
        ct.setDbConnect(dbConnect);

        // Start Controller and DummyTestClient
        DummyTestClient dc = new DummyTestClient();

        // Creating Request object
        Request request = new Request(requestType, respAttr);
        dc.sendRequest(request);
        Request response = dc.getResponse();
        closeServer(ct);
        Thread.sleep(2000);

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    void AddClassTest() throws InterruptedException, IOException, ClassNotFoundException {
        ControllerThread ct = generateControllerThread();

        // Tests
        System.out.println("Testing add class");
        System.out.println();

        // Setting test parameters
        int tutorID = 1;
        ArrayList<String> className = new ArrayList<>();

        // Preparing Request object parameters
        String requestType = "addclass";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("tutorID", tutorID);
        respAttr.put("className", className);

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        ct.setDbConnect(dbConnect);
        Thread.sleep(2000);

        // Start Controller and DummyTestClient
        DummyTestClient dc = new DummyTestClient();

        // Creating Request object
        Request request = new Request(requestType, respAttr);
        dc.sendRequest(request);
        Request response = dc.getResponse();
        closeServer(ct);

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    void RemoveClassTest() throws InterruptedException, IOException, ClassNotFoundException {
        ControllerThread ct = generateControllerThread();

        // Tests
        System.out.println("Testing remove class");
        System.out.println();

        // Setting test parameters
        int tutorID = 1;
        String className = "CSCI 104";

        // Preparing Request object parameters
        String requestType = "removeclass";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("tutorID", tutorID);
        respAttr.put("className", className);

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
//        when(dbConnect.removeTutorFromClass(tutorID, className)).return(null);
        ct.setDbConnect(dbConnect);
        Thread.sleep(2000);

        // Start Controller and DummyTestClient
        DummyTestClient dc = new DummyTestClient();

        // Creating Request object
        Request request = new Request(requestType, respAttr);
        dc.sendRequest(request);
        Request response = dc.getResponse();
        closeServer(ct);

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    void GetAvailabilityTest() throws InterruptedException, IOException, ClassNotFoundException {
        ControllerThread ct = generateControllerThread();

        // Tests
        System.out.println("Testing get availability");
        System.out.println();

        // Setting test parameters
        int tutorID = 1;
        ArrayList<Integer> availability = new ArrayList<Integer>();
        availability.add(1);
        availability.add(2);
        availability.add(3);

        // Preparing Request object parameters
        String requestType = "getavailability";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("tutorID", tutorID);
        respAttr.put("availability", availability);

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        ct.setDbConnect(dbConnect);
        Thread.sleep(2000);

        // Start Controller and DummyTestClient
        DummyTestClient dc = new DummyTestClient();

        // Creating Request object
        Request request = new Request(requestType, respAttr);
        dc.sendRequest(request);
        Request response = dc.getResponse();
        closeServer(ct);

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    void GetClassesTest() throws InterruptedException, IOException, ClassNotFoundException {
        ControllerThread ct = generateControllerThread();

        // Tests
        System.out.println("Testing get classes");
        System.out.println();

        // Setting test parameters
        int tutorID = 1;

        // Preparing Request object parameters
        String requestType = "getclasses";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("tutorID", tutorID);

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        when(dbConnect.getTutorClasses(tutorID)).thenReturn(null);
        ct.setDbConnect(dbConnect);
        Thread.sleep(2000);

        // Start Controller and DummyTestClient
        DummyTestClient dc = new DummyTestClient();

        // Creating Request object
        Request request = new Request(requestType, respAttr);
        dc.sendRequest(request);
        Request response = dc.getResponse();

        // Tests
        assertEquals("Success", response.getRequestType());
        closeServer(ct);
    }

    @Test
    void SearchPreviousTest() throws InterruptedException, IOException, ClassNotFoundException {
        ControllerThread ct = generateControllerThread();

        // Tests
        System.out.println("Testing search previous");
        System.out.println();

        // Setting test parameters
        int userID = 1;

        // Preparing Request object parameters
        String requestType = "searchprevious";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("userID", userID);

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        when(dbConnect.searchTutorsPrevious(userID)).thenReturn(null);
        ct.setDbConnect(dbConnect);
        Thread.sleep(2000);

        // Start Controller and DummyTestClient
        DummyTestClient dc = new DummyTestClient();

        // Creating Request object
        Request request = new Request(requestType, respAttr);
        dc.sendRequest(request);
        Request response = dc.getResponse();

        // Tests
        assertEquals("Success", response.getRequestType());
        closeServer(ct);
    }

    @Test
    void GetUserInfoTest() throws InterruptedException, IOException, ClassNotFoundException {
        ControllerThread ct = generateControllerThread();

        // Tests
        System.out.println("Testing get user info");
        System.out.println();

        // Setting test parameters
        int userId = 1;

        // Preparing Request object parameters
        String requestType = "getuserinfo";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("userID", userId);

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        when(dbConnect.getUserInformation(userId)).thenReturn(null);
        ct.setDbConnect(dbConnect);
        Thread.sleep(2000);

        // Start Controller and DummyTestClient
        DummyTestClient dc = new DummyTestClient();

        // Creating Request object
        Request request = new Request(requestType, respAttr);
        dc.sendRequest(request);
        Request response = dc.getResponse();
        closeServer(ct);

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    void AddRatingTest() throws InterruptedException, IOException, ClassNotFoundException {
        ControllerThread ct = generateControllerThread();

        // Tests
        System.out.println("Testing add rating");
        System.out.println();

        // Setting test parameters
        int userID = 1;
        double rating = 1;

        // Preparing Request object parameters
        String requestType = "addrating";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("userID", userID);
        respAttr.put("rating", rating);

        // DBConnect mocks
        DBConnect dbConnect = mock(DBConnect.class);
        ct.setDbConnect(dbConnect);
        Thread.sleep(2000);

        // Start Controller and DummyTestClient
        DummyTestClient dc = new DummyTestClient();

        // Creating Request object
        Request request = new Request(requestType, respAttr);
        dc.sendRequest(request);
        Request response = dc.getResponse();
        closeServer(ct);

        // Tests
        assertEquals("Success", response.getRequestType());
    }
}


class ControllerThread extends Controller implements Runnable {
    public synchronized void setDbConnect(DBConnect dbConnect) {
        this.dbConnect = dbConnect;
    }
    @Override
    public void run() {
        useTestingPort();
        startController();
    }
}

class DummyTestClient {
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public DummyTestClient() throws IOException {
        System.out.println("# Starting Dummy Test Client");

        Socket s = new Socket("localhost", 6780);
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