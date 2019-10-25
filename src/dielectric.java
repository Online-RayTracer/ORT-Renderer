public class dielectric implements material {
    dielectric(float ri) { ref_idx = ri; }

    @Override
    public boolean scatter(ray r_in, hit_record rec, vec3 out_attenuation, ray out_scattered) {
        vec3 outward_normal;
        vec3 reflected = math.reflect(r_in.dir, rec.normal);
        float ni_over_nt;
        out_attenuation.copy(new vec3(1, 1, 1));
        vec3 refracted = new vec3();
        if (r_in.dir.dot(rec.normal) > 0) {
            outward_normal = rec.normal.inverse();
            ni_over_nt = ref_idx;
        }
        else {
            outward_normal = rec.normal;
            ni_over_nt = 1 / ref_idx;
        }
        if (math.refract(r_in.dir, outward_normal, ni_over_nt, refracted)) {
            out_scattered.copy(new ray(rec.p, refracted));
        }
        else {
            out_scattered.copy(new ray(rec.p, reflected));
            return false;
        }
        return true;
    }

    float ref_idx;
}
