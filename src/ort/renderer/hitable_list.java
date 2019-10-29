package ort.renderer;

public class hitable_list implements hitable {
    public hitable_list() {}
    public hitable_list(hitable[] l) {
        list = l;
    }

    @Override
    public boolean hit(ray r, float t_min, float t_max, hit_record rec) {
        boolean hit_anything = false;
        float closest_so_far = t_max;
        for (int i = 0; i < list.length; ++i) {
            if (list[i].hit(r, t_min, closest_so_far, rec)) {
                hit_anything = true;
                closest_so_far = rec.t;
            }
        }
        return hit_anything;
    }

    public hitable[] list;
}
