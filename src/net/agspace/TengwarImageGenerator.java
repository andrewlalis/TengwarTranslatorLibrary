package net.agspace;

import javax.imageio.ImageIO;
import java.awt.*;
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
    public static File generateImage(String tengwarText, int maxWidth, float fontSize, boolean bold, boolean italic, String filepath) {
        BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        Font tengwarFont = getTengwarFont(bold, italic, fontSize);
        graphics.setFont(tengwarFont);
        FontMetrics fm = graphics.getFontMetrics();
        int width = fm.stringWidth(tengwarText);
        int height = fm.getHeight();
        graphics.dispose();

        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphics = bufferedImage.createGraphics();
        graphics.setBackground(new Color(54, 57, 62));
        graphics.clearRect(0, 0, width, height);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics.setColor(Color.white);
        graphics.setFont(tengwarFont);
        graphics.drawString(tengwarText, 0, fm.getAscent());
        graphics.dispose();

        try {
            File file = new File(filepath);
            ImageIO.write(bufferedImage, "png", file);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the tngan.ttf font loaded into a Java Font.
     * @param bold Whether or not font should be bold.
     * @param italic Whether or not font should be italic.
     * @param size The font size, in pt.
     * @return The Font loaded, or null.
     */
    public static Font getTengwarFont(boolean bold, boolean italic, float size){
        Font font = null;
        try {
            String filename;
            if (bold && italic){
                filename = "tnganbi.ttf";
            } else if (bold){
                filename = "tnganb.ttf";
            } else if (italic){
                filename = "tngani.ttf";
            } else {
                filename = "tngan.ttf";
            }
            font = Font.createFont(Font.TRUETYPE_FONT, TengwarImageGenerator.class.getClassLoader().getResourceAsStream(filename)).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return font;
    }

}
