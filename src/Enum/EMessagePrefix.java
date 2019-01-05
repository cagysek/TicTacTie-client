package Enum;

public enum EMessagePrefix {
    DELIMETER(";"),
    LOGIN("NAME;"),
    FIND_GAME("FIND_GAME;|"),
    TURN("TURN;"),
    REMATCH("REMATCH;|"),
    CLOSE_GAME("CLOSE_GAME;|"),
    EXIT("EXIT;|")
    ;



    private final String text;

    EMessagePrefix(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }


}
