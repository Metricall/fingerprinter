/*
 * Metrical Finterprint thing
 */
package metricalfingerprint;

import java.lang.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
/**
 *
 * @author jwu
 */
public class MetricalFingerprint extends Application {
    
    @Override public void start(Stage primaryStage) throws Exception {
        Pane root = new FingerprintPane();
        primaryStage.setMaximized(true);
        //primaryStage.setFullScreen(true);
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public class FingerprintPane extends Pane {

        public FingerprintPane() {
            VBox.setVgrow(this, Priority.ALWAYS);
            setMaxWidth(Double.MAX_VALUE);
            setMaxHeight(Double.MAX_VALUE);

            WebView view = new WebView();
            view.setContextMenuEnabled(false);
            view.addEventFilter(KeyEvent.ANY, KeyEvent::consume);
            view.setMinSize(500, 400);
            view.setPrefSize(500, 400);
            final WebEngine eng = view.getEngine();
            eng.load("http://metrical.name/findex.php");
            final TextField userid = new TextField("");
            userid.setPromptText("fill in identity");
            userid.setMaxHeight(Double.MAX_VALUE);
            final TextField loc = new TextField("");
            loc.setPromptText("location here");
            loc.setMaxHeight(Double.MAX_VALUE);
            Button goButton = new Button("Go");
            goButton.setDefaultButton(true);
            EventHandler<ActionEvent> goAction = new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent event) {
                    eng.load("http://metrical.name/finger.php?Identity=" + userid.getText() + "&Location=" + loc.getText() );
                }
            };
            goButton.setOnAction(goAction);
//            userid.setOnAction(goAction);
//            eng.locationProperty().addListener(new ChangeListener<String>() {
//               @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                    userid.setText(newValue);
//                }
//            });
            GridPane grid = new GridPane();
            grid.setVgap(5);
            grid.setHgap(5);
            GridPane.setConstraints(userid, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.SOMETIMES);
            GridPane.setConstraints(loc, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.SOMETIMES);
            GridPane.setConstraints(goButton,1,0);
            GridPane.setConstraints(view, 0, 2, 2, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
            grid.getColumnConstraints().addAll(
                    new ColumnConstraints(100, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true),
                    new ColumnConstraints(40, 40, 40, Priority.NEVER, HPos.CENTER, true)
            );
            grid.getChildren().addAll(userid, loc, goButton, view);
            getChildren().add(grid);
        }

        @Override protected void layoutChildren() {
            List<Node> managed = getManagedChildren();
            double width = getWidth();
            double height = getHeight();
            double top = getInsets().getTop();
            double right = getInsets().getRight();
            double left = getInsets().getLeft();
            double bottom = getInsets().getBottom();
            for (int i = 0; i < managed.size(); i++) {
                Node child = managed.get(i);
                layoutInArea(child, left, top,
                               width - left - right, height - top - bottom,
                               0, Insets.EMPTY, true, true, HPos.CENTER, VPos.CENTER);
            }
        }
    }
}
