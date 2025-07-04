const apiBaseUrl = "http://localhost:8080";
const wsUrl = "ws://localhost:8080/ws/sorter";
let socket;

let numLines;
let numWidgets;

function openWebSocket() {
    socket = new WebSocket(wsUrl);

    socket.onopen = function(event) {
        console.log("WebSocket is connected turtle turtle");
    };

    socket.onmessage = processSocketMsg;

    socket.onerror = function(error) {
        console.log('WebSocket Error:', error);
    };

    socket.onclose = function(event) {
        console.log('WebSocket connection closed');
    };
}

function processSocketMsg(event){
    let str = event.data;
    let twoPart = str.split("::");

    let widgetNum = parseInt(twoPart[0]);
    let vals = getValsFromString(twoPart[1]);

    

    drawLinesInWidget(numLines, vals, widgetNum);
 
}

function getValsFromString(valsString){
    let valsStringArray = valsString.split(" ");
    let vals = [];
    for(let valString of valsStringArray){
        vals.push(parseInt(valString));
    }
    return vals;
}

function submitNewParameters(){
    let newNumWidgets = parseInt(document.getElementById('widgetCount').value);

    if(numWidgets && numWidgets === newNumWidgets){
        regenerateSameWidgetsWithNewLines();
    }
    else{
        generateWidgets();
    }
}

function regenerateSameWidgetsWithNewLines(){
    numLines = parseInt(document.getElementById('lineCount').value);
    
    let widgetHeight = document.getElementById(`widget-${0}`).clientHeight;
    let vals = generateVals(widgetHeight, numLines);

    for(let i = 0; i < numWidgets; i++){
        drawLinesInWidget(numLines, vals, i);
    }
}

function generateWidgets() {
    numWidgets = parseInt(document.getElementById('widgetCount').value);
    numLines = parseInt(document.getElementById('lineCount').value);

    const container = document.getElementById('widgetContainer');
    container.innerHTML = '';

    if (isNaN(numWidgets) || numWidgets < 1 || numWidgets > 6) return;

    const screenWidth = window.innerWidth * 0.9;
    const screenHeight = (window.innerHeight - 150) * 0.9;

    let columns = numWidgets <= 2 ? numWidgets : Math.ceil(numWidgets / 2);
    let rows = numWidgets > 2 ? 2 : 1;

    const gap = 20;
    const totalGapWidth = gap * (columns + 1);
    const totalGapHeight = gap * (rows + 1);

    const widgetWrapperWidth = Math.floor((screenWidth - totalGapWidth) / columns);
    const widgetWrapperHeight = Math.floor((screenHeight - totalGapHeight) / rows);

    let minSidebarWidth = 80;
    const sidebarWidth = Math.max(minSidebarWidth, Math.floor(widgetWrapperWidth * 0.15));
    const widgetWidth = widgetWrapperWidth - sidebarWidth;

    let vals = generateVals(widgetWrapperHeight, numLines);

    for (let i = 0; i < numWidgets; i++) {
        const wrapper = document.createElement('div');
        wrapper.className = 'widget-wrapper';
        wrapper.id = `wrapper-${i}`;
        wrapper.style.width = `${widgetWrapperWidth}px`;
        wrapper.style.height = `${widgetWrapperHeight}px`;
        

        const widget = document.createElement('div');
        widget.className = 'widget';
        widget.id = `widget-${i}`;
        widget.style.width = `${widgetWidth}px`;
        widget.style.height = `${widgetWrapperHeight}px`;

        const sidebar = document.createElement('div');
        sidebar.className = 'sidebar';
        sidebar.id = `sidebar-${i}`;
        sidebar.style.width = `${sidebarWidth}px`;

        const label = document.createElement('div');
        label.id = `label-${i}`;
        label.textContent = `Widget ${i + 1}`;

        const select = document.createElement('select');
        select.title = 'Options';
        select.id = `select-${i}`;
        select.innerHTML = `
          <option value="bubble">Bubble Sort</option>
          <option value="selection">Selection Sort</option>
          <option value="insertion">Insertion Sort</option>
          <option value="merge">Merge Sort</option>
          <option value="quick">Quick Sort</option>
          <option value="double-pivot-quick">Double Pivot Quick Sort</option>
          <option value="heap">Heap Sort</option>
          <option value="tim">Tim Sort</option>
          <option value="counting">Counting Sort</option>
          <option value="bogo">Bogo Sort</option>
          <option value="bozo">Bozo Sort</option>
        `;

        //maybe don't do counting sort lol, we see, it will just look like a write over, maybe we do tho, we see

        const currentSelection = document.createElement('div');
        currentSelection.className = 'current-selection';
        currentSelection.textContent = 'Selected: Bubble Sort';
        currentSelection.id = `current-select-${i}`;

        select.onchange = (e) => {
            const selectedText = e.target.options[e.target.selectedIndex].text;
            currentSelection.textContent = 'Selected: ' + selectedText;
        };

        sidebar.appendChild(label);
        sidebar.appendChild(select);
        sidebar.appendChild(currentSelection);

        wrapper.appendChild(widget);
        wrapper.appendChild(sidebar);
        container.appendChild(wrapper);



        drawLinesInWidget(numLines, vals, i);
    }
}


function generateVals(widgetHeight, numLines){
    let vals = [];

    for(let i = 0; i < numLines; i++){
        vals.push(Math.floor(Math.random() * widgetHeight * 0.9) + 10);
    }

    return vals;
}

function drawLinesInWidget(count, vals, widgetNum) {
    let widget = document.getElementById(`widget-${widgetNum}`);
    const widgetWidth = widget.clientWidth;
    const widgetHeight = widget.clientHeight;

    widget.innerHTML = "";

    const minVal = Math.min(...vals);
    const maxVal = Math.max(...vals);

    const gap = 0; //pixels between lines
    const totalGap = gap * (count - 1);
    const lineWidth = (widgetWidth - totalGap) / count;

    let left = 0;
    for (let i = 0; i < count; i++) {
        const line = document.createElement('div');
        line.className = 'vertical-line';
        line.id = `${widgetNum}-line-${i}`;

        const height = vals[i];
        line.style.position = 'absolute';
        line.style.bottom = '0';
        line.style.height = `${height}px`;
        line.style.width = `${lineWidth}px`;
        line.style.left = `${left}px`;

        const hue = 360 * (height - minVal) / (maxVal - minVal || 1);
        line.style.backgroundColor = `hsl(${hue}, 70%, 40%)`;

        widget.appendChild(line);
        left += lineWidth + gap;
    }
}



function limitInputs() {
    const widgetInput = document.getElementById('widgetCount');
    const lineInput = document.getElementById('lineCount');

    let widgetVal = parseInt(widgetInput.value);
    let lineVal = parseInt(lineInput.value);

    if (widgetVal < 1) widgetVal = 1;
    if (widgetVal > 6) widgetVal = 6;
    if (lineVal < 0) lineVal = 0;
    if (lineVal > 500) lineVal = 500;

    widgetInput.value = widgetVal;
    lineInput.value = lineVal;
}

async function sendGraphDataToBackend(selectedOption, dataAsString) {
    const data = { input: dataAsString };

    console.log(dataAsString);

    try {
        const response = await fetch(`${apiBaseUrl}/solve-${selectedOption}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data), //sendinginfo
        });

        const result = await response.text(); // Extract result
        return result; 

    } catch (error) {
        console.error("Error:", error);
        throw error; //throw error if needed
    }
}

function startSolvingAll(){
    for(let i = 0; i < numWidgets; i++){
        let vals = getValsFromGraph(i);
        let dataString = getDataString(i, vals);
        
        let select = document.getElementById(`select-${i}`);
        let selectedOption = select.value;

        sendGraphDataToBackend(selectedOption, dataString);
    
    }
}

function getDataString(widgetNum, vals) {
    let dataString = widgetNum + "::" + vals.join(" ");
    return dataString;
}

function getValsFromGraph(widgetNum){
    let vals = [];
    let widget = document.getElementById(`widget-${widgetNum}`);

    for(let i = 0; i < numLines; i++){
        let line = document.getElementById(`${widgetNum}-line-${i}`);
        vals.push(parseInt(line.style.height));
    }

    return vals;

}

function initialize(){
    let submitButton = document.getElementById("submit-Btn");
    let sortButton = document.getElementById("sort-Btn");
    let widgetInput = document.getElementById('widgetCount');
    let lineInput = document.getElementById('lineCount');

    submitButton.addEventListener("click", submitNewParameters);

    sortButton.addEventListener("click", startSolvingAll);

    widgetInput.addEventListener('input', limitInputs);
    lineInput.addEventListener('input', limitInputs);

    document.addEventListener('keydown', (event) => {
        if (event.key == "Enter") {
            generateWidgets();
        }
    });

    generateWidgets();

    openWebSocket();
}



window.onload = initialize;
window.onresize = generateWidgets;