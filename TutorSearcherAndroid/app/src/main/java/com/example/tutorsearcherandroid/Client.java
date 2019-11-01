package com.example.tutorsearcherandroid;

import android.widget.TextView;
import java.io.IOException;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import android.os.AsyncTask;
import tutor.searcher.TutorSearcher.Request;

public class Client extends AsyncTask<Void, Void, Void> {

    /*
     * Class Variables
     */
    String address = "10.0.2.2";
    int port = 6789;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private TextView textResponse;
    private String response = "";
    private Request returnRequest;
    private String incomingRequestType;
    private HashMap<String,Object> incomingAttributes;

    /*
     * Constructor
     */

    Client() {}
    /*
     * Connect to server
     */

    Client(String incomingRequestType, HashMap<String,Object> incomingAttributes) {
        this.incomingAttributes = incomingAttributes;
        this.incomingRequestType = incomingRequestType;
    }

    public Request getResponse() {
        return returnRequest;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        System.out.println("Client.doInBackground");
        Socket socket = null;
        Request serverData = null;
        try {
            socket = new Socket(address, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("87");
            //Preprocessing and serialization of data
            Request frontEndData = new Request(incomingRequestType, incomingAttributes);
            oos.writeObject(frontEndData);

            oos.flush();

            // Wait for server response
            serverData = (Request) ois.readObject();

            //Process Request
            response += serverData.getRequestType();
            System.out.println(response);
        }
        catch (Exception e) {
            e.getCause();
            e.printStackTrace();
        }
        finally {
            if (socket != null) {
                try {
                    //Close socket and object streams
                    socket.close();
                    oos.close();
                    ois.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        returnRequest = serverData;
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        //textResponse.setText(response);
        //super.onPostExecute(result);
    }


}
