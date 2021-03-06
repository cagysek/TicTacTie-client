package Network;

import Logic.Controller;
import Enum.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import Object.*;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class MessageManager
{
    private Socket socket;

    private BufferedWriter bw;
    private BufferedReader br;

    private Controller controller;

    private String lastMessage = "";

    private Configuration config;

    private boolean allowCommunication = true;

    public MessageManager(Controller controller, Configuration config)
    {
        this.controller = controller;
        this.config = config;

        createConnection(2000);

    }

    public void createConnection(int timeout)
    {
        try
        {
            socket = new Socket();


            InetSocketAddress isa = new InetSocketAddress(this.config.getIp(), this.config.getPort());
            socket.connect(isa, timeout);


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
        if (this.allowCommunication) {

            if (msg.equals("ACK;|") || testConnection()) {
                try {
                    bw.write(msg);
                    bw.flush();

                } catch (IOException e) {
                    printLostConnection();
                }
            } else {
                printLostConnection();
            }
        }


    }

    private void printLostConnection()
    {
        this.allowCommunication = false;

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Lost Connection");
        alert.setContentText("Connection with server is lost. Trying to reconnect.");
        alert.showAndWait();

        controller.actionSetStatus("Reconnecting...");
    }

    private boolean testConnection()
    {
        try
        {
            bw.write("PING;|");
            bw.flush();

            return true;
        }
        catch (IOException e)
        {
            this.allowCommunication = false;
            return false;
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
               // lostConnection();
                return null;
            }

        }
        catch (Exception e)
        {
            //lostConnection();

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
        sendMessage(EMessagePrefix.LOGIN + nickname + ";|");
    }

    public void sentMoveToServer(int row, int column)
    {
        sendMessage(EMessagePrefix.TURN.toString() + row + EMessagePrefix.DELIMETER + column + EMessagePrefix.DELIMETER + "|");
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

    public void resolving(String msg)
    {
        System.out.println(msg);
        String parts[] = msg.split("%");
        ArrayList<String> usedParts = new ArrayList<>();
        boolean used;

        for (int i = 0 ; i < parts.length ; i++)
        {
            used = false;

            for (int j = 0 ; j < usedParts.size() ; j++ )
            {
                if (usedParts.get(j).compareToIgnoreCase(parts[i]) == 0)
                {
                    used = true;
                }
            }

            if (!used)
            {
                usedParts.add(parts[i]);
                resolveMessage(parts[i]);
            }

        }


    }

    public int resolveMessage(String msg)
    {
        String parts[] = msg.split(";");

        if (parts.length == 0)
        {
            return -1;
        }

        EState state;

        try {
            state = EState.valueOf(parts[0].toUpperCase());
        }catch (Exception e)
        {
            System.out.println("Invalid message. Error: Unrecognised type of message");

            return -1;
        }

        System.out.println("Message Received: " + msg);

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
        else if (state.equals(EState.PING))
        {
            data = new ResponseData(state, 0);
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

        return 0;

    }


    private boolean checkConnection()
    {
        // Creates a socket address from a hostname and a port number
        InetSocketAddress socketAddress = new InetSocketAddress(this.config.getIp(), this.config.getPort());
        Socket socket = new Socket();

        // Timeout required - it's in milliseconds
        int timeout = 2000;

        try
        {
            socket.connect(socketAddress, timeout);
            socket.close();
            return true;
        }
        catch (Exception exception)
        {
            return false;
        }
    }

    public Controller getController()
    {
        return this.controller;
    }

    public void setAllowCommunication(boolean allowCommunication)
    {
        this.allowCommunication = allowCommunication;
    }



}
