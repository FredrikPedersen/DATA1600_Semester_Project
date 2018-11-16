package goty.graphics;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static goty.controllers.GameController.canvasWidth;

/**
 * <h1>Menu Animation</h1>
 * <p>Statiske metoder for animasjon i meny</p>
 *
 * @author Erik Larsen
 * @version 1.0
 * @since 24. april 2018
 */


public class MenuAnimation  {


    /**
     * fade in, opacity fra 0 til 1,
     * @param duration lengde på effekten
     * @param node hvilken node som skal "fade" in
     */
    public static void fadeIn(Duration duration, Node node) {
        FadeTransition ft = new FadeTransition(duration, node);
        ft.setToValue(1);
        ft.setFromValue(0);
        ft.play();
    }

    /**
     * bilde spinner og blir mindre
     * @param image gitt bilde
     * @param eventHandler handler for hva som skal etter animasjonen er fulført.
     */

    public static void enterGameAnimation(ImageView image, EventHandler<javafx.event.ActionEvent> eventHandler) {
        image.setVisible(true);
        image.setFitWidth(canvasWidth);
        ScaleTransition st = new ScaleTransition();
        st.setFromX(1); st.setFromY(1);
        st.setToX(0); st.setToY(0);
        st.setCycleCount(1);
        st.setDuration(Duration.seconds(2));


        RotateTransition rt = new RotateTransition();
        rt.setByAngle(720);
        rt.setDuration(Duration.seconds(2));
        rt.setCycleCount(1);
        rt.setInterpolator(Interpolator.LINEAR);

        ParallelTransition pt = new ParallelTransition();
        pt.setNode(image);
        pt.getChildren().addAll(st, rt);
        pt.setCycleCount(1);
        pt.play();
        pt.setOnFinished(eventHandler);
    }




}
