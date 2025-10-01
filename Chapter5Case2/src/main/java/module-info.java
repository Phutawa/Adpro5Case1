module se233.chapter5part2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens se233.chapter5part2.controller;
    opens se233.chapter5part2.model;
    opens se233.chapter5part2.view;

    exports se233.chapter5part2;
    exports se233.chapter5part2.controller;
    exports se233.chapter5part2.model;
    exports se233.chapter5part2.view;
}
