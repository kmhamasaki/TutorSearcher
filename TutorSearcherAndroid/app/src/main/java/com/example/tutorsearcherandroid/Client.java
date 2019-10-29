package com.example.tutorsearcherandroid;

import android.widget.TextView;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
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

    /*
     * Constructor
     */
    Client(TextView textResponse){
        this.textResponse = textResponse;
    }

    /*
     * Connect to server
     */
    @Override
    protected Void doInBackground(Void... arg0) {

        System.out.println("In doInBackground");
        Socket socket = null;

        try {
            //Initialize socket and object streams
            socket = new Socket(address, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            //Preprocessing and serialization of data
            HashMap<String, String> userInputs = new HashMap<>();
            userInputs.put("email","haseyama@usc.edu");
            userInputs.put("password","password");
            Request frontEndData = new Request("login",userInputs);
            oos.writeObject(frontEndData);
            oos.flush();

            // Wait for server response
            Request serverData = (Request) ois.readObject();

            //Process Request
            response += serverData.getRequestType();
            System.out.println(response);
        }
        catch (UnknownHostException e) {
            // TODO Display error to user
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        }
        catch (IOException e) {
            // TODO Display error to user
            e.printStackTrace();
            response = "IOException: " + e.toString();
        }
        catch(ClassNotFoundException e){
            // TODO Display error to user
            e.printStackTrace();
            response = "ClassNotFoundException: " + e.toString();
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
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        textResponse.setText(response);
        super.onPostExecute(result);
    }
}
