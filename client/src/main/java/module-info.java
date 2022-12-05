module modules.controller {
    requires javafx.controls;
    requires javafx.fxml;

    opens modules.core to javafx.fxml;
    opens modules.core.fxmlcontrollers.employee to javafx.fxml;
    opens modules.core.fxmlcontrollers.manager to javafx.fxml;
    opens modules.core.models to javafx.base;
    opens modules.core.tables to javafx.base;

    exports modules.core;
    exports modules.core.fxmlcontrollers.employee;
    exports modules.core.fxmlcontrollers.manager;
    exports modules.core.models;
    exports modules.core.tables;
}
