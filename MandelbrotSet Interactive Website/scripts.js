let instance;

let canvas = document.getElementById("canvas").getContext("2d");
canvas.canvas.width = window.innerWidth;
canvas.canvas.height = window.innerHeight;

let lastRender;

fetch('./main.wasm').then(response =>
        response.arrayBuffer()
    ).then(bytes => WebAssembly.instantiate(bytes)).then(results => {
        instance = results.instance;
    }).catch(console.error);

function calcPoint(x, y, qual) {
    return instance.exports.calc_mandelbrot(x,y,qual);
}

function buttonPressed() {
    let x = Number(document.getElementById("x coordinate").value);
    let y = Number(document.getElementById("y coordinate").value);
    let radius = Number(document.getElementById("radius").value);
    let iters = Number(document.getElementById("iterations").value);
    render(x,y,radius,iters);
}

function render(centerA, centerB, radius, quality) {

    lastRender = {
        "centerA": centerA,
        "centerB": centerB,
        "radius": radius,
        "quality": quality
    }
    
    let width   = canvas.canvas.width;
    let height  = canvas.canvas.height;
    let countWidth  = 0;
    let countHeight = 0;
    let longer = width > height ? width: height;
    let goToHeight = centerB - (radius*height) / longer;
    let goToWidth  = centerA + (radius*width)  / longer;
    let point;

    let start = new Date();
    start = start.getTime();
    for (h = centerB + (radius*height)/longer; h >= goToHeight && countHeight < height - 1; h -= (2*radius)/longer){
        for (w = centerA - (radius*width)/longer; w <= goToWidth && countWidth < width - 1; w += (2*radius)/longer) {
            countWidth++;
            point = calcPoint(w,h,quality);
            canvas.fillStyle = point===quality ? "#000000" : "rgb("+(point%255%50*50)%255+","+((point%255%50*50)+100)%255 +","+((point%255%50*50)+150)%255 +")";
            canvas.fillRect( countWidth, countHeight, 1, 1 );
        }
        countWidth = 0;
        countHeight++;
    }
    let end = new Date();
    console.log("Done with quality of", quality, "after", (end.getTime()-start)/1000 + " seconds");
}

window.addEventListener('resize', function() {
    canvas.canvas.width = window.innerWidth;
    canvas.canvas.height = window.innerHeight;
    if (lastRender){
        let {centerA, centerB, radius, quality} = lastRender;
        render(centerA, centerB, radius, quality);
    }
}, true);


/**Good renders
 * render(-0.7453,.1107,6.5E-4,1000);
 */
