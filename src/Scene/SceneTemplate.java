package Scene;

import Network.MessageManager;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public abstract class SceneTemplate {

    protected MessageManager messageManager;
    private int width;
    private int heigth;
    protected String nickName;

    public SceneTemplate(MessageManager messageManager, int width, int heigth)
    {
        this.messageManager = messageManager;
        this.width = width;
        this.heigth = heigth;
    }

    public Scene getScene()
    {
        return new Scene(getRoot(), this.width,this.heigth);
    }

    private Parent getRoot()
    {
        BorderPane root = new BorderPane();
        root.setCenter(getControls());
        return root;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getNickName()
    {
        return nickName;
    }

    abstract Node getControls();

}