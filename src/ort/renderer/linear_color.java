package ort.renderer;

import java.awt.*;

/**
 * 선형 RGBA 색상을 나타냅니다.
 * 각 RGBA 컴포넌트는 0~1 사이의 float 값입니다.
 * A는 Alpha, 불투명도를 나타냅니다. 1이면 불투명, 0이면 투명입니다.
 */
public class linear_color {
    float r, g, b;

    linear_color() {}
    linear_color(linear_color o) {copy(o);}
    linear_color(float r, float g, float b) {this.r=r;this.g=g;this.b=b;}

    void copy(linear_color o) {r=o.r;g=o.g;b=o.b;}
    void reset(float r, float g, float b) {this.r=r;this.g=g;this.b=b;}

    void add(linear_color c) { r += c.r; g += c.g; b += c.b; }
    linear_color sum(linear_color c) { var ret = new linear_color(this); ret.add(c); return ret; }

    void mul(float f) { r *= f; g *= f; b *= f; }
    void mul(linear_color o) { r *= o.r; g *= o.g; b *= o.b; }
    linear_color get_mul(float f) { var ret = new linear_color(this); ret.mul(f); return ret; }
    linear_color get_mul(linear_color o) { var ret = new linear_color(this); ret.mul(o); return ret; }

    linear_color lerp(linear_color b, float t) { return get_mul(1-t).sum(b.get_mul(t)); }

    void gamma_correct() {
        r = (float)Math.sqrt(r);
        g = (float)Math.sqrt(g);
        b = (float)Math.sqrt(b);
    }

    Color to_color() { return new Color(r, g, b); }
}
