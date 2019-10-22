public class metal implements material {
    metal(vec3 a) {
        albedo = a;
    }

    @Override
    public boolean scatter(ray r_in, hit_record rec, vec3 attenuation, ray scattered) {
        vec3 reflected = math.reflect(r_in.dir.get_normal(), rec.normal);
        scattered.copy(new ray(rec.p, reflected));
        attenuation.copy(albedo);
        return scattered.dir.dot(rec.normal) > 0;
    }

    vec3 albedo;
}
