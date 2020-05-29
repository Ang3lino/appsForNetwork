

import socket
import pickle
import threading
import random
import functools
import sys
import reliable


def receive_reliable(conn):
    buff = conn.recv(8)  # expect an int, IMPORTANT to recv eight
    length = pickle.loads(buff)  # how many bytes we got
    bytes_ = b''
    while len(bytes_) < length:
        to_read = length - len(bytes_)
        bytes_ += conn.recv(4096 if to_read > 4096 else to_read)
    return bytes_

def server(HOST, PORT):
    s_req = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # create a TCP Server
    s_req.bind((HOST, PORT))
    s_req.listen(1)
    conn, addr = s_req.accept()
    s_req.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    object_ = pickle.loads(reliable.receive(conn))
    object_.sort()
    print(object_)

    reliable.send(conn, object_)
    conn.close()

server('localhost', 50007)