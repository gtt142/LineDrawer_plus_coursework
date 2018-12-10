/*  
  Lines generator in .mf format
  "x0 y0 x1 y1" /double
  
  SYNOPSIS: program_name [lines_count]
*/

#include <stdio.h>
#include <time.h>
#include <stdlib.h>

int main(int argc, char* argv[]) {
    int width = 800;
    int height = 600;
    int count = 10;

    if (argc > 1) {
        sscanf(argv[1], "%d", &count);
        if(count < 0 || count > 1e6)
            count = 10;
    }

    srand(time(NULL));
    for (int i = 0; i < count; i++) {
        int x0 = rand() % (width+1);
        int y0 = rand() % (height+1);
        int x1 = rand() % (width+1);
        int y1 = rand() % (height+1); 
        printf("%d.0 %d.0 %d.0 %d.0\n", x0, y0, x1, y1);
    }

    return 0;
}
