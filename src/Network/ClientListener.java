package Network;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClientListener implements Runnable
{

    private MessageManager mm;

    public ClientListener(MessageManager mm)
    {
        this.mm = mm;
    }

    @Override
    public void run() {

            while (true)
            {
                try
                {
                   final String message = mm.recvMessage();
                    if (message != null)
                    {
                        System.out.println("Message Received: " + message);
                        Platform.runLater(() -> mm.resolveMessage(message));
                    }
                }
                catch (Exception e)
                {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setHeaderText("Connection lost");
                        alert.setContentText("Connection lost");
                        alert.showAndWait();

                        System.exit(0);
                    });
                    break;
                }
            }
    }
}

