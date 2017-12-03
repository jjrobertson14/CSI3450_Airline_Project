import javafx.application.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * This is the main class of the application. It defines the root UI elements
 * Location src/main/java
 * Date: November 23th 2017
 * @author Noah
 *
 */
public class App extends Application {

    // the main scene displayed by the primaryStage
    Scene mainScene;

    // the root element of the mainScene
    BorderPane root;

    // the main display element
    AirlineTabPane tabPane;

    // status bar
    StatusBar statusBar;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        tabPane  = new AirlineTabPane();

        statusBar = new StatusBar();

        root = new BorderPane();

        mainScene = new Scene(root);

        root.setCenter(tabPane);
        root.setBottom(statusBar);

        statusBar.launchConnectionStatusThread();

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Airline Administration");
        primaryStage.setMinHeight(545);
        primaryStage.setMinWidth(700);
        primaryStage.show();
    }
}
