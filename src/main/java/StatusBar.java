import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class StatusBar extends HBox {

    private Label connectionStatus;

    private Thread connectionStatusThread;

    public StatusBar() {
        super();
        connectionStatus = new Label("Not Connected.");
        this.getChildren().add(connectionStatus);
    }

    public void launchConnectionStatusThread() {
        if (connectionStatusThread != null) {
            connectionStatusThread.interrupt();
        }

        connectionStatusThread = new Thread( () -> {
            AirlineSQLExecutor executor = new AirlineSQLExecutor();

            while(true) {

                if (Thread.interrupted()) {
                    return; 
                }

                try {

                    Platform.runLater( () -> {
                        connectionStatus.setText("...");
                    });

                    boolean result = executor.canConnect();
                    String statusLabel = result ? "Connected." : "Not Connected.";

                    Platform.runLater( () ->{
                        connectionStatus.setText(statusLabel);
                    });

                    Thread.sleep(8000);
                }
                catch (InterruptedException e) {
                    return;
                }
            }
        });

        connectionStatusThread.setDaemon(true);
        connectionStatusThread.start();
    }
}