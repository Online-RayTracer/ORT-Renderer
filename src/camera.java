public class camera {
    camera(float vfov, float aspect) {
        float theta = vfov * (float)Math.PI / 180;
        float half_height = (float)Math.tan(theta/2);
        float half_width = aspect * half_height;
        lower_left_corner = new vec3(-half_width, -half_height, -1);
        horizontal = new vec3(2*half_width, 0, 0);
        vertical = new vec3(0, 2*half_height, 0);
        origin = new vec3(0, 0, 0);
    }

    ray get_ray(float u, float v) {
        return new ray(origin, lower_left_corner.sum(horizontal.get_mul(u)).sum(vertical.get_mul(v)).diff(origin));
    }

    vec3 origin;
    vec3 lower_left_corner;
    vec3 horizontal;
    vec3 vertical;
}
