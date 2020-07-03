package com.scaudachuang.campus_navigation.fx;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Manages switching Scenes on the Primary Stage
 */
public class StageManager {
	public static Stage myStage = new Stage();
	private final StackPane content = new StackPane();
	//private static final Logger LOG = getLogger(com.pdai.javafx.app.fx.StageManager.class);
	
	private final Stage primaryStage;

	private final SpringFXMLLoader springFXMLLoader;

	public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
		this.springFXMLLoader = springFXMLLoader;
		this.primaryStage = stage;
		
		// set decorator

	}
	
	/**
	 * replace content for primary stage
	 * 
	 * @param view
	 */
	public void switchScene(final FxmlView view) {
		Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.fxml());
		this.content.getChildren().add(viewRootNodeHierarchy);
	}
	
	/**
	 * replace content for pane
	 * 
	 * @param view
	 * @param body
	 */
	public void switchContent(final FxmlView view, ScrollPane body) {
		Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.fxml());
		body.setContent(viewRootNodeHierarchy);
	}
	
	/**
	 * show
	 * 
	 * @param view
	 */
	public void showPopWindow(final FxmlView view) {
		Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.fxml());
		
		Scene scene = prepareScene(viewRootNodeHierarchy);
		primaryStage.setTitle(view.title());
		primaryStage.setScene(scene);
		primaryStage.setHeight(600d);
		primaryStage.setWidth(1000d);
        primaryStage.centerOnScreen();
		try {
			primaryStage.show();
		} catch (Exception exception) {
			logAndExit("Unable to show scene for title" + view.title(), exception);
		}
	}

	private Scene prepareScene(Parent rootnode) {
		Scene scene = primaryStage.getScene();

		if (scene == null) {
			scene = new Scene(rootnode);
		}
		scene.setRoot(rootnode);
		return scene;
	}

	/**
	 * Loads the object hierarchy from a FXML document and returns to root node of
	 * that hierarchy.
	 *
	 * @return Parent root node of the FXML document hierarchy
	 */
	private Parent loadViewNodeHierarchy(String fxmlFilePath) {
		Parent rootNode = null;
		try {
			rootNode = springFXMLLoader.load(fxmlFilePath);
			Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
		} catch (Exception exception) {
			logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
		}
		return rootNode;
	}

	private void logAndExit(String errorMsg, Exception exception) {
		//LOG.error(errorMsg, exception, exception.getCause());
		Platform.exit();
	}

	/*public GNDecorator getDecorator() {
		return decorator;
	}

	public void closeAllPopups() {
		if (MainController.popConfig.isShowing())
			MainController.popConfig.hide();
		if (MainController.popup.isShowing())
			MainController.popup.hide();
	}
	*/
	public void show() {
		Scene myScene = new Scene(this.content);
		myStage.setScene(myScene);
		myStage.show();
	}

	public void close(){
		myStage.close();
	}

}
