import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ORTRenderer {
    public static void main(String[] args) {
        int nx = 512;
        int ny = 256;
        int ns = 1000;

        hitable list[] = new hitable[4];
        list[0] = new sphere(new vec3(0, 0, -1), .5f, new lambertian(new vec3(.8f, .3f, .3f)));
        list[1] = new sphere(new vec3(0, -100.5f, -1), 100, new lambertian(new vec3(.8f, .8f, 0)));
        list[2] = new sphere(new vec3(1, 0, -1), .5f, new metal(new vec3(.8f, .6f, .2f), 1));
        list[3] = new sphere(new vec3(-1, 0, -1), .5f, new metal(new vec3(.8f, .8f, .8f), .3f));

        hitable world = new hitable_list(list);
        camera cam = new camera();

        BufferedImage image = new BufferedImage(nx, ny, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < ny; y++) {
            for (int x = 0; x < nx; x++) {
                vec3 col = new vec3();

                // MSAA
                for (int s = 0; s < ns; ++s) {
                    float u = (x + (float)Math.random()) / nx;
                    float v = 1 - (y + (float)Math.random()) / ny;
                    ray r = cam.get_ray(u, v);
                    vec3 p = r.point_at(2);
                    col.add(get_color(r, world, 0));
                }
                col.div(ns);

                // gamma correction
                col.x = (float)Math.sqrt(col.x);
                col.y = (float)Math.sqrt(col.y);
                col.z = (float)Math.sqrt(col.z);

                image.setRGB(x, y, new Color(col.x, col.y, col.z).getRGB());
            }
        }

        try {
            ImageIO.write(image, "png", new File("asdf.png"));
        } catch (IOException e) {}
    }

    static vec3 get_color(ray r, hitable world, int depth) {
        hit_record rec = new hit_record();
        if (world.hit(r, .001f, Float.MAX_VALUE, rec)) {
            ray scattered = new ray();
            vec3 attenuation = new vec3();
            if (depth < 50 && rec.mat.scatter(r, rec, attenuation, scattered)) {
                return get_color(scattered, world, depth+1).get_mul(attenuation);
            }
            return new vec3(0, 0, 0);
        }

        // sky
        vec3 dir = r.dir.get_normal();
        float t = .5f * (dir.y + 1);
        vec3 a = new vec3(1, 1, 1);
        vec3 b = new vec3(.5f, .7f, 1);
        return a.lerp(b, t);
    }
}
