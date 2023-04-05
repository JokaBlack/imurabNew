let ctx1 = document.getElementById("canv").getContext("2d");

const results = [
    {mood: "Angry", total: 800, shade: "#5bbc2e"},
    {mood: "Angry", total: 700, shade: "#2e60bc"},
    {mood: "Angry", total: 600, shade: "#bc2e52"},
    {mood: "Angry", total: 500, shade: "#cdeb4b"},
    {mood: "Angry", total: 400, shade: "#f11c92"},
    {mood: "Angry", total: 300, shade: "#2acde2"},
    {mood: "Angry", total: 200, shade: "#fc580d"},
    {mood: "Angry", total: 100, shade: "#80b6b9"},
    {mood: "Angry", total: 50, shade: "#b3560b"} 
];

let sum = 0;
let totalNumberOfPeople = results.reduce((sum, {total}) => sum + total, 0);
let currentAngle = 0;

for (let moodValue of results) {
    //calculating the angle the slice (portion) will take in the chart
    let portionAngle = (moodValue.total / totalNumberOfPeople) * 2 * Math.PI;
    //drawing an arc and a line to the center to differentiate the slice from the rest
    ctx1.beginPath();
    ctx1.arc(100, 100, 100, currentAngle, currentAngle + portionAngle);
    currentAngle += portionAngle;
    ctx1.lineTo(100, 100);
    //filling the slices with the corresponding mood's color
    ctx1.fillStyle = moodValue.shade;
    ctx1.fill();
}

let canvas = [];
let values = [];
canvas = document.getElementsByClassName("canvas");

for(let i=0; i<canvas.length; i++){
    let ctx = canvas[i].getContext("2d");
    ctx.font = "60pt Arial";
    ctx.fillStyle = "#4b4b4b";
    ctx.fillText(canvas[i].textContent, 40, 95);
}