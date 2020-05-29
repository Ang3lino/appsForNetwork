

import socket
import pickle
import threading
import random
import functools
import sys


def server(HOST, PORT):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # create a TCP Server
    s.bind((HOST, PORT))
    s.listen(1)
    conn, addr = s.accept()
    s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    print('Connected by', addr)

    buff = conn.recv(8)  # expect an int, IMPORTANT to recv eight
    length = pickle.loads(buff)

    bytes_ = b''
    while len(bytes_) < length:
        to_read = length - len(bytes_)
        bytes_ += conn.recv(4096 if to_read > 4096 else to_read)
    print(pickle.loads(bytes_)) 

    conn.close()

server('localhost', 50007)