package tutor.searcher.TutorSearcher;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.Socket;

import static org.mockito.Mockito.*;

@SpringBootTest
class RequestThreadMockTests {

    @Test
    public void RequestThreadMockTests_Constructor() {
        Socket s = new Socket();
        Controller controllerMock = mock(Controller.class);
        RequestThread rt = new RequestThread(s, controllerMock);
    }
}
