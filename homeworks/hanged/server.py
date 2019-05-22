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
    difficulty = int.from_bytes(data, byteorder='big') % 3 # guaranteed i in [0,1,2]
    print('difficulty', difficulty)
    list_words = words_dict[difficulty]
    rand_index = randint(0, len(list_words))
    print(rand_index)
    res = list_words[rand_index]
    print('Word sent =>', res)
    connection.send(str.encode(res))
    connection.close()
