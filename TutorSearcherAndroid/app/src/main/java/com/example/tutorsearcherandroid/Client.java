package com.example.tutorsearcherandroid;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.text.DecimalFormat;

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
    protected String response = "";
    protected Request returnRequest;
    protected String incomingRequestType;
    protected HashMap<String,Object> incomingAttributes;

    /*
     * Constructor
     */

    public Client() {}
    /*
     * Connect to server
     */

    public void useTestingAddress() {
        this.address = "localhost";
    }

    Client(String incomingRequestType, HashMap<String,Object> incomingAttributes) {
        this.incomingAttributes = incomingAttributes;
        this.incomingRequestType = incomingRequestType;
    }

    public Request getResponse() {
        return returnRequest;
    }

    public void setTypeAndAttr(String incomingRequestType, HashMap<String,Object> incomingAttributes) {
        this.incomingAttributes = incomingAttributes;
        this.incomingRequestType = incomingRequestType;
    }

    public void setAttributes(HashMap<String,Object> incomingAttributes) {
        this.incomingAttributes = incomingAttributes;
    }

    public void setRequestType(String incomingRequestType) {
        this.incomingRequestType = incomingRequestType;
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
//            System.out.println("87");
            //Preprocessing and serialization of data
            Request frontEndData = new Request(incomingRequestType, incomingAttributes);

//            System.out.println("asdas");

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

    static Client initClient(Application app) {
        if(app.getClass().getName().equals("com.example.tutorsearcherandroid.TutorSearcherApp")) {
            return new Client();
        } else {
            return new ClientTest();
        }
    }

    static Client initClient(String incomingRequestType, HashMap<String,Object> incomingAttributes,
                             Application app) {
        if(app.getClass().getName().equals("com.example.tutorsearcherandroid.TutorSearcherApp")) {
            return new Client(incomingRequestType, incomingAttributes);
        } else {
            return new ClientTest(incomingRequestType, incomingAttributes);
        }
    }

    static String round(double num) {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(num);
    }

}
