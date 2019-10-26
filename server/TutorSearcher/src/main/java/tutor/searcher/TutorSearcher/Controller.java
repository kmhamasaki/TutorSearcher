package tutor.searcher.TutorSearcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller {
    @Autowired
    JdbcTemplate jdbc;

    int port = 6789;
    private ServerSocket ss = null;
    private Socket s = null;

    public String Controller() {

        System.out.println("Launching --GameServer--");
        System.out.println();





        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {
            ss = new ServerSocket(6789);
            ois = new ObjectInputStream(s.getInputStream());
            Request req = (Request) ois.readObject();

            oos.flush();

        } catch (ClassNotFoundException ioe) {

        } catch (IOException ioe) {

        }

        return "data inserted Successfully";
    }
}
