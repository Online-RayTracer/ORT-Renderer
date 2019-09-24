import java.awt.Color;

public class linear_color {
    linear_color(float r, float g, float b) {
        this.r = r; this.g = g; this.b = b;
    }

    static linear_color lerp(linear_color a, linear_color b, float t) {
        return new linear_color(math.lerp(a.r, b.r, t), math.lerp(a.g, b.g, t), math.lerp(a.b, b.b, t));
    }

    Color to_color() {
        return new Color(r, g, b);
    }

    float r, g, b;
}
