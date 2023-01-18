package App;

import Back.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Formatter;


public class App extends Application {

	private static final String DBL_FMT = "##.####";
	private static double totalPerformance = 0;

	/**
	 * application
	 * @param primaryStage
	 */
	public void start(final Stage primaryStage) {

		Button buttonDSA = new Button();
		buttonDSA.setText("Signature electronique" + "\n" + "\t" +"\t" + "DSA");
		buttonDSA.setTranslateX(150);
		buttonDSA.setTranslateY(150);
		buttonDSA.setMaxSize(160,50);

		buttonDSA.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				SignatureElec DSA = new SignatureElec();
				try {
					double perfDSA = DSA.signVerify();
					App.totalPerformance += perfDSA;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});

		Button buttonMAC = new Button();
		buttonMAC.setText("Message authentication code" + "\n" + "\t" +"\t" + "MAC");
		buttonMAC.setTranslateX(150);
		buttonMAC.setTranslateY(-150);
		buttonMAC.setMaxSize(160,50);

		buttonMAC.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				MessageAuthenticationCode MessageAuthenticationCode = new MessageAuthenticationCode();
				try {
					double perfMAC = MessageAuthenticationCode.MacPerformance();
					App.totalPerformance += perfMAC;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});


		Button buttonHash = new Button();
		buttonHash.setText("Hashage et verification" + "\n" + "\t" +"\t" + "SHA");
		buttonHash.setTranslateX(-150);
		buttonHash.setTranslateY(150);
		buttonHash.setMaxSize(150,50);

		Button buttonAsym = new Button();
		buttonAsym.setText("Chiffrage et déchiffrage"+"\n"+"\t"+"\t"+"Asymétrique");
		buttonAsym.setTranslateX(0);
		buttonAsym.setTranslateY(250);
		buttonAsym.setMaxSize(150,50);

		buttonAsym.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				EncryptPublicKey EncryptPublicKey = new EncryptPublicKey();
				try {
					double perfAsym = EncryptPublicKey.encryptDecrypt();
					App.totalPerformance += perfAsym;
					System.out.println(totalPerformance);

				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});

		Button buttonGCM = new Button();
		buttonGCM.setText("Chiffrage et Dechiffrage" + "\n" + "\t" +"\t" + "GCM");
		buttonGCM.setTranslateX(-150);
		buttonGCM.setTranslateY(-150);
		buttonGCM.setMaxSize(150,50);

		buttonGCM.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				EncryptorAesGcmPasswordFile EncryptorAesGcmPasswordFile = new EncryptorAesGcmPasswordFile();
				try {
					double perfGCM = EncryptorAesGcmPasswordFile.cryptDecrypt();
					App.totalPerformance += perfGCM;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});

		buttonHash.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Hash hash = new Hash();
				try {
					double perfHash = hash.hashVerify();
					totalPerformance += perfHash;
					Formatter fm=new Formatter();
					fm.format("%.4f", totalPerformance);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});

		StackPane root = new StackPane();
		root.getChildren().add(buttonGCM);
		root.getChildren().add(buttonDSA);
		root.getChildren().add(buttonMAC);
		root.getChildren().add(buttonHash);
		root.getChildren().add(buttonAsym);

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
				perfValue.setTranslateY(300);
			}
		});
		Button Clean = new Button();
		Clean.setText("Clean");
		Clean.setTranslateX(200);
		Clean.setTranslateY(300);
		Clean.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				root.getChildren().remove(perfValue);
			}
		});
		root.getChildren().add(Clean);
		Scene scene = new Scene(root, 600, 700);
		primaryStage.setTitle("Projet Cryptographie");
		primaryStage.setScene(scene);
		primaryStage.show();



	}
	public static void main(String[] args) {
		launch(args);
	}

}

