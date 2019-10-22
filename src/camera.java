public class camera {
    camera() {
        lower_left_corner = new vec3(-2, -1, -1);
        horizontal = new vec3(4, 0, 0);
        vertical = new vec3(0, 2, 0);
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
