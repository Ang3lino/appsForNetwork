from socket import *
from random import randint # inclusive range

import json
import dictionary # dictionary of words loaded from a local file

myHost = '' # '' means all available interfaces on host
myPort = 6969 

# '' = all available interfaces on host
sockobj = socket(AF_INET, SOCK_STREAM) # make a TCP socket object
sockobj.bind((myHost, myPort)) 
sockobj.listen(5) # listen, allow 5 pending connections

words_dict = dictionary.load()
print('server ON, waiting for a connection')
while True: # listen until process killed
    connection, address = sockobj.accept() # wait for the next client connection
    print('Connection from', address) 
    data = connection.recv(1024) # read next line on client socket
    difficulty = int.from_bytes(data) % 3 # guaranteed i in [0,1,2]
    res = words_dict[difficulty][randint(0, len(words_dict[difficulty]))]
    print('Word sent =>', res)
    connection.send(bytes(res))
    connection.close()
