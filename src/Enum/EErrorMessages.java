package Enum;

public enum EErrorMessages {
    
    ;



    private final String text;

    EErrorMessages(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }




}
