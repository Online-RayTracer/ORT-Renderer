import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ORTRenderer {
    public static void main(String[] args) {
        int nx = 200;
        int ny = 100;
        int ns = 100;

        hitable list[] = new hitable[2];
        list[0] = new sphere(new vec3(0, 0, -1), .5f);
        list[1] = new sphere(new vec3(0, -100.5f, -1), 100);

        hitable world = new hitable_list(list);
        camera cam = new camera();

        BufferedImage image = new BufferedImage(nx, ny, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < ny; y++) {
            for (int x = 0; x < nx; x++) {
                vec3 col = new vec3();
                for (int s = 0; s < ns; ++s) {
                    float u = (x + (float)Math.random()) / nx;
                    float v = 1 - (y + (float)Math.random()) / ny;
                    ray r = cam.get_ray(u, v);
                    vec3 p = r.point_at(2);
                    col.add(get_color(r, world));
                }
                col.div(ns);
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

    static vec3 get_color(ray r, hitable world) {
        hit_record rec = new hit_record();
        if (world.hit(r, .001f, Float.MAX_VALUE, rec)) {
            vec3 target = rec.p.sum(rec.normal).sum(math.random_in_unit_sphere());
            return get_color(new ray(rec.p, target.diff(rec.p)), world).get_mul(.5f);
        }

        vec3 dir = r.dir.get_normal();
        float t = .5f * (dir.y + 1);
        vec3 a = new vec3(1, 1, 1);
        vec3 b = new vec3(.5f, .7f, 1);
        return a.lerp(b, t);
    }
}
