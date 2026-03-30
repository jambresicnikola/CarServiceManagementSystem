module hr.tvz.carservicemanagementsystem.carservicemanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens hr.tvz.carservicemanagementsystem to javafx.fxml;
    exports hr.tvz.carservicemanagementsystem;
}