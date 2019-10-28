package tutor.searcher.TutorSearcher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class DummyClient extends Thread {

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private boolean end = false;

    DummyClient() {

        Socket s = null;

        System.out.println("asdads");

        try {
            s = new Socket("localhost", 6789);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ois = new ObjectInputStream(s.getInputStream());
            oos = new ObjectOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.start();
    }

    public void dummyRequest() {
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("hello", "world");

        Request req = new Request("testing", attributes);
        sendRequest(req);
    }

    public void run() {
        while (!end) {
            Request res;
            try {
                res = (Request) ois.readObject();
                processResponse(res);
            } catch (ClassNotFoundException e) {
                if (!end) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                if (!end) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Thank you for playing Hangman!");
        System.exit(0);
    }

    private void sendRequest(Request req) {
        try {
            oos.writeObject(req);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processResponse(Request req) {
        System.out.println("Printed Something");
        System.out.println(req.getRequestType());
    }

    public static void main(String[] args) {
        DummyClient dc = new DummyClient();
        dc.dummyRequest();
    }
}
