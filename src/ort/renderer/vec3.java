package ort.renderer;

public class vec3 {
    vec3() { x = 0; y = 0; z = 0; }
    vec3(float x1, float y1, float z1) { x = x1; y = y1; z = z1; }
    vec3(vec3 o) { x = o.x; y = o.y; z = o.z; }

    void invert() { x = -x; y = -y; z = -z; }
    vec3 inverse() { vec3 v = new vec3(this); v.invert(); return v; }

    void add(vec3 o) { x += o.x; y += o.y; z += o.z; }
    vec3 sum(vec3 o) { vec3 v = new vec3(this); v.add(o); return v; }

    void sub(vec3 o) { x -= o.x; y -= o.y; z -= o.z; }
    vec3 diff(vec3 o) { vec3 v = new vec3(this); v.sub(o); return v; }

    void mul(vec3 o) { x *= o.x; y *= o.y; z *= o.z; }
    vec3 get_mul(vec3 o) { vec3 v = new vec3(this); v.mul(o); return v; }

    void mul(float t) { x *= t; y *= t; z *= t; }
    vec3 get_mul(float t) { vec3 v = new vec3(this); v.mul(t); return v; }

    void div(vec3 o) { x /= o.x; y /= o.y; z /= o.z; }
    vec3 get_div(vec3 o) { vec3 v = new vec3(this); v.div(o); return v; }

    void div(float t) { x /= t; y /= t; z /= t; }
    vec3 get_div(float t) { vec3 v = new vec3(this); v.div(t); return v; }

    float dot(vec3 o) { return x*o.x + y*o.y + z*o.z; }
    vec3 cross(vec3 o) { return new vec3(y*o.z - z*o.y, z*o.x - x*o.z, x*o.y - y*o.x); }

    float size() { return (float)Math.sqrt(size_sqr()); }
    float size_sqr() { return x*x + y*y + z*z; }

    void normalize() { div(size()); }
    vec3 get_normal() { vec3 v = new vec3(this); v.normalize(); return v; }

    vec3 lerp(vec3 b, float t) {
        return get_mul(1-t).sum(b.get_mul(t));
    }

    void copy(vec3 src) {
        x = src.x;
        y = src.y;
        z = src.z;
    }

    float x, y, z;
}
