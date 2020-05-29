import pickle
import socket 

    
def send(sock, data):
    data_str = pickle.dumps(data)
    data_len = pickle.dumps(len(data_str))
    sock.send(data_len)
    sock.sendall(data_str)

def receive(conn):
    buff = conn.recv(8)  # expect an int, IMPORTANT to recv eight
    length = pickle.loads(buff)  # how many bytes we got
    bytes_ = b''
    while len(bytes_) < length:
        to_read = length - len(bytes_)
        bytes_ += conn.recv(4096 if to_read > 4096 else to_read)
    return bytes_