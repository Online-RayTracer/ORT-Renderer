public class camera {
    camera(vec3 lookfrom, vec3 lookat, vec3 vup, float vfov, float aspect) {
        vec3 u, v, w;
        float theta = vfov * (float)Math.PI / 180;
        float half_height = (float)Math.tan(theta/2);
        float half_width = aspect * half_height;
        origin = lookfrom;
        w = lookfrom.diff(lookat).get_normal();
        u = vup.cross(w).get_normal();
        v = w.cross(u);
        lower_left_corner = origin.diff(u.get_mul(half_width)).diff(v.get_mul(half_height)).diff(w);
        horizontal = u.get_mul(2*half_width);
        vertical = v.get_mul(2*half_height);
    }

    ray get_ray(float u, float v) {
        return new ray(origin, lower_left_corner.sum(horizontal.get_mul(u)).sum(vertical.get_mul(v)).diff(origin));
    }

    vec3 origin;
    vec3 lower_left_corner;
    vec3 horizontal;
    vec3 vertical;
}
