package GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.*;
import java.util.Base64;
import javax.imageio.ImageIO;

public class first_frame_settings_1 {
    public static final String FOLDER = "data/profile_images";
    public static Path getImagePath(String username) {
        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(username.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        return Paths.get(FOLDER, encoded + ".png");
    }

    public static String saveImage(String username, String sourcePath) {
        try {
            Files.createDirectories(Paths.get(FOLDER));
            BufferedImage image = ImageIO.read(new File(sourcePath));

            if (image == null) {
                return null;
            }

            Path savePath = getImagePath(username);
            ImageIO.write(image, "png", savePath.toFile());

            return savePath.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String loadImage(String username) {
        Path path = getImagePath(username);

        try {
            if (Files.exists(path)) {

                BufferedImage image = ImageIO.read(path.toFile());

                if (image != null) {
                    return path.toString();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "image/user3.png";
    }
}
