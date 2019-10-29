package ort.renderer;

public class sphere implements hitable {
    public sphere() {}
    public sphere(vec3 cen, float r, material m) {
        center = cen;
        radius = r;
        mat = m;
    }

    @Override
    public boolean hit(ray r, float t_min, float t_max, hit_record rec) {
        vec3 oc = r.origin.diff(center);
        float a = r.dir.dot(r.dir);
        float b = oc.dot(r.dir);
        float c = oc.dot(oc) - radius*radius;
        float discriminant = b*b - a*c;
        if (discriminant > 0) {
            ray_check check = (d_sqrt) -> {
                float temp = (-b + d_sqrt) / a;
                if (temp < t_max && temp > t_min) {
                    rec.t = temp;
                    rec.p = r.point_at(rec.t);
                    rec.normal = rec.p.diff(center).get_div(radius);
                    rec.mat = mat;
                    return true;
                }
                return false;
            };
            float d_sqrt = (float)Math.sqrt(discriminant);
            if (check.check(-d_sqrt)) return true;
            if (check.check(d_sqrt)) return true;
        }
        return false;
    }

    public vec3 center;
    public float radius;
    public material mat;
}

@FunctionalInterface
interface ray_check {
    boolean check(float d_sqrt);
}
