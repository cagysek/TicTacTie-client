package Main;

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
import Object.Configuration;
import Network.MessageManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application
{

    private static Configuration config;

    public static void main(String[] args)
    {
        try
        {
            config = new Configuration(args[0], Integer.valueOf(args[1]));
        }
        catch (Exception e)
        {
            printHelp();

            System.exit(0);
        }


        launch(args);

    }

    public static void printHelp()
    {
        System.out.println("Using: java -jar TicTacToe <ADDRESS> <PORT>");
        System.out.println("ADDRESS \t- Server address");
        System.out.println("PORT \t\t- Server port");
    }

    @Override
    public void start(Stage primaryStage)
    {

        Controller controller = new Controller(primaryStage);

        MessageManager messageManager = new MessageManager(controller, config);


        controller.setMessageManager(messageManager);
        controller.startUpListener();
        controller.setDefaultWindow();

        primaryStage.show();
    }
}


