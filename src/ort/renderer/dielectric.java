package ort.renderer;

public class dielectric implements material {
    dielectric(float ri) { ref_idx = ri; }

    @Override
    public boolean scatter(ray r_in, hit_record rec, linear_color out_attenuation, ray out_scattered) {
        vec3 outward_normal;
        vec3 reflected = math.reflect(r_in.dir, rec.normal);
        float ni_over_nt;
        out_attenuation.reset(1, 1, 1);
        vec3 refracted = new vec3();
        float reflect_prob;
        float cosine;
        if (r_in.dir.dot(rec.normal) > 0) {
            outward_normal = rec.normal.inverse();
            ni_over_nt = ref_idx;
            cosine = ref_idx * r_in.dir.dot(rec.normal) / r_in.dir.size();
        }
        else {
            outward_normal = rec.normal;
            ni_over_nt = 1 / ref_idx;
            cosine = -r_in.dir.dot(rec.normal) / r_in.dir.size();
        }
        if (math.refract(r_in.dir, outward_normal, ni_over_nt, refracted)) {
            reflect_prob = math.schlick(cosine, ref_idx);
        }
        else {
            out_scattered.copy(new ray(rec.p, reflected));
            reflect_prob = 1;
        }
        if ((float)Math.random() < reflect_prob) {
            out_scattered.copy(new ray(rec.p, reflected));
        }
        else {
            out_scattered.copy(new ray(rec.p, refracted));
        }
        return true;
    }

    float ref_idx;
}
