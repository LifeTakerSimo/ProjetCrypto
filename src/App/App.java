/*package App;

import Back.EncryptorAesGcmPasswordFile;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class App extends Application {

	private static double totalPerformance = 0;

	/**
	 * application
	 * @param primaryStage
	 */
	public void start(final Stage primaryStage) {

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


		buttonGCM.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				EncryptorAesGcmPasswordFile EncryptorAesGcmPasswordFile = new EncryptorAesGcmPasswordFile();
				try {
					double perfGCM = EncryptorAesGcmPasswordFile.cryptDecrypt();
					App.totalPerformance += perfGCM;
					System.out.println(totalPerformance);

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

		Button buttonPerf = new Button();
		buttonPerf.setTranslateX(0);
		buttonPerf.setTranslateY(0);

		root.getChildren().add(buttonPerf);
		buttonPerf.setText("Performance");

		Label perfValue = new Label();
		buttonPerf.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				perfValue.setText("Performance en Cryptage : " + Double.toString(totalPerformance) + " kilobytes/ms" );
				root.getChildren().add(perfValue);
				perfValue.setTranslateX(0);
				perfValue.setTranslateY(400);

			}
		});
		Button Clean = new Button();
		Clean.setText("Clean");
		Clean.setTranslateX(200);
		Clean.setTranslateY(400);
		Clean.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				root.getChildren().remove(perfValue);
			}
		});
		root.getChildren().add(Clean);
		Scene scene = new Scene(root, 800, 900);
		primaryStage.setTitle("Projet Cryptographie");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

}

*/