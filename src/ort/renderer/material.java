package ort.renderer;

public interface material {
    boolean scatter(ray r_in, hit_record rec, linear_color out_attenuation, ray out_scattered);
}
