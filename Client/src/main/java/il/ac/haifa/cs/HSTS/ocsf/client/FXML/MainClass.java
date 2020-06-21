package il.ac.haifa.cs.HSTS.ocsf.client.FXML;

import java.io.IOException;

import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClient;
import il.ac.haifa.cs.HSTS.ocsf.client.HSTSClientInterface;
import il.ac.haifa.cs.HSTS.ocsf.client.Services.Bundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.boot.archive.scan.spi.ScanEnvironment;

import static il.ac.haifa.cs.HSTS.ocsf.client.HSTSClientInterface.saveStateBeforeLogout;

public class MainClass extends Application {

	private static Scene scene;
	private HSTSClient client;
	private Bundle bundle;
	private static String ip;
	private static int port;

    @Override
	public void start(Stage stage) throws IOException {
        initConnection();
        stage.setTitle("Login");
        bundle = Bundle.getInstance();
        bundle.put("client",client);
        FXMLLoader loader = new FXMLLoader(MainClass.class.getResource("Login.fxml"));
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();

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
        saveStateBeforeLogout();
        super.stop();
        client.closeConnection();
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

	public static void main(String[] args) {
		ip = args[0];
		port = Integer.parseInt(args[1]);
        launch();
	}
    
}
