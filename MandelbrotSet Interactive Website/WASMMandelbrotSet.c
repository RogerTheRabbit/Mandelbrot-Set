// Compiled using https://webassembly.studio/

#include <math.h>

#define WASM_EXPORT __attribute__((visibility("default")))

struct Complex {
    double a,b;
};

struct Complex add_complex(struct Complex x, struct Complex y) {
    struct Complex out = {x.a + y.a, x.b + y.b};
    return out;
}

struct Complex mult_complex(struct Complex x, struct Complex y) {
    struct Complex out = {x.a * y.a - x.b * y.b, x.a * y.b + x.b * y.a};
    return out;
}

double calc_dist(struct Complex x) {
    return sqrt(pow(x.a,2) + pow(x.b, 2));
}

WASM_EXPORT
int calc_mandelbrot(double x_cord, double y_cord, int quality) {
    struct Complex cord = {x_cord, y_cord};
    struct Complex z = {0, 0};
    int x;
    for(x = 0; x < quality; x++) {
        z = mult_complex(z, z);
        z = add_complex(z, cord);
        if(calc_dist(z) > 2) {
            return x;
        }
    }
    return quality;
}
