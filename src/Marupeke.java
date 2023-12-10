import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.util.Random;

public class Marupeke extends Application {
    MarupekeGrid g;
    int size = 10;
    GridPane gridPane;
    BorderPane borderPane;
    GridPane gridPane1;
    Random r = new Random();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{

            this.g = MarupekeGrid.randomPuzzle(this.size, r.nextInt(this.size/2), r.nextInt(this.size/2), r.nextInt(this.size/2));

            borderPane = new BorderPane();

            borderPane.setBottom(sizeButtons());
            gridPane = buttonsMaker(this.g);
            gridPane1 = new GridPane();


            gridPane1.add(endButton(),1, 1);
            gridPane1.setAlignment(Pos.CENTER);
            gridPane.setAlignment(Pos.CENTER);
            borderPane.setTop(gridPane1);
            borderPane.setCenter(gridPane);



            Scene scene = new Scene(borderPane);

            primaryStage.setTitle("Marupeke!");
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch (TooManyMarkedSquares e) {
            System.exit(1);
        }


    }

    public Button endButton() {
        Button endCheck = new Button("Check");

        endCheck.setOnAction(e -> {
            if (g.isPuzzleComplete()) {
                endCheck.setText("Winner");
                TilePane tile = new TilePane();
                Label label = new Label("Congratulations you win! \nTo play again select a different size.");
                label.setTextAlignment(TextAlignment.CENTER);
                tile.getChildren().add(label);
                tile.setAlignment(Pos.CENTER);
                borderPane.setCenter(tile);
            }

        });
        return endCheck;
    }
    public GridPane sizeButtons() {
        GridPane sizes = new GridPane();

        final int[] size = {10};
        for (int i = 3; i <= size[0]; i++) {
            String name = "Size " + String.valueOf(i);
            Button button = new Button(name);
            button.setPrefSize(60, 20);
            int finalI = i;
            button.setOnAction(e -> {
                size[0] = finalI;
                this.size = size[0];
                borderPane.getChildren().remove(gridPane);
                borderPane.getChildren().remove(gridPane1);
                try {
                    this.g = MarupekeGrid.randomPuzzle(this.size, r.nextInt(this.size/2), r.nextInt(this.size/2), r.nextInt(this.size/2));
                } catch (TooManyMarkedSquares tooManyMarkedSquares) {
                    System.exit(1);
                }
                gridPane = buttonsMaker(g);
                gridPane.setAlignment(Pos.CENTER);
                gridPane1.add(endButton(),1, 1);
                gridPane1.setAlignment(Pos.CENTER);
                borderPane.setTop(gridPane1);
                borderPane.setCenter(gridPane);
            });
            sizes.add(button, i, 0);
        }

        sizes.setAlignment(Pos.CENTER);
        return sizes;
    }

    public GridPane buttonsMaker(MarupekeGrid grid) {
        GridPane gridPane = new GridPane();
        char[][] c = grid.getGrid();
        boolean[][] editable = grid.getEditable();

        for (int j = 0; j < grid.getGridW(); j++) {
            for (int i = 0; i < grid.getGridL(); i++) {
                Button button = new Button();
                button.setPrefSize(50,50);
                if (c[i][j] == '_') {
                    c[i][j] = ' ';
                }
                String s = String.valueOf(c[i][j]);
                button.setText(s);
                final int[] loop = {-1};
                Button finalButton = button;
                button.setOnAction(e -> {
                     {
                         if (g.getEditablePart(GridPane.getRowIndex(finalButton), GridPane.getColumnIndex(finalButton))) {
                             loop[0]++;
                             switch (loop[0]) {
                                 case 0: finalButton.setText(Tile.X.toString());
                                     g.markX(GridPane.getRowIndex(finalButton), GridPane.getColumnIndex(finalButton));
                                     break;
                                 case 1: finalButton.setText(Tile.O.toString());
                                     g.markO(GridPane.getRowIndex(finalButton), GridPane.getColumnIndex(finalButton));
                                     break;
                                 case 2: finalButton.setText(" ");
                                     g.unmark(GridPane.getRowIndex(finalButton), GridPane.getColumnIndex(finalButton));
                                     loop[0] = -1;
                                     break;
                             }
                         }

                    }
                });

                if (!editable[i][j]) {
                    button.setStyle("-fx-background-color: #ff0000; -fx-font-size: 2em;");
                } else {
                    button.setStyle("-fx-font-size: 2em;");
                }


                gridPane.add(button, j, i, 1, 1);
            }
        }
        return gridPane;
    }

}
