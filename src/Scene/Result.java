package Scene;

import Network.MessageManager;
import Object.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Result extends SceneTemplate {

    private String headerText;

    private Score score;

    private Button replayButt;

    public Result(MessageManager messageManager, int width, int heigth, String headerText, Score score)
    {
        super(messageManager, width, heigth);
        this.headerText = headerText;
        this.score = score;
    }

    @Override
    Node getControls() {
        SplitPane root = new SplitPane();

        root.setOrientation(Orientation.VERTICAL);

        VBox controls = new VBox();
        Text header = new Text(headerText);
        header.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        HBox result = new HBox();
        Text yourScore = new Text(Integer.toString(score.getYourScore()));
        Text delimiter = new Text("  -  ");
        Text opponentScore = new Text(Integer.toString(score.getOpponentScore()));

        yourScore.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        opponentScore.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        delimiter.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        result.setAlignment(Pos.CENTER);


        result.getChildren().addAll(yourScore, delimiter, opponentScore);

        this.replayButt = new Button("Replay");
        this.replayButt.setPrefSize(80,30);
        this.replayButt.setOnAction(event -> replay());

        Button exitButt = new Button("Exit");
        exitButt.setPrefSize(80,30);
        exitButt.setOnAction(event -> exit());

        controls.getChildren().addAll(header, result, replayButt, exitButt);
        controls.setSpacing(15);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10));

        HBox status = new HBox();
        Text statusHeadline = new Text("Status: ");
        statusHeadline.setTextAlignment(TextAlignment.LEFT);

        this.statusText = new Text("Ready");
        this.statusText.setTextAlignment(TextAlignment.LEFT);

        status.getChildren().addAll(statusHeadline,this.statusText);
        status.setMaxHeight(10);

        root.getItems().addAll(controls, status);
        SplitPane.setResizableWithParent(status, false);

        return root;
    }

    public void lookingFor()
    {
        this.replayButt.setText("Waiting for opponent");
        this.replayButt.setDisable(true);
        this.replayButt.setCursor(Cursor.WAIT);
    }

    private void replay()
    {
        this.messageManager.replay();
    }


    private void exit()
    {
        this.messageManager.closeGame();
    }
}
