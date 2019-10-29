public class metal implements material {
    metal(vec3 a, float f) {
        albedo = a;
        fuzz = f;
    }

    @Override
    public boolean scatter(ray r_in, hit_record rec, vec3 attenuation, ray scattered) {
        vec3 reflected = math.reflect(r_in.dir.get_normal(), rec.normal);
        scattered.copy(new ray(rec.p, reflected.sum(math.random_in_unit_sphere().get_mul(fuzz))));
        attenuation.copy(albedo);
        return scattered.dir.dot(rec.normal) > 0;
    }

    vec3 albedo;
    float fuzz;
}
