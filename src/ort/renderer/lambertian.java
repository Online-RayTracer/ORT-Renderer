package ort.renderer;

public class lambertian implements material {
    lambertian(linear_color a) {
        albedo = a;
    }

    @Override
    public boolean scatter(ray r_in, hit_record rec, linear_color attenuation, ray scattered) {
        vec3 target = rec.p.sum(rec.normal).sum(math.random_in_unit_sphere());
        scattered.copy(new ray(rec.p, target.diff(rec.p)));
        attenuation.copy(albedo);
        return true;
    }

    linear_color albedo;
}
