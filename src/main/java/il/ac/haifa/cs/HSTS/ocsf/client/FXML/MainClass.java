package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import java.io.IOException;

import il.ac.haifa.cs.HSTS.HSTSClient;
import il.ac.haifa.cs.HSTS.HSTSClientInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.boot.archive.scan.spi.ScanEnvironment;

public class MainClass extends Application {

	private static Scene scene;


    @Override
	public void start(Stage stage) throws IOException {
	    stage.setTitle("Login");
	    //stage.initStyle(StageStyle.UNDECORATED);
        scene = new Scene(loadFXML("Login"));
        Scene menu = new Scene(loadFXML("Login"));
        stage.setScene(scene);
        stage.show();
	}

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

	public static void main(String[] args) {
		launch();
	}

}
