package Scene;

import Network.MessageManager;
import Enum.EMessagePrefix;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Lobby extends SceneTemplate{

    private Button findGameButt;

    public Lobby(MessageManager messageManager, int width, int heigth)
    {
        super(messageManager, width, heigth);
    }

    @Override
    public Node getControls()
    {
        VBox controls = new VBox();
        Text header = new Text("Vítej " + super.getNickName());
        header.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        System.out.println(getNickName());

        findGameButt = new Button("Hrát hru");
        findGameButt.setPrefSize(130,30);
        findGameButt.setOnAction(event -> findGame());

        Button exitGameButt = new Button("Konec");
        exitGameButt.setPrefSize(130,30);
        exitGameButt.setOnAction(event -> exitGame());


        controls.getChildren().addAll(header, findGameButt, exitGameButt);
        controls.setSpacing(15);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10));

        return controls;

    }

    public void lookingFor()
    {
        findGameButt.setText("Looking for game");
        findGameButt.setDisable(true);
        findGameButt.setCursor(Cursor.WAIT);
    }

    private void findGame()
    {
        this.messageManager.findGame();
    }

    private void exitGame()
    {
        this.messageManager.exit();
    }
}
