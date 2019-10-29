package ort.renderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ORTRenderer {
    String filepath;
    int width, height;
    int samples;
    camera cam;
    linear_color light_color;
    hitable_list world;
    on_rendered on_rendered;

    public void start_render_async() {
        new Thread(()->render()).start();
    }

    public float get_progress() {
        return Math.min(1.f, (float)(width*y + x) / (width*height));
    }


    private int x, y;

    private void render() {
        var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (y = 0; y < height; ++y) {
            for (x = 0; x < width; ++x) {
                var col = new linear_color();

                for (var s = 0; s < samples; ++s) {
                    var u = (x + math.rand()) / width;
                    var v = 1 - (y + math.rand()) / height;
                    var r = cam.get_ray(u, v);
                    col.add(get_color(r, 0));
                }

                col.mul(1.f/samples);
                col.gamma_correct();
                image.setRGB(x, y, col.to_color().getRGB());
            }
        }
        try {
            ImageIO.write(image, "png", new File(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        on_rendered.execute();
    }

    private linear_color get_color(ray r, int depth) {
        hit_record rec = new hit_record();
        if (world.hit(r, .001f, Float.MAX_VALUE, rec)) {
            var scattered = new ray();
            var attenuation = new linear_color();
            if (depth < 50 && rec.mat.scatter(r, rec, attenuation, scattered)) {
                return get_color(scattered, depth+1).get_mul(attenuation);
            }
            return new linear_color(0, 0, 0);
        }

        return light_color;
    }
}
