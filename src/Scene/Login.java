package Scene;

import Network.MessageManager;
import Enum.EMessagePrefix;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Login extends SceneTemplate {

    public Login(MessageManager messageManager, int width, int heigth)
    {
        super(messageManager, width, heigth);
    }

    @Override
    public Node getControls() {
        VBox controls = new VBox();
        Text header = new Text("Please log in");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        TextField nickNameInput = new TextField();
        nickNameInput.setMaxWidth(240);
        nickNameInput.setMinWidth(240);

        Button logButt = new Button("Log in");
        logButt.setPrefSize(80,30);
        logButt.setOnAction(event -> loginToServer(nickNameInput.getText()));

        controls.getChildren().addAll(header, nickNameInput, logButt);
        controls.setSpacing(15);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10));

        return controls;
    }

    private void loginToServer(String nickName)
    {
        setNickName(nickName);
        super.messageManager.loginToServer(nickName);
    }


}
