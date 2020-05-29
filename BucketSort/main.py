
import socket
import pickle
import threading
import random
import functools
import sys

from struct import pack, unpack


msg_len = 4096


def chunks(lst, n):
    """Yield successive n-sized chunks from lst."""
    for i in range(0, len(lst), n):
        yield lst[i:i + n]


HOST = 'localhost'
PORT = 50007
# Create a socket connection.
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((HOST, PORT))

data = [random.randint(0, 10_000) for _ in range(5000)]

data_str = pickle.dumps(data)
data_len = pickle.dumps(len(data_str))

s.send(data_len)
s.sendall(data_str)

print(data_str)
s.close()

print ('Data Sent to Server')





