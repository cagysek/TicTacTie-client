package Network;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class ClientListener implements Runnable
{

    private MessageManager mm;

    public int invalid_message_couter = 0;

    private boolean before_invalid = false;

    private int MAX_INVALID = 5;

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
                        if (message.length() > 20)
                        {
                            throw new IOException();
                        }

                        Platform.runLater(() -> {

                            int result = mm.resolveMessage(message);

                            if (result == -1)
                            {
                                this.invalid_message_couter++;
                            }
                            else
                            {
                                this.invalid_message_couter = 0;
                            }

                        });
                    }
                }
                catch (IOException e){
                    this.invalid_message_couter++;
                    System.out.println("Invalid message. Error: Message out of range.");

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
                }
                finally {
                    if (MAX_INVALID < invalid_message_couter)
                    {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setHeaderText("Application stopped");
                            alert.setContentText("Application stopped, because server is sending invalid messages. Try it again later");
                            alert.showAndWait();

                            System.exit(0);
                        });
                    }
                }
            }
    }
}

