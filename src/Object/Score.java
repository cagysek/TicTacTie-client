package Object;

public class Score {
    int yourScore;
    int opponentScore;

    public Score(int your_score, int opponent_score)
    {
        this.yourScore = your_score;
        this.opponentScore = opponent_score;
    }

    public int getYourScore() {
        return yourScore;
    }

    public int getOpponentScore() {
        return opponentScore;
    }
}
