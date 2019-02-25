
const socket = io.connect('localhost:4000');

let treeDir = null;
socket.on('initTree', (data) => {
    treeDir = data; 
});

export default treeDir;