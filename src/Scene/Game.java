package Scene;

import Network.MessageManager;
import Object.*;
import Enum.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Game extends SceneTemplate {


    private final Reconnect reconnect;
    private int SIZE = 3;

    private Cell[][] board = new Cell[SIZE][SIZE];

    public Game(MessageManager messageManager, int width, int heigth, Reconnect reconnect)
    {
        super(messageManager, width, heigth + 25); // magic jak svine, ale zoufaly situace, zoufaly ciny :D
        this.reconnect = reconnect;
    }


    @Override
    public Node getControls() {
        SplitPane root = new SplitPane();

        root.setOrientation(Orientation.VERTICAL);
        Pane controls = new Pane();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Cell cell = new Cell(this.messageManager, j, i);
                cell.setTranslateX(j * 100);
                cell.setTranslateY(i * 100);

                controls.getChildren().add(cell);

                board[j][i] = cell;
            }
        }


        HBox status = new HBox();
        Text statusHeadline = new Text("Status: ");
        statusHeadline.setTextAlignment(TextAlignment.LEFT);

        this.statusText = new Text("");
        this.statusText.setTextAlignment(TextAlignment.LEFT);

        status.getChildren().addAll(statusHeadline,this.statusText);
        status.setMaxHeight(10);

        root.getItems().addAll(controls, status);
        SplitPane.setResizableWithParent(status, false);

        return root;
    }

    public void drawX(int row, int column)
    {
        Cell cell = this.board[row][column];
        cell.drawX();
    }

    public void drawO(int row, int column)
    {
        Cell cell = this.board[row][column];
        cell.drawO();
    }


    public void reconnect()
    {
        int[][] data = this.reconnect.getGameBoard();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (data[j][i] == this.reconnect.getMyIndicator())
                {
                    board[j][i].drawX();
                }
                else if (data[j][i] != 0)
                {
                    board[j][i].drawO();
                }
            }
        }
    }

    public void reset()
    {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[j][i].clearCell();
            }
        }
    }


    private class Cell extends StackPane {
        private Text text = new Text();
        private MessageManager messageManager;
        private int row;
        private int column;

        public Cell(MessageManager messageManager, int row, int column) {
            this.messageManager = messageManager;
            this.row = row;
            this.column = column;

            Rectangle border = new Rectangle(100, 100);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font(72));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            setOnMouseClicked(event ->
            {
                if (event.getButton() == MouseButton.PRIMARY)
                {
                    this.messageManager.sentMoveToServer(this.row, this.column);
                }

            });
        }

        public double getRow()
        {
            return this.row;
        }

        public double getColumn()
        {
            return this.column;
        }

        public double getCenterX() {
            return getTranslateX() + 100;
        }

        public double getCenterY() {
            return getTranslateY() + 100;
        }

        public String getValue() {
            return text.getText();
        }

        public void drawX() {
            text.setText("X");
        }

        public void drawO() {
            text.setText("O");
        }

        public void clearCell()
        {
            text.setText("");
        }
    }
}


