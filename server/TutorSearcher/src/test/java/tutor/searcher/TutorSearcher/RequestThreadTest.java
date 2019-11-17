package tutor.searcher.TutorSearcher;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.Socket;

@SpringBootTest
class RequestThreadTest {

    @Test
    void ConstructorTest() {
        Socket s = new Socket();
        Controller controller = new Controller();

        System.out.println("ASDAD");

        RequestThread rt = new RequestThread(s, controller);

    }
}

