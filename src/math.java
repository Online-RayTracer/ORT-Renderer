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
}
