package Network;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Connectivity implements Runnable {

    private boolean ping;

    private boolean check;

    private static final int TIMEOUT = 60;
    private MessageManager messageManager;

    public Connectivity(MessageManager messageManager)
    {
        this.messageManager = messageManager;
        this.ping = true;
        this.check = true;
    }

    @Override
    public void run() {
        while (true)
        {
            try
            {

                if (!this.ping && this.check)
                {
                    throw new Exception();
                }

                this.ping = false;
                Thread.sleep(10000);
            }
            catch (Exception e)
            {
                this.check = false;

                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Connection lost");
                    alert.setContentText("Connection with server lost");
                    alert.showAndWait();

                });

                int i = 0;

                this.messageManager.getController().actionSetStatus("Reconnecting...");
                this.messageManager.setAllowCommunication(false);

                while(!this.check)
                {

                    try
                    {
                        i++;

                        if (i == TIMEOUT)
                        {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setHeaderText("Connection timeout");
                                alert.setContentText("Connection timeout.");
                                alert.showAndWait();

                                System.exit(0);
                            });
                            break;
                        }


                        Thread.sleep(1000);

                    }
                    catch (Exception ex)
                    {
                        System.out.println("Unhandled exception");
                    }

                }

            }
        }
    }

    public void recievedPing()
    {
        this.ping = true;
    }

    public void setCheck(boolean check)
    {
        this.check = check;
    }
}
