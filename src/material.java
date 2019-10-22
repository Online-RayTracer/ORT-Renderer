public interface material {
    boolean scatter(ray r_in, hit_record rec, vec3 attenuation, ray scattered);
}
