public class math {
    static float lerp(float a, float b, float t) {
        return (1-t)*a + t*b;
    }

    static vec3 random_in_unit_sphere() {
        vec3 p = new vec3();
        do {
            p.x = (float)Math.random() * 2 - 1;
            p.y = (float)Math.random() * 2 - 1;
            p.z = (float)Math.random() * 2 - 1;
        } while (p.size_sqr() >= 1);
        return p;
    }

    static vec3 reflect(vec3 v, vec3 n) {
        return v.diff(n.get_mul(2*v.dot(n)));
    }

    static boolean refract(vec3 v, vec3 n, float ni_over_nt, vec3 out_refracted) {
        vec3 uv = v.get_normal();
        float dt = uv.dot(n);
        float discriminant = 1 - ni_over_nt*ni_over_nt*(1-dt*dt);
        if (discriminant > 0) {
            out_refracted.copy(uv.diff(n.get_mul(dt)).get_mul(ni_over_nt).diff(n.get_mul((float)Math.sqrt(discriminant))));
            return true;
        }
        return false;
    }

    static float schlick(float cosine, float ref_idx) {
        float r0 = (1-ref_idx) / (1+ref_idx);
        r0 *= r0;
        return r0 + (1 - r0) * (float)Math.pow(1 - cosine, 5);
    }

    static vec3 random_in_unit_disk() {
        vec3 p = new vec3();
        do {
            p.x = (float)Math.random() * 2 - 1;
            p.y = (float)Math.random() * 2 - 1;
        } while (p.dot(p) >= 1);
        return p;
    }
}
