package ort.renderer;

public interface hitable {
    boolean hit(ray r, float t_min, float t_max, hit_record rec);
}
