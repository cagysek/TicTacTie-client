package Object;

public class Reconnect {

    private int[][] gameBoard;

    private int myIndicator;

    public Reconnect(String gameBoard, int myIndicator)
    {

        this.gameBoard = generateGameboard(gameBoard);
        this.myIndicator = myIndicator;
    }


    private int[][] generateGameboard(String gameBoard)
    {
        int[][] data = new int[3][3];

        String[] split = gameBoard.split(",");

        int k = 0; //col
        int j = 0; //row
        for (int i = 0 ; i < split.length ; i++)
        {
            data[j][k] = Integer.parseInt(split[i].trim());

            k++;

            if ((i + 1) % 3 == 0)
            {
                k = 0;
                j++;
            }
        }

        return data;
    }


    public int[][] getGameBoard() {
        return gameBoard;
    }

    public int getMyIndicator() {
        return myIndicator;
    }
}
