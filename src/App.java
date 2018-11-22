import Logic.Controller;
import Network.ClientListener;
import Network.MessageManager;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;




public class App extends Application {

    private Stage stage;

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;

        Controller controller = new Controller(this.stage);

        MessageManager messageManager = new MessageManager(controller);

        ClientListener clientListener = new ClientListener(messageManager);
        Thread listener = new Thread(clientListener);
        listener.start();

        controller.setMessageManager(messageManager);
        controller.setDefaultWindow();
        this.stage.show();
    }
}


