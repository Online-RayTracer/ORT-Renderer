package ort.renderer;

public class camera {
    camera(vec3 lookfrom, vec3 lookat, vec3 vup, float vfov, float aspect, float aperture, float focus_dist) {
        lens_radius = aperture / 2;
        float theta = vfov * (float)Math.PI / 180;
        float half_height = (float)Math.tan(theta/2);
        float half_width = aspect * half_height;
        origin = lookfrom;
        w = lookfrom.diff(lookat).get_normal();
        u = vup.cross(w).get_normal();
        v = w.cross(u);
        lower_left_corner = origin.diff(u.get_mul(half_width*focus_dist)).diff(v.get_mul(half_height*focus_dist)).diff(w.get_mul(focus_dist));
        horizontal = u.get_mul(2*half_width*focus_dist);
        vertical = v.get_mul(2*half_height*focus_dist);
    }

    ray get_ray(float s, float t) {
        vec3 rd = math.random_in_unit_disk().get_mul(lens_radius);
        vec3 offset = u.get_mul(rd.x).sum(v.get_mul(rd.y));
        return new ray(origin.sum(offset), lower_left_corner.sum(horizontal.get_mul(s)).sum(vertical.get_mul(t)).diff(origin).diff(offset));
    }

    vec3 origin;
    vec3 lower_left_corner;
    vec3 horizontal;
    vec3 vertical;
    vec3 u, v, w;
    float lens_radius;
}
