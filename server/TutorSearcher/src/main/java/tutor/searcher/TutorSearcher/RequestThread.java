package tutor.searcher.TutorSearcher;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestThread extends Thread {
    private Controller controller;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private boolean end = false;

    public RequestThread(Socket s, Controller controller) {

        System.out.println("RequestThread created.");

        this.controller = controller;

        try {
            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());
        } catch (IOException e) { }

        this.start();
    }

    public void run() {
        while(!end) {
            try {
                Request req = (Request) ois.readObject();
//                System.out.print("Run at 33");
                controller.processRequest(req, this);
            } catch (IOException e) {
                // This exception get's thrown a lot.
                // e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendResponse(Request req) {
        try {
            oos.writeObject(req);
            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            end = true;
        }
    }
}
