module hr.tvz.carservicemanagementsystem.carservicemanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires ch.qos.logback.classic;
    requires java.sql;
    requires bcrypt;

    exports hr.tvz.carservicemanagementsystem.app;
    opens hr.tvz.carservicemanagementsystem.app        to javafx.fxml;
    opens hr.tvz.carservicemanagementsystem.controller to javafx.fxml;
    opens hr.tvz.carservicemanagementsystem.model      to javafx.base;
}