package net.agspace;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Andrew Lalis
 * Class to generate images of tengwar text to fit certain criteria.
 */
public class TengwarImageGenerator {

    /**
     * Generates an image of some tengwar text. The text is wrapped so that each line may not take up more than
     * {@code maxWidth} pixels.
     * @param tengwarText The string of tengwar text to transform into an image.
     * @param maxWidth The maximum width, in pixels, that the generated image can be.
     * @param fontSize The size of the font to use.
     * @param bold Whether or not the font should be bold.
     * @param italic Whether or not the font should be italic.
     * @return An Image Object containing the drawn text.
     */
    public static Image generateImage(String tengwarText, int maxWidth, int fontSize, boolean bold, boolean italic) {
        BufferedImage bufferedImage = new BufferedImage(maxWidth, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D)bufferedImage.getGraphics();
        Color backgroundColor = new Color(54, 57, 62);
        Font tengwarFont = getTengwarFont(bold, italic);
        tengwarFont = tengwarFont.deriveFont(fontSize);
        graphics.setFont(tengwarFont);
        FontMetrics fm = graphics.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds(tengwarText, graphics);
        graphics.setColor(backgroundColor);
        graphics.fillRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
        graphics.setColor(Color.white);
        graphics.drawString(tengwarText, 0, fm.getHeight());
        try {
            ImageIO.write(bufferedImage, "png", new File("C:\\Users\\AndrewComputer\\Documents\\Programming\\IntelliJ_Projects"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (Image)bufferedImage;
    }

    /**
     * Returns the tngan.ttf font loaded into a Java Font.
     * @param bold Whether or not font should be bold.
     * @param italic Whether or not font should be italic.
     * @return The Font loaded, or null.
     */
    public static Font getTengwarFont(boolean bold, boolean italic){
        Font font = null;
        try {
            String filename = null;
            if (bold && italic){
                filename = "resources/tnganbi.ttf";
            } else if (bold){
                filename = "resources/tnganb.ttf";
            } else if (italic){
                filename = "resources/tngani.ttf";
            } else {
                filename = "resources/tngan.tff";
            }
            font = Font.createFont(Font.TRUETYPE_FONT, TengwarImageGenerator.class.getClassLoader().getResourceAsStream(filename));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return font;
    }

}
