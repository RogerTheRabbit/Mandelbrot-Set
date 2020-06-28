/**Good renders
 * Similar website: https://guciek.github.io/web_mandelbrot.html#0;0;1;1000
 * Test zooms: http://www.cuug.ab.ca/~dewara/mandelbrot/Mandelbrowser.html
 * Too zoomed in probably???: http://superliminal.com/fractals/mbrot/mbrot.htm
 * render(-0.7453,.1107,6.5E-4,1000);
 */


let canvas = document.getElementById("canvas").getContext("2d");
canvas.canvas.width = window.innerWidth;
canvas.canvas.height = window.innerHeight;

let lastRender;

var worker = new Worker("test.js");

function buttonPressed() {
    let x = Number(document.getElementById("x coordinate").value);
    let y = Number(document.getElementById("y coordinate").value);
    let radius = Number(document.getElementById("radius").value);
    let iters = Number(document.getElementById("iterations").value);
    render(x,y,radius,iters);
}

function render(centerA, centerB, radius, quality) {

    worker.postMessage([centerA, centerB, radius, quality, canvas.canvas.width, canvas.canvas.height]);

    // lastRender = {
    //     "centerA": centerA,
    //     "centerB": centerB,
    //     "radius": radius,
    //     "quality": quality
    // }
    
    // let width   = canvas.canvas.width;
    // let height  = canvas.canvas.height;
    // let countWidth  = 0;
    // let countHeight = 0;
    // let longer = width > height ? width: height;
    // let goToHeight = centerB - (radius*height) / longer;
    // let goToWidth  = centerA + (radius*width)  / longer;
    // let point;

    // console.log("JS: Starting render with quality of", quality);
    // let start = new Date();
    // start = start.getTime();
    // for (h = centerB + (radius*height)/longer; h >= goToHeight && countHeight < height - 1; h -= (2*radius)/longer){
    //     for (w = centerA - (radius*width)/longer; w <= goToWidth && countWidth < width - 1; w += (2*radius)/longer) {
    //         countWidth++;
    //         point = calcPoint(w,h,quality);
    //         canvas.fillStyle = point===quality ? "#000000" : "rgb("+(point%255%50*50)%255+","+((point%255%50*50)+100)%255 +","+((point%255%50*50)+150)%255 +")";
    //         canvas.fillRect( countWidth, countHeight, 1, 1 );
    //     }
    //     countWidth = 0;
    //     countHeight++;
    // }
    // let end = new Date();
    // console.log("JS: Done with quality of", quality, "after", (end.getTime()-start)/1000 + " seconds");
}

worker.onmessage = function (e) {
    if(e.data) {
        let point = e.data[0];
        let countWidth = e.data[1]
        let countHeight = e.data[2]
        canvas.fillStyle = point===100 ? "#000000" : "rgb("+(point%255%50*50)%255+","+((point%255%50*50)+100)%255 +","+((point%255%50*50)+150)%255 +")";
        canvas.fillRect( countWidth, countHeight, 1, 1 );
    } else {
        // Terminate worker here?
    }
}

window.addEventListener('resize', function() {
    canvas.canvas.width = window.innerWidth;
    canvas.canvas.height = window.innerHeight;
    if (lastRender){
        let {centerA, centerB, radius, quality} = lastRender;
        render(centerA, centerB, radius, quality);
    }
}, true);
