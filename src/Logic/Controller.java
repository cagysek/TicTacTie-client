package Logic;

import Network.MessageManager;
import Scene.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {

    private final Stage stage;

    private MessageManager messageManager;

    public Controller(Stage stage)
    {

        this.stage = stage;

    }

    public void setMessageManager(MessageManager messageManager)
    {
        this.messageManager = messageManager;
    }

    public void setDefaultWindow()
    {
        render(new Login(this.messageManager, 380, 200).getScene());
    }


    public void actionSuccLogin()
    {
        render(new Lobby(this.messageManager, 380,200).getScene());
    }

    public void actionStartGame() {
       try
       {
           while (true)
           {
               if (this.messageManager.getLastMessage().equalsIgnoreCase("STARTING_GAME"))
               {
                   render(new Game(this.messageManager, 300, 300).getScene());
                   break;
               }
               Thread.sleep(200);
           }
       }
       catch (Exception e)
       {
           System.out.println("Error while starting game");
       }

    }


    public void actionLogin()
    {

    }

    public void render(Scene window)
    {
        this.stage.setScene(window);
    }




}
