import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ORTRenderer {
    public static void main(String[] args) {
        int nx = 200;
        int ny = 100;

        vec3 lower_left_corner = new vec3(-2, -1, -1);
        vec3 horizontal = new vec3(4, 0, 0);
        vec3 vertical = new vec3(0, 2, 0);
        vec3 origin = new vec3(0, 0, 0);

        BufferedImage image = new BufferedImage(nx, ny, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < ny; y++) {
            for (int x = 0; x < nx; x++) {
                float u = (float)x / nx;
                float v = 1 - (float)y / ny;
                ray r = new ray(origin, lower_left_corner.sum(horizontal.get_mul(u).sum(vertical.get_mul(v))));
                image.setRGB(x, y, get_color(r).getRGB());
            }
        }

        try {
            ImageIO.write(image, "png", new File("asdf.png"));
        } catch (IOException e) {}
    }

    static Color get_color(ray r) {
        vec3 dir = r.dir.get_normal();
        float t = .5f * (dir.y + 1);
        linear_color a = new linear_color(1, 1, 1);
        linear_color b = new linear_color(.5f, .7f, 1);
        return linear_color.lerp(a, b, t).to_color();
    }
}
