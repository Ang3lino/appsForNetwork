const net = require('net');

const HOST = '127.0.0.1';
const PORT = 6969;

const client = new net.Socket();
client.connect(PORT, HOST, function() {

    console.log('CONNECTED TO: ' + HOST + ':' + PORT);
    // Write a message to the socket as soon as the client is connected, the server will receive it as message from the client 
    client.write(1);
});

client.on('data', function(data) { // response obtained from server
    console.log('DATA: ' + data);
    client.destroy();
});

// Add a 'close' event handler for the client socket
client.on('close', function() {
    console.log('Connection closed');
});