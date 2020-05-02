// (async () => {
//     const promise = fetch("./main.wasm");
//     const module = await WebAssembly.instantiateStreaming(promise);

//     console.log(module);
// })();

let instance;

fetch('./main.wasm').then(response =>
        response.arrayBuffer()
    ).then(bytes => WebAssembly.instantiate(bytes)).then(results => {
        instance = results.instance;
        document.getElementById("container").textContent = instance.exports.calc_mandelbrot(0,0,500);
    }).catch(console.error);

function calcPoint(x, y, qual) {
    return instance.exports.calc_mandelbrot(x,y,qual);
}

const STEP = 720;

function genImage() {
    const out = [];
    for(x = -1; x < 1; x+=2/STEP) {
        const row = []
        for (let y = -1; y < 1; y+=2/STEP) {
            row.push(calcPoint(x,y,500));
        }
        out.push(row);
    }
    return out;
}

// function displayImage() {
//     const container = document.getElementsByClassName("grid-container")[0];
//     let point;
//     let div;
//     for(x = -1; x < 1; x+=2/STEP) {
//         for (let y = -1; y < 1; y+=2/STEP) {
//             point = calcPoint(x,y,500);
//             div = document.createElement("div")
//             div.className = "grid-item";
//             div.innerText = point;
//             div.style.backgroundColor = point;
//             container.appendChild(div);
//         }
//     }
//     console.log("done");
// }

function displayImage(qual) {
    const table = document.getElementById("table");
    let point;
    let item;
    let row;
    let start = new Date();
    start = start.getTime();
    for(x = -2; x < 2; x+=4/STEP) {
        row = document.createElement("tr");
        for (let y = -2; y < 2; y+=4/STEP) {
            point = calcPoint(y,x,qual);
            item = document.createElement("td");
            // item.innerText = point;
            item.style.backgroundColor = point===qual ? "#000000" : "rgb("+(point%255%50*50)%255+","+((point%255%50*50)+100)%255 +","+((point%255%50*50)+150)%255 +")";
            row.appendChild(item);
        }
        table.appendChild(row);
    }
    let end = new Date();
    console.log((end.getTime()-start)/1000 + " seconds");
}
