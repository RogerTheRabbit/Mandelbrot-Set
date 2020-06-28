
let instance;
fetch('./main.wasm').then(response =>
        response.arrayBuffer()
    ).then(bytes => WebAssembly.instantiate(bytes)).then(results => {
        instance = results.instance;

        lastRender = {
            "centerA": 0,
            "centerB": 0,
            "radius": 2,
            "quality": 100
        };

        // document.getElementById("x coordinate").value = lastRender.centerA;
        // document.getElementById("y coordinate").value = lastRender.centerB
        // document.getElementById("radius").value = lastRender.radius;
        // document.getElementById("iterations").value = lastRender.quality;

        // render(lastRender.centerA, lastRender.centerB, lastRender.radius, lastRender.quality);
    }).catch(console.error);

function calcPoint(x, y, qual) {
    return instance.exports.calc_mandelbrot(x,y,qual);
}


onmessage = function(e) {

    centerA = e.data[0];
    centerB = e.data[1];
    radius = e.data[2];
    quality = e.data[3];
    width = e.data[4]
    height = e.data[5]

    lastRender = {
        "centerA": centerA,
        "centerB": centerB,
        "radius": radius,
        "quality": quality
    }
    
    // let width   = canvas.canvas.width;
    // let height  = canvas.canvas.height;
    let countWidth  = 0;
    let countHeight = 0;
    let longer = width > height ? width: height;
    let goToHeight = centerB - (radius*height) / longer;
    let goToWidth  = centerA + (radius*width)  / longer;

    console.log("Worker: Starting render with quality of", quality);
    let start = new Date();
    start = start.getTime();
    for (h = centerB + (radius*height)/longer; h >= goToHeight && countHeight < height - 1; h -= (2*radius)/longer){
        for (w = centerA - (radius*width)/longer; w <= goToWidth && countWidth < width - 1; w += (2*radius)/longer) {
            countWidth++;
            this.postMessage([calcPoint(w,h,quality), countWidth, countHeight]);
        }
        countWidth = 0;
        countHeight++;
    }
    let end = new Date();
    console.log("Worker: Done with quality of", quality, "after", (end.getTime()-start)/1000 + " seconds");
}
