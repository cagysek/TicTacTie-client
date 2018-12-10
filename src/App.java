// Dear programmer:
// When I wrote this code, only god and I
// knew how it worked.
// Now only god knows it!

// Therefore, if you are trying to optimize
// this routine and it falls (most surely),
// please increase this counter as a warning
// for next person:

// total_waste_hours_here = XXX

import Logic.Controller;
import Network.ClientListener;
import Network.MessageManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Controller controller = new Controller(primaryStage);

        MessageManager messageManager = new MessageManager(controller);

        ClientListener clientListener = new ClientListener(messageManager);
        Thread listener = new Thread(clientListener);
        listener.start();

        controller.setMessageManager(messageManager);
        controller.setDefaultWindow();

        primaryStage.show();
    }
}


