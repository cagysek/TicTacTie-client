package Object;

import Enum.*;

public class ResponseData {


    EState state;
    int result;

    private Coordinates coordinates;
    private Score score;


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
}
