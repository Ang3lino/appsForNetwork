import sys
from socket import * # portable socket interface plus constants
serverHost = 'localhost' # server name, or: 'starship.python.net'
serverPort = 50007 # non-reserved port used by the server

# default text to send to server
# requires bytes: b'' or str,encode()
message = [b'Hello network world']

if len(sys.argv) > 1:
    serverHost = sys.argv[1] # server from cmd line arg 1
    if len(sys.argv) > 2: # text from cmd line args 2..n
        message = (x.encode() for x in sys.argv[2:])

sockobj = socket(AF_INET, SOCK_STREAM)# make a TCP/IP socket object
sockobj.connect((serverHost, serverPort)) 
# connect to server machine + port
for line in message:
    sockobj.send(line)
    data = sockobj.recv(1024)
    print('Client received:', data) # send line to server over socket
    # receive line from server: up to 1k
    # bytes are quoted, was `x`, repr(x)
sockobj.close() # close socket to send eof to server