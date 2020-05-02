#include <iostream>
#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#include <png.h>


struct Complex {
    double a,b;
};

struct Coord {
    double x,y;
};

Complex add_complex(Complex x, Complex y) {
    struct Complex out = {x.a + y.a, x.b + y.b};
    return out;
}

Complex mult_complex(Complex x, Complex y) {
    struct Complex out = {x.a * y.a - x.b * y.b, x.a * y.b + x.b * y.a};
    return out;
}

double calc_dist(Complex x, Complex y) {
    return sqrt(pow(x.a-y.a,2) + pow(y.b-x.b, 2));
}

double calc_dist(Complex x) {
    return sqrt(pow(x.a,2) + pow(x.b, 2));
}

int calc_mandelbrot(struct Complex cord, int quality) {
    Complex z = {0, 0};
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

void gen_txt_mandelbrot(int x_resolution, int y_resolution, struct Coord center, double radius, int quality) {
    double x;
    double y;
    struct Complex coord;
    for(y = center.y + radius; y > center.y - radius; y -= (2 * radius) / y_resolution) {
        for(x = center.x - radius; x < center.x + radius; x += (2 * radius) / x_resolution) {
            coord = {x, y};
            if(calc_mandelbrot(coord, quality) == quality) {
                printf("*");
                fflush(stdout);
            } else {
                printf(" ");
            }
        }
        printf("\n");
    }
}

void gen_img_mandelbrot_ppm(char* filename, int x_resolution, int y_resolution, struct Coord center, double radius, int quality)  {

    FILE *fp = fopen("third.ppm", "wb"); /* b - binary mode */
    (void) fprintf(fp, "P6\n%d %d\n255\n", x_resolution, y_resolution);

    double x;
    double y;

    int longer = x_resolution > y_resolution ? x_resolution : y_resolution;

    struct Complex coord;
    for(y = center.y + radius; y > center.y - (radius*y_resolution)/longer; y -= (2 * radius) / y_resolution) {
        for(x = center.x - radius; x < center.x + (radius*x_resolution)/longer; x += (2 * radius) / x_resolution) {
            coord = {x, y};
            int value = calc_mandelbrot(coord, quality);
            static unsigned char color[3];
            color[0] = (value%255%50*50)+0;    /* red   */
            color[1] = (value%255%50*50)+100;  /* green */
            color[2] = (value%255%50*50)+150;  /* blue  */
            if(y == y_resolution || x == x_resolution) {
                printf("%f, %f", x, y);
            }
            (void) fwrite(color, 1, 3, fp);
        }
    }

    (void) fclose(fp);
    // return EXIT_SUCCESS;
}


void gen_img_mandelbrot_png(char* filename, int x_resolution, int y_resolution, struct Coord center, double radius, int quality)  {

    // int y;
    png_bytep *row_pointers = NULL;

    FILE *fp = fopen(filename, "wb");
    if(!fp) abort();

    png_structp png = png_create_write_struct(PNG_LIBPNG_VER_STRING, NULL, NULL, NULL);
    if (!png) abort();

    png_infop info = png_create_info_struct(png);
    if (!info) abort();

    if (setjmp(png_jmpbuf(png))) abort();

    png_init_io(png, fp);

    // Output is 8bit depth, RGBA format.
    png_set_IHDR(
        png,
        info,
        x_resolution, y_resolution,
        8,
        PNG_COLOR_TYPE_RGBA,
        PNG_INTERLACE_NONE,
        PNG_COMPRESSION_TYPE_DEFAULT,
        PNG_FILTER_TYPE_DEFAULT
    );
    png_write_info(png, info);

    // To remove the alpha channel for PNG_COLOR_TYPE_RGB format,
    // Use png_set_filler().
    //png_set_filler(png, 0, PNG_FILLER_AFTER);

    if (!row_pointers) abort();

    png_write_image(png, row_pointers);
    png_write_end(png, NULL);

    for(int y = 0; y < y_resolution; y++) {
        free(row_pointers[y]);
    }
    free(row_pointers);

    fclose(fp);

    png_destroy_write_struct(&png, &info);
}


int main() {

    const int X_RESOLUTION = 100;
    const int Y_RESOLUTION = 100;
    const int QUALITY = 100;
    const struct Coord CENTER = {0, 0};
    const double RADIUS = 2;
    char filename[] = "CCCCCCC_MANDELBROT";

    printf("~~~~~~~~~~~~~~~~~~\n");
    fflush(stdout);
    // gen_txt_mandelbrot(X_RESOLUTION, Y_RESOLUTION, CENTER, RADIUS, QUALITY);
    // gen_img_mandelbrot_ppm(filename, X_RESOLUTION, Y_RESOLUTION, CENTER, RADIUS, QUALITY);
    gen_img_mandelbrot_png(filename, X_RESOLUTION, Y_RESOLUTION, CENTER, RADIUS, QUALITY);
    printf("~~~~~~~~~~~~~~~~~~\n");
    fflush(stdout);
    return 0;
}
