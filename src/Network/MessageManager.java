package Network;

import Logic.Controller;
import Enum.EMessagePrefix;
import javafx.stage.Stage;

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

    public MessageManager(Controller controller)
    {
        this.controller = controller;

        try
                {
                    socket = new Socket("127.0.0.1", 10000);
                    InetAddress adresa = socket.getInetAddress();
                    System.out.print("Pripojuju se na : "+adresa.getHostAddress()+" se jmenem : "+adresa.getHostName()+"\n" );

                    OutputStreamWriter osw = new OutputStreamWriter(this.socket.getOutputStream());
                    bw = new BufferedWriter(osw);

                    br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
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

            if (!msg.isEmpty()) {
                this.lastMessage = msg;

                return this.lastMessage;
            }

            return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "error baby";
        }
    }

    public Socket getSocket()
    {
        return this.socket;
    }

    public String getLastMessage()
    {
        return this.lastMessage;
    }


    public void findGame()
    {
        sendMessage(EMessagePrefix.FIND_GAME.toString());
        controller.actionStartGame();
    }

    public void loginToServer(String nickname)
    {
        sendMessage(EMessagePrefix.LOGIN + nickname + ";");
        controller.actionSuccLogin();

    }

    public void sentMoveToServer(int row, int column)
    {
        sendMessage(EMessagePrefix.TURN.toString() + row + EMessagePrefix.DELIMETER + column + EMessagePrefix.DELIMETER);
    }


}
