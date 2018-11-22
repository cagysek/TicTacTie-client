import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;




public class Layout extends Application {

    private Stage stage;
    private TextField nickName;


    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MessageManager mm = new MessageManager();
        mm.sendMessage("NAME;cagy;");

        ClientListener clientListener = new ClientListener(mm);
        Thread listener = new Thread(clientListener);
        listener.start();



        this.stage = primaryStage;
        this.stage.setScene(getSceneLoggin());

        this.stage.show();
    }

    private Scene getSceneLoggin()
    {
        Scene logginScene = new Scene(getRoot(), 380,280);
        stage.setTitle("Log in");
        return  logginScene;
    }


    private Parent getRoot()
    {
        BorderPane root = new BorderPane();
        root.setCenter(getLogginControls());
        return root;
    }

    private Node getLogginControls()
    {
        VBox controls = new VBox();
        Text header = new Text("Please log in");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        nickName = new TextField();
        nickName.setMaxWidth(240);
        nickName.setMinWidth(240);

        Button logButt = new Button("Log in");
        logButt.setPrefSize(80,30);

        controls.getChildren().addAll(header, nickName, logButt);
        controls.setSpacing(15);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(10));

        return controls;
    }
}

