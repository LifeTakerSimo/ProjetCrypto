package App;

import Back.EncryptorAesGcmPasswordFile;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class App extends Application {

	public void start(final Stage primaryStage) {
		Button buttonPerf = new Button();
		buttonPerf.setTranslateX(0);
		buttonPerf.setTranslateY(0);

		Button button1 = new Button();
		button1.setTranslateX(150);
		button1.setTranslateY(150);

		Button button2 = new Button();
		button2.setTranslateX(150);
		button2.setTranslateY(-150);

		Button button3 = new Button();
		button3.setTranslateX(-150);
		button3.setTranslateY(150);

		Button button4 = new Button();
		button4.setTranslateX(0);
		button4.setTranslateY(300);

		Button buttonGCM = new Button();
		buttonGCM.setText("Chiffrage et Déchiffrage" + "\n" + "\t" +"\t" + "GCM");
		buttonGCM.setTranslateX(-150);
		buttonGCM.setTranslateY(-150);
		buttonGCM.setMaxSize(150,50);

		double totalPerformance = 0;

		buttonGCM.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				EncryptorAesGcmPasswordFile EncryptorAesGcmPasswordFile = new EncryptorAesGcmPasswordFile();
				try {
					//totalPerformance += EncryptorAesGcmPasswordFile.cryptDecrypt();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
				});
		StackPane root = new StackPane();
		root.getChildren().add(buttonGCM);
		root.getChildren().add(button1);
		root.getChildren().add(button2);
		root.getChildren().add(button3);
		root.getChildren().add(button4);
		root.getChildren().add(buttonPerf);



		buttonPerf.setText("Performance");
		Text perfEncryptor = new Text();
		perfEncryptor.setText("Performance en Cryptage : " + String.valueOf(totalPerformance) + " kilobytes/ms" + "\n" + "Performance en Decryptage : " +  " kilobytes/ms");
		perfEncryptor.setTranslateX(150);
		perfEncryptor.setTranslateY(150);

		Scene scene = new Scene(root, 800, 900);
		primaryStage.setTitle("Projet Cryptographie");
		primaryStage.setScene(scene);
		primaryStage.show();

		System.out.println(totalPerformance);
	}

	public static void main(String[] args) {
		launch(args);
	}

}

