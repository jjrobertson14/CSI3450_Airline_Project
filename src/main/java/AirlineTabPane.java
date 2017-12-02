import javafx.scene.control.*;

public class AirlineTabPane extends TabPane {

    private Tab customerTab;

    private Tab employeeTab;

    private Tab adminTab;

    private Tab databaseTab;

    public AirlineTabPane() {
        super();

        customerTab = new Tab("Customer");
        employeeTab = new Tab("Employee");
        adminTab    = new Tab("Admin");
        databaseTab = new Tab("Database");

        this.getTabs().addAll(
            customerTab,
            employeeTab,
            adminTab,
            databaseTab
        );
        
        customerTab.setContent(new CustomerControl());
        adminTab.setContent(new AdminControl());
        databaseTab.setContent(new DatabaseControl());
        employeeTab.setContent(new EmployeeControl());

        // disallow closing tabs
        this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }

}
