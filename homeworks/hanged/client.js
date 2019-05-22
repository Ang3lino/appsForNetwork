const net = require('net');

const HOST = '127.0.0.1';
const PORT = 6969;

const readline = require('readline').createInterface({
    input: process.stdin,
    output: process.stdout
})

const client = new net.Socket();

client.connect(PORT, HOST, function () {
    console.log('CONNECTED TO: ' + HOST + ':' + PORT);

    // Write a message to the socket as soon as the client is connected, the server will receive it as message from the client 
    readline.question(`Choose a difficulty (0, 1, 2): `, input => {
        client.write(input);
        readline.close();
    })
});

client.on('data', function (data) { // response obtained from server, type object
    play(data.toString());
    client.destroy();
});

// Add a 'close' event handler for the client socket
client.on('close', function () {
    console.log('Connection closed');
});

zip = rows => rows[0].map((_, c) => rows.map(row => row[c]))

String.prototype.replaceAt = function (index, replacement) {
    return this.substr(0, index) + replacement + this.substr(index + replacement.length);
}

function play(word) {
    let lives = 3, indexes = initialHelp(word), question = '_'.repeat(word.length)
    for (let i = 0; i < lives; ++i) {
        indexes.forEach(j => question = question.replaceAt(j, word.charAt(j)))
        readline.question(`Type: ${question}`, oinput => {
            input = oinput.slice(0, word.length)
            zip(input, word).filter(t => t[0] === t[1]).forEach(t => indexes.add(t[0]))
        })
        if (indexes.length === word.length) return true
    }
    return false
}

// inclusive range
function randomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

function initialHelp(word) {
    let indexes = new Set()
    const helps = Math.floor(word.length / 3)
    const b = word.length - 1
    while (indexes.size < helps) {
        indexes.add(randomInt(0, b))
    }
    console.log(indexes)
    return indexes
}