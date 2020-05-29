import pickle
import socket 
import struct
    

def send(sock, data):
    data_str = pickle.dumps(data)
    data_len = struct.pack('>Q', len(data_str))
    sock.send(data_len)
    sock.sendall(data_str)
    
def receive(conn):
    buff = conn.recv(8)  # expect an int, IMPORTANT to recv eight
    (length,) = struct.unpack('>Q', buff)  # how many bytes we got
    print(length)
    bytes_ = b''
    while len(bytes_) < length:
        to_read = length - len(bytes_)
        bytes_ += conn.recv(4096 if to_read > 4096 else to_read)
        print(len(bytes_))
    return bytes_


# with open('./client.py', 'r') as fp:
#     data = fp.read()
