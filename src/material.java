public interface material {
    boolean scatter(ray r_in, hit_record rec, vec3 out_attenuation, ray out_scattered);
}
