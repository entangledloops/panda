import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Random;


public class Panda extends Application
{
  final static int SEED = 1;
  final static int COLS = 6, ROWS = 5;

  enum Material
  {
    FIRE,
    WATER,
    GRASS,
    LIGHT,
    DARK,
    HEALTH,
    POISON,
    DEATH,
    JAMMER,
    BOMB,
    ;

    public Material next()
    {
      if (ordinal() == values().length-1) return values()[0];
      return values()[ ordinal()+1 ];
    }

    public static Material find(String name)
    {
      for (int i = 0; i < values().length; ++i)
        if (values()[i].name().equals(name)) return values()[i];
      return null;
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception
  {
    primaryStage.setTitle("Panda");

    final Random random = new Random(SEED);
    final GridPane gridPane = new GridPane();
    final Button[][] buttons = new Button[COLS][ROWS];

    for (int x = 0; x < COLS; ++x)
    {
      for (int y = 0; y < ROWS; ++y)
      {
        final int col = x, row = y;
        final Button button = new Button( Material.values()[0].name() );
        button.setOnAction(e -> buttons[col][row].setText( Material.find( buttons[col][row].getText() ).next().name() ));
        buttons[x][y] = button;
        gridPane.add(button, x, y, 1, 1);
      }
    }

    final Scene scene = new Scene(gridPane, 640, 480);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args)
  {
    Application.launch(args);
  }
}
