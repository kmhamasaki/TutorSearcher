package tutor.searcher.TutorSearcher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.Socket;
import java.util.HashMap;

@SpringBootTest
class ControllerTest {

    @Autowired
    private Controller controller;

    public ControllerTest() {
        
    }

    @Test
    void processRequestTest_Signup() {
        Socket s = new Socket();

        String requestType = "asda";
        HashMap<String, Object> respAttr = new HashMap<String, Object>();

        Request request = new Request(requestType, respAttr);
        RequestThread requestThread = new RequestThread(s, controller);
    }

}
