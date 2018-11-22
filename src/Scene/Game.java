package Scene;

import Network.MessageManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Game extends SceneTemplate {


    private Cell[][] board = new Cell[3][3];

    public Game(MessageManager messageManager, int width, int heigth)
    {
        super(messageManager, width, heigth);
    }


    @Override
    public Node getControls() {
        Pane controls = new Pane();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Cell cell = new Cell();
                cell.setTranslateX(j * 100);
                cell.setTranslateY(i * 100);

                controls.getChildren().add(cell);

                board[j][i] = cell;
            }
        }

        return controls;
    }


    private class Cell extends StackPane {
        private Text text = new Text();

        public Cell() {
            Rectangle border = new Rectangle(100, 100);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font(72));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);

            setOnMouseClicked(event -> {
             /*   if (!playable)
                    return;

                if (event.getButton() == MouseButton.PRIMARY) {
                    if (!turnX)
                        return;

                    drawX();
                    turnX = false;
                    checkState();
                } else if (event.getButton() == MouseButton.SECONDARY) {
                    if (turnX)
                        return;

                    drawO();
                    turnX = true;
                    checkState();
                }
                */

                if (event.getButton() == MouseButton.PRIMARY) {

                    drawX();

                }

            });
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
    }
}


