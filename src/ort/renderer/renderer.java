package ort.renderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class renderer {
    String filepath;
    int width, height;
    int samples;
    camera cam;
    linear_color light_color;
    hitable_list world;
    on_rendered on_rendered;

    public void start_render_async() {
        new Thread(this::render).start();
    }

    public float get_progress() {
        return (float)(y*width + x) / (width*height);
    }


    private int x, y;

    private void render() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (y = 0; y < height; ++y) {
            for (x = 0; x < width; ++x) {
                linear_color col = new linear_color();

                for (int s = 0; s < samples; ++s) {
                    float u = (x + math.rand()) / width;
                    float v = 1 - (y + math.rand()) / height;
                    ray r = cam.get_ray(u, v);
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
            ray scattered = new ray();
            linear_color attenuation = new linear_color();
            if (depth < 50 && rec.mat.scatter(r, rec, attenuation, scattered)) {
                return get_color(scattered, depth+1).get_mul(attenuation);
            }
            return new linear_color(0, 0, 0);
        }

        return light_color;
    }
}
