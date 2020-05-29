
import socket
import pickle  # object serialization
import threading
import random
import functools
import sys
import reliable

from struct import pack, unpack   


msg_len = 4096


def chunks(lst, n):
    """Yield successive n-sized chunks from lst."""
    for i in range(0, len(lst), n):
        yield lst[i:i + n]
    
def send_reliable(sock, data):
    data_str = pickle.dumps(data)
    data_len = pickle.dumps(len(data_str))
    sock.send(data_len)
    sock.sendall(data_str)

def client(HOST, PORT, data):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # create TCP Socket
    s.connect((HOST, PORT))
    reliable.send(s, data)
    result = pickle.loads(reliable.receive(s))
    print(result)
    s.close()



HOST = 'localhost'
PORT = 50007
data = [random.randint(0, 10_000) for _ in range(5_000)]
client(HOST, PORT, data)


