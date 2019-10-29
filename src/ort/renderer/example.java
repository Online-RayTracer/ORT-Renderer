/*
 * 이해를 돕기 위한 예제 코드입니다.
 */

package ort.renderer;

import java.util.ArrayList;

import static java.lang.Thread.currentThread;

public class example {
    public static void main(String[] args) {
        var start = System.currentTimeMillis();

        var renderer = new renderer();
        renderer.filepath = "image.png";
        renderer.width = 1280;
        renderer.height = 720;
        renderer.samples = 100;
        renderer.light_color = new linear_color(.9f, .9f, 1);

        var lookfrom = new vec3(-3,.5f,1);
        var lookat = new vec3(-.1f,-.1f,-1);
        var dist_to_focus = lookfrom.diff(lookat).size();
        var aperture = 0.f;
        renderer.cam = new camera(lookfrom, lookat, new vec3(0,1,0), 25, (float)renderer.width/renderer.height, aperture, dist_to_focus);

        var objects = new ArrayList<hitable>();
        objects.add(new sphere(new vec3(0,0,-1),.5f,new lambertian(new linear_color(.3f,.4f,1))));
        objects.add(new sphere(new vec3(0,-100.5f,-1),100,new lambertian(new linear_color(.5f,.5f,.5f))));
        objects.add(new sphere(new vec3(1,0,-.75f),.5f,new metal(new linear_color(.8f,.6f,.2f),.3f)));
        objects.add(new sphere(new vec3(-1,0,-1.1f),.5f,new dielectric(1.5f)));
        objects.add(new sphere(new vec3(-1,0,-1.1f),-.45f,new dielectric(1.5f)));
        renderer.world = new hitable_list(objects.toArray(new hitable[0]));

        var this_thread = currentThread();
        renderer.on_rendered = this_thread::interrupt;

        renderer.start_render_async();
        System.out.println("Rendering started...");

        while (true) {
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            float progress = renderer.get_progress();
            System.out.printf("Progress: %.2f%%\n", progress*100);
            if (progress >= 1) break;
        }
        System.out.println("Image rendered successfully!");

        var s = (System.currentTimeMillis()-start)/1000;
        System.out.printf("Time took: %d:%02d:%02d", s/3600, s%3600/60, s%60);
    }
}
