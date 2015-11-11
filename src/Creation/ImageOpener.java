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

    public Image CardImage;

    public Image Open(String name) throws IOException {
        if (name.contains("Foil")) {
            name = name.substring(4, name.length() - 1);
        }
        String strippedname = name.replace(' ', '+');
        URL imgURL = new URL("http://gatherer.wizards.com/Handlers/Image.ashx?type=card&name=" + strippedname);

        InputStream inStream = imgURL.openStream();
        FileOutputStream outStream = new FileOutputStream("CardImage.jpg");

        byte[] b = new byte[2048];
        int len;

        while((len = inStream.read(b)) != -1) {outStream.write(b, 0, len);}
        if (inStream != null) {inStream.close();}
        if (outStream != null) {outStream.close();}

        CardImage = new Image("file:CardImage.jpg");

        return CardImage;
    }
}
