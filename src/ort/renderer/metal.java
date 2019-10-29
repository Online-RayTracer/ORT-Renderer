package ort.renderer;

public class metal implements material {
    metal(linear_color a, float f) {
        albedo = a;
        fuzz = f;
    }

    @Override
    public boolean scatter(ray r_in, hit_record rec, linear_color attenuation, ray scattered) {
        vec3 reflected = math.reflect(r_in.dir.get_normal(), rec.normal);
        scattered.copy(new ray(rec.p, reflected.sum(math.random_in_unit_sphere().get_mul(fuzz))));
        attenuation.copy(albedo);
        return scattered.dir.dot(rec.normal) > 0;
    }

    linear_color albedo;
    float fuzz;
}
