package App;

import Back.EncryptorAesGcmPasswordFile;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class App extends Application {

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
		button4.setTranslateX(20);
		button4.setTranslateY(300);

		Button button = new Button();
		button.setText("Chiffrage et Déchiffrage");
		button.setTranslateX(-150);
		button.setTranslateY(-150);
		button.setMaxSize(150,50);
		Text madeBy = new Text("Mohamed Kabbou");
		madeBy.setTranslateX(-150);
		madeBy.setTranslateY(-110);

		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {

				Text explain = new Text("You will be crypting the file TestData ");
				explain.setFont(new Font(15));

				StackPane secondaryLayout = new StackPane();
				secondaryLayout.getChildren().add(explain);
				Scene secondScene = new Scene(secondaryLayout, 900, 700);

				Button buttonRun = new Button();
				buttonRun.setText("Run");
				buttonRun.setTranslateY(50);
				buttonRun.setMaxSize(50,20);
				buttonRun.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent actionEvent) {
						EncryptorAesGcmPasswordFile EncryptorAesGcmPasswordFile = new EncryptorAesGcmPasswordFile();
						try {
							long result[] = EncryptorAesGcmPasswordFile.cryptDecrypt();
							Text perfEncryptor = new Text();
							perfEncryptor.setText("Performance en Cryptage : " + String.valueOf(result[0]) + " kilobytes/ms" + "\n" + "Performance en Decryptage : " + String.valueOf(result[1]) + " kilobytes/ms");
							perfEncryptor.setTranslateX(150);
							perfEncryptor.setTranslateY(150);
							secondaryLayout.getChildren().add(perfEncryptor);
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				});
				secondaryLayout.getChildren().add(buttonRun);
				// New window
				Stage newWindow = new Stage();
				newWindow.setTitle("Chiffrage et Déchiffrage");
				newWindow.setScene(secondScene);

				// Set position of second window, related to primary window.
				newWindow.setX(primaryStage.getX() + 300);
				newWindow.setY(primaryStage.getY() + 300);
				newWindow.show();
			}
		});
		StackPane root = new StackPane();
		root.getChildren().add(button);
		root.getChildren().add(button1);
		root.getChildren().add(button2);
		root.getChildren().add(button3);
		root.getChildren().add(button4);
		root.getChildren().add(madeBy);

		Scene scene = new Scene(root, 800, 900);
		primaryStage.setTitle("Projet Cryptographie");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}

