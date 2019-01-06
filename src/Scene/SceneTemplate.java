package Scene;

import Network.MessageManager;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public abstract class SceneTemplate {

    protected MessageManager messageManager;
    private int width;
    private int heigth;
    protected String nickName;

    protected Text statusText;

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
        return this.nickName;
    }

    public void setStatusText(String text)
    {
        this.statusText.setFill(Color.BLACK);
        this.statusText.setText(text);
    }

    public void setErrorStatusText(String text)
    {
        this.statusText.setFill(Color.RED);
        this.statusText.setText(text);
    }

    public String getStatusText()
    {
        return this.statusText.getText();
    }



    abstract Node getControls();

}