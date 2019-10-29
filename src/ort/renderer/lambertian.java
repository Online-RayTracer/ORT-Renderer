public class lambertian implements material {
    lambertian(vec3 a) {
        albedo = a;
    }

    @Override
    public boolean scatter(ray r_in, hit_record rec, vec3 attenuation, ray scattered) {
        vec3 target = rec.p.sum(rec.normal).sum(math.random_in_unit_sphere());
        scattered.copy(new ray(rec.p, target.diff(rec.p)));
        attenuation.copy(albedo);
        return true;
    }

    vec3 albedo;
}
