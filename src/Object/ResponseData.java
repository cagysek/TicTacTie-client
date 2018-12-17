package Object;

import Enum.*;

public class ResponseData {


    private EState state;
    private int result;
    private String msg;

    private Coordinates coordinates;
    private Score score;

    private Reconnect reconnect;

    public ResponseData(EState state, int result)
    {
        this.state = state;
        this.result = result;
    }

    public ResponseData(EState state, Coordinates coordinates)
    {

        this.state = state;

        this.coordinates = coordinates;
    }

    public ResponseData(EState state, Score score)
    {
        this.state = state;
        this.score = score;
    }

    public ResponseData(EState state, Reconnect reconnect)
    {
        this.state = state;
        this.reconnect = reconnect;
    }

    public ResponseData(EState state, String msg)
    {

        this.state = state;
        this.msg = msg;
    }

    public EState getState() {
        return state;
    }

    public int getResult() {
        return result;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Score getScore() {
        return score;
    }

    public Reconnect getReconnect() {
        return reconnect;
    }

    public String getMsg() {
        return msg;
    }
}
