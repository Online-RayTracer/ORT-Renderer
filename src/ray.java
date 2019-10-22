public class ray {
    ray() {}
    ray(vec3 origin, vec3 dir) { this.origin = origin; this.dir = dir; }

    vec3 point_at(float t) { return origin.sum(dir.get_mul(t)); }

    void copy(ray src) {
        origin = src.origin;
        dir = src.dir;
    }

    vec3 origin, dir;
}
