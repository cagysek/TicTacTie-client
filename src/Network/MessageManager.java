package Network;

import Logic.Controller;
import Enum.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import Object.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class MessageManager
{
    private Socket socket;

    private BufferedWriter bw;
    private BufferedReader br;

    private Controller controller;

    private String lastMessage = "";

    public MessageManager(Controller controller, Configuration config)
    {
        this.controller = controller;

        try
                {
                    socket = new Socket(config.getIp(), config.getPort());


                    //socket = new Socket("10.10.80.19", 10000);
                    InetAddress adresa = socket.getInetAddress();
                    System.out.print("Pripojuju se na : "+adresa.getHostAddress()+" se jmenem : "+adresa.getHostName()+"\n" );

                    OutputStreamWriter osw = new OutputStreamWriter(this.socket.getOutputStream());
                    bw = new BufferedWriter(osw);

                    br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                }
                catch (IOException e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Server Error");
                    alert.setContentText("Can not connect to server. Try it again later.");
                    alert.showAndWait();

                    System.exit(0);
                }

    }

    public void sendMessage(String msg)
    {
        try
        {
            bw.write(msg);
            bw.flush();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public String recvMessage()
    {
        try {
            String msg = br.readLine().trim();

            if (!msg.isEmpty())
            {
                this.lastMessage = msg;

                return this.lastMessage;
            }
            else
            {
                lostConnection();

                return null;
            }

        }
        catch (Exception e)
        {
            lostConnection();

            return null;
        }
    }

    private void lostConnection()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Connection lost");
        alert.setContentText("Connection lost");
        alert.showAndWait();

        System.exit(0);
    }


    public void findGame()
    {
        sendMessage(EMessagePrefix.FIND_GAME.toString());
    }

    public void loginToServer(String nickname)
    {
        this.controller.setPlayer(nickname);
        sendMessage(EMessagePrefix.LOGIN + nickname + ";");
    }

    public void sentMoveToServer(int row, int column)
    {
        sendMessage(EMessagePrefix.TURN.toString() + row + EMessagePrefix.DELIMETER + column + EMessagePrefix.DELIMETER);
    }

    public void closeGame()
    {
        sendMessage(EMessagePrefix.CLOSE_GAME.toString());
    }

    public void exit()
    {
        sendMessage(EMessagePrefix.EXIT.toString());
    }


    public void replay()
    {
        sendMessage(EMessagePrefix.REMATCH.toString());
    }

    public void resolveMessage(String msg)
    {
        String parts[] = msg.split(";");

        EState state = EState.valueOf(parts[0].toUpperCase());
        ResponseData data;

        if (state.equals(EState.OPPONENT_TURN) || state.equals(EState.YOUR_TURN))
        {
            int row = Integer.valueOf(parts[1]);
            int column = Integer.valueOf(parts[2]);

            data = new ResponseData(state, new Coordinates(row, column));
        }
        else if (state.equals(EState.WIN) ||  state.equals(EState.TIE) ||  state.equals(EState.LOSE))
        {
            int yourScore = Integer.valueOf(parts[1]);
            int opponentScore = Integer.valueOf(parts[2]);

            data = new ResponseData(state, new Score(yourScore, opponentScore));
        }
        else if (state.equals(EState.RECONNECT))
        {
            data = new ResponseData(state, new Reconnect(parts[1], Integer.parseInt(parts[2])));
        }
        else if (state.equals(EState.STATUS))
        {
            data = new ResponseData(state, parts[1]);
        }
        else
        {
            int result = 0;
            if (parts.length > 1) {
                result = Integer.valueOf(parts[1]);
            }
            data = new ResponseData(state, result);
        }


        controller.setState(data);

    }



}
