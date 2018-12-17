package Network;

import Network.MessageManager;
import javafx.application.Platform;

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
                    e.printStackTrace();
                    //
                }
            }
    }
}

