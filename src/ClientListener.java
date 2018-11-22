public class ClientListener implements Runnable
{

    private  MessageManager mm;

    public ClientListener(MessageManager mm)
    {
        this.mm = mm;
    }

    @Override
    public void run() {
        String message = null;
        while (true)
        {
            try
            {
                message = mm.recvMessage();
                if (message != null)
                {
                    System.out.println("Message Received: " + message);
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

