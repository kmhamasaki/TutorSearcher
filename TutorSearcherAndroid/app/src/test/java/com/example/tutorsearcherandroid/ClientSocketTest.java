package com.example.tutorsearcherandroid;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.Tutor;
import tutor.searcher.TutorSearcher.User;

import static org.junit.Assert.assertEquals;

public class ClientSocketTest {

    @Test
    public void InvalidRequest() {
        // Testing duplicate sign ups
        System.out.println("Testing invalid request");
        System.out.println();

        // Preparing Request object parameters
        String requestType = "invalid";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();

        // Creating Request object
        Client client = new Client(requestType, respAttr);
        client.useTestingAddress();
        client.doInBackground();
        Request response = client.getResponse();

        // Tests
        assertEquals("", response.getRequestType());
    }

    @Test
    public void SignUpSuccess() {
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
        Client client = new Client(requestType, respAttr);
        client.useTestingAddress();
        client.doInBackground();
        Request response = client.getResponse();

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    public void SignUpDuplicate() {
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
        Client client = new Client(requestType, respAttr);
        client.useTestingAddress();
        client.doInBackground();
        Request response = client.getResponse();

        // Tests
        assertEquals("Error: email exists", response.getRequestType());
    }

    @Test
    public void UpdateInfo() {
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
        respAttr.put("user", user);

        // Creating Request object
        Client client = new Client(requestType, respAttr);
        client.useTestingAddress();
        client.doInBackground();
        Request response = client.getResponse();

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    public void Search() {
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

        // Creating Request object
        Client client = new Client(requestType, respAttr);
        client.useTestingAddress();
        client.doInBackground();
        Request response = client.getResponse();

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    public void NewRequest() {
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

        // Creating Request object
        Client client = new Client(requestType, respAttr);
        client.useTestingAddress();
        client.doInBackground();
        Request response = client.getResponse();

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    public void ViewRequests() {
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

        // Creating Request object
        Client client = new Client(requestType, respAttr);
        client.useTestingAddress();
        client.doInBackground();
        Request response = client.getResponse();

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    public void UpdateRequestStatus() {
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

        // Creating Request object
        Client client = new Client(requestType, respAttr);
        client.useTestingAddress();
        client.doInBackground();
        Request response = client.getResponse();

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    public void UpdateAvailability() {
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

        // Creating Request object
        Client client = new Client(requestType, respAttr);
        client.useTestingAddress();
        client.doInBackground();
        Request response = client.getResponse();

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    public void AddClass() {
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

        // Creating Request object
        Client client = new Client(requestType, respAttr);
        client.useTestingAddress();
        client.doInBackground();
        Request response = client.getResponse();

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    public void RemoveClass() {
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

        // Creating Request object
        Client client = new Client(requestType, respAttr);
        client.useTestingAddress();
        client.doInBackground();
        Request response = client.getResponse();

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    public void GetAvailability() {
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

        // Creating Request object
        Client client = new Client(requestType, respAttr);
        client.useTestingAddress();
        client.doInBackground();
        Request response = client.getResponse();

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    public void GetClasses() {
        // Tests
        System.out.println("Testing get classes");
        System.out.println();

        // Setting test parameters
        int tutorID = 1;

        // Preparing Request object parameters
        String requestType = "getclasses";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("tutorID", tutorID);

        // Creating Request object
        Client client = new Client(requestType, respAttr);
        client.useTestingAddress();
        client.doInBackground();
        Request response = client.getResponse();

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    public void SearchPrevious() {
        // Tests
        System.out.println("Testing search previous");
        System.out.println();

        // Setting test parameters
        int userID = 1;

        // Preparing Request object parameters
        String requestType = "searchprevious";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("userID", userID);

        // Creating Request object
        Client client = new Client(requestType, respAttr);
        client.useTestingAddress();
        client.doInBackground();
        Request response = client.getResponse();

        // Tests
        assertEquals("Success", response.getRequestType());
    }

    @Test
    public void GetUserInfo() {
        // Tests
        System.out.println("Testing get user info");
        System.out.println();

        // Setting test parameters
        int userId = 1;

        // Preparing Request object parameters
        String requestType = "getuserinfo";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();
        respAttr.put("userID", userId);

        // Creating Request object
        Client client = new Client(requestType, respAttr);
        client.useTestingAddress();
        client.doInBackground();
        Request response = client.getResponse();

        // Tests
        assertEquals("Success", response.getRequestType());
    }

}