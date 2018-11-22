import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class MessageManager
{
    private Socket socket;

    public MessageManager()
    {
                try
                {
                    socket = new Socket("127.0.0.1", 10000);
                    InetAddress adresa = socket.getInetAddress();
                    System.out.print("Pripojuju se na : "+adresa.getHostAddress()+" se jmenem : "+adresa.getHostName()+"\n" );
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

    }

    public void sendMessage(String msg)
    {
        OutputStreamWriter osw;
        BufferedWriter bw;
        try
        {
            osw = new OutputStreamWriter(this.socket.getOutputStream());
            bw = new BufferedWriter(osw);


            bw.write(msg);
            bw.flush();

            bw.close();
            osw.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public String recvMessage()
    {
        try {
            String msg = "";
            String buffer;
            BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            while ((buffer = br.readLine()) != null) {
                msg += buffer;
               // if (msg.contains(";")) {
              //      return msg;
              //  }
            }


            return msg;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "error baby";
        }
    }
}
