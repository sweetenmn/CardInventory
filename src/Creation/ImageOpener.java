package Creation;

import application.Card;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import java.io.IOException;
import java.net.URL;

/**
 * Created by reedmershon on 11/3/15.
 */


public class ImageOpener {

    public BackgroundImage CardImage;

    int backgroundWidth = 149;
    int backgroundHeight = 207;

    private BackgroundSize BackSize = new BackgroundSize(backgroundWidth, backgroundHeight, false, false, true, false);

    public BackgroundImage Open(String input) throws IOException {
        URL imgURL = new URL("http://gatherer.wizards.com/Handlers/Image.ashx?type=card&name=" + input);
        Image background = new Image(imgURL.toString());
        CardImage = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackSize);

        return CardImage;
    }
}
