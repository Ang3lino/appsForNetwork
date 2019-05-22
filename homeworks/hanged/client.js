const net = require('net');

const HOST = '127.0.0.1';
const PORT = 6969;

const readline = require('readline').createInterface({
    input: process.stdin,
    output: process.stdout
})

const client = new net.Socket();

client.connect(PORT, HOST, function() {
    console.log('CONNECTED TO: ' + HOST + ':' + PORT);

    // Write a message to the socket as soon as the client is connected, the server will receive it as message from the client 
    readline.question(`Choose a difficulty (0, 1, 2): `, input => {
        client.write(input);
        readline.close();
    })
});

client.on('data', function(data) { // response obtained from server
    play(data);
    client.destroy();
});

// Add a 'close' event handler for the client socket
client.on('close', function() {
    console.log('Connection closed');
});

function play(word) {
    let lives = 3, indexes = initialHelp(word), question = '_'.repeat(word.length)
    for (let i = 0; i < lives; ++i) {
        indexes.forEach(j => question.charAt(j) = word.charAt(j)) 
        readline.question(`${question}`, input => { 
            for (let j = 0; j < input.length; ++j) if (input.charAt(j) === word.charAt(j))
            [...input].entries.filter(t => t[0] === )
        })
    }
}

// inclusive range
function randomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1) ) + min;
}

function initialHelp(word) {
    let indexes = new Set()
    const helps = Math.floor(word.length / 3)
    const b = word.length - 1
    while (indexes.size < helps) {
        indexes.add(randomInt(0, b))
    }
    return indexes
}