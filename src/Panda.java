import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.util.Random;


public class Panda extends Application
{
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

  final static int WIDTH = 640, HEIGHT = 480;
  final static int SEED = 1;
  final static int COLS = 6, ROWS = 5;

  GraphicsContext gc;
  private Button[][] grid;
  private Button btnTarget;

  @Override
  public void start(Stage primaryStage) throws Exception
  {
    primaryStage.setTitle("Panda");

    final Random random = new Random(SEED);
    final GridPane gridPane = new GridPane();
    grid = new Button[COLS][ROWS];

    for (int x = 0; x < COLS; ++x)
    {
      for (int y = 0; y < ROWS; ++y)
      {
        final int col = x, row = y;
        final Button button = new Button( Material.values()[0].name() );
        button.setOnAction(e -> grid[col][row].setText( Material.find( grid[col][row].getText() ).next().name() ));
        grid[x][y] = button;
        gridPane.add(button, x, y, 1, 1);
      }
    }

    btnTarget = new Button( Material.values()[0].name() );
    btnTarget.setOnAction(e -> btnTarget.setText( Material.find( btnTarget.getText() ).next().name() ));
    gridPane.add(btnTarget, COLS/2-COLS/4, ROWS, COLS/2, 1);

    final Button btnSolve = new Button("Solve");
    btnSolve.setOnAction(e -> solve());
    gridPane.add(btnSolve, COLS/2+COLS/4, ROWS, COLS/2, 1);

    final Group group = new Group(gridPane);
    final Scene scene = new Scene(group, WIDTH, HEIGHT);

    final Canvas canvas = new Canvas(WIDTH, HEIGHT);
    canvas.setMouseTransparent(true);
    gc = canvas.getGraphicsContext2D();
    group.getChildren().add(canvas);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void solve()
  {
    gc.clearRect(0, 0, WIDTH, HEIGHT);
    
    final Bounds bounds0 = grid[0][0].localToScene( grid[0][0].getBoundsInLocal() );
    final Bounds bounds1 = grid[3][3].localToScene( grid[3][3].getBoundsInLocal() );

    gc.setFill(Color.BLACK);
    gc.setStroke(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.RED), new Stop(1, Color.GREEN)));
    gc.setLineWidth(5);
    gc.strokeLine(bounds0.getMinX(), bounds0.getMinY(), bounds1.getMinX(), bounds1.getMinY());
  }

  public static void main(String[] args)
  {
    Application.launch(args);
  }
}
