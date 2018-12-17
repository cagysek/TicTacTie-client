package Logic;

import Network.ClientListener;
import Network.MessageManager;
import Enum.*;
import Object.*;
import Scene.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Controller {

    private final Stage stage;

    private MessageManager messageManager;

    private EState state;

    private SceneTemplate currentScene;

    private String nickName;

    private Thread listener;

    public Controller(Stage stage)
    {

        this.stage = stage;

    }

    public void startUpListener()
    {
        ClientListener clientListener = new ClientListener(messageManager);
        listener = new Thread(clientListener);
        listener.start();
    }

    public void setPlayer(String player)
    {
        this.nickName = player;
    }

    public void setMessageManager(MessageManager messageManager)
    {
        this.messageManager = messageManager;
    }

    public void setDefaultWindow()
    {
        this.currentScene = new Login(this.messageManager, 380, 200);
        render(this.currentScene.getScene());
    }


    private void actionSuccLogin()
    {
        this.currentScene = new Lobby(this.messageManager, 380,200);
        this.currentScene.setNickName(this.nickName);
        render(this.currentScene.getScene());

        System.out.println("Player successful logged");
    }

    private void actionStartGame()
    {
        this.currentScene = new Game(this.messageManager, 300, 300, null);
        render(this.currentScene.getScene());
        System.out.println("Starting game");

    }

    private void actionYourMove(int row, int column)
    {
        ((Game)this.currentScene).drawX(row, column);
    }

    private void actionOpponentMove(int row, int column)
    {
        ((Game)this.currentScene).drawO(row, column);
    }

    private void actionShowResult(String result, Score score)
    {
        this.currentScene = new Result(this.messageManager, 300,300, result, score);

        render(this.currentScene.getScene());

    }

    private void actionCloseGame()
    {
        this.currentScene = new Lobby(this.messageManager, 380,200);
        this.currentScene.setNickName(this.nickName);
        render(this.currentScene.getScene());
    }

    private void actionShowAlertMsg(String msg_header, String msg_body)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(msg_header);
        alert.getDialogPane().setContentText(msg_body);
        alert.showAndWait();
    }

    private void actionWaiting()
    {
        System.out.println("Looking for game");
        ((Lobby)this.currentScene).lookingFor();
    }

    private void actionExit()
    {
        listener.interrupt();


        System.exit(0);
    }

    private void actionReconnectGame(Reconnect reconnect)
    {
        this.currentScene = new Game(this.messageManager, 300, 300, reconnect);
        render(this.currentScene.getScene());
        ((Game) this.currentScene).reconnect();
        System.out.println("Reconnecting game");
    }

    public void render(Scene window)
    {
        this.stage.setScene(window);
    }

    public void actionSetStatus(String msg)
    {
        this.currentScene.setStatusText(msg);
    }

    public void setState(ResponseData data)
    {
        switch (data.getState())
        {
            case LOGIN:
                actionSuccLogin();
                break;
            case LOBBY:

                break;
            case WAITING:
                actionWaiting();
                break;
            case STARTING_GAME:
                actionStartGame();
                break;
            case YOUR_TURN:
                if (data.getResult() == 0)
                {
                    actionYourMove(data.getCoordinates().getRow(), data.getCoordinates().getColumn());
                }

                break;
            case OPPONENT_TURN:
                actionOpponentMove(data.getCoordinates().getRow(), data.getCoordinates().getColumn());
                break;

            case WIN:
                actionShowResult("YOU WIN!", data.getScore());
                break;
            case LOSE:
                actionShowResult("YOU LOSE", data.getScore());
                break;
            case TIE:
                actionShowResult("TIE", data.getScore());
                break;
            case CLOSE_GAME:
                actionCloseGame();
                break;
            case NAME_IS_NOT_AVALIABLE:
                actionShowAlertMsg("Log in error!","Name is used at the moment. Please choose different.");
                break;
            case EXIT:
                actionExit();
                break;
            case RECONNECT:
                actionReconnectGame(data.getReconnect());
                break;
            case STATUS:
                actionSetStatus(data.getMsg());
                break;
            default:
                System.out.println("UNKNOWN STATE: " +data.getState());

        }
    }

}
