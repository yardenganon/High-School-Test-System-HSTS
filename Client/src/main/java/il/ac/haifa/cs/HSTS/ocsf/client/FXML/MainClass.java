package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import java.io.IOException;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClientInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.boot.archive.scan.spi.ScanEnvironment;

public class MainClass extends Application {

	private static Scene scene;
	private HSTSClient client;

    @Override
	public void start(Stage stage) throws IOException {
	    stage.setTitle("Login");
        FXMLLoader loader = new FXMLLoader(MainClass.class.getResource("Login.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        initConnection();

        // Passing client reference to LoginController
        LoginController loginController = loader.<LoginController>getController();
        loginController.passHSTSClientReference(client);
	}

	public void initConnection() {
        this.client = new HSTSClient("localhost", 3000);
        try {
            client.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void stop() throws Exception {
        super.stop();
        client.closeConnection();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

	public static void main(String[] args) {
		launch();
	}

}
