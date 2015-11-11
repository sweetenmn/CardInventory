package Creation;

import javafx.scene.image.Image;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by reedmershon on 11/3/15.
 */


public class ImageOpener {

    public Image cardImage;

    public Image open(String name) throws IOException {
        if (name.contains("Foil")) {
            name = name.substring(4, name.length() - 1);
        }
        String strippedName = name.replace(' ', '+');
        URL imgURL = new URL("http://gatherer.wizards.com/Handlers/Image.ashx?type=card&name=" + strippedName);

        InputStream inStream = imgURL.openStream();
        FileOutputStream outStream = new FileOutputStream("CardImage.jpg");

        byte[] b = new byte[2048];
        int len;

        while((len = inStream.read(b)) != -1) {outStream.write(b, 0, len);}
        if (inStream != null) {inStream.close();}
        if (outStream != null) {outStream.close();}

        cardImage = new Image("file:CardImage.jpg");

        return cardImage;
    }
}
