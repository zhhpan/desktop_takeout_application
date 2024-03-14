module com.test.topdesk {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires com.baomidou.mybatis.plus.core;
    requires com.baomidou.mybatis.plus.annotation;
    requires lombok;
    requires org.mybatis;
    requires junit;
    requires spring.context;
    requires com.baomidou.mybatis.plus.extension;

    opens com.test.topdesk to javafx.fxml;
    exports com.test.topdesk;
    exports com.test.topdesk.Controller;
    exports com.test.topdesk.pojo;
    opens com.test.topdesk.Controller to javafx.fxml;
}