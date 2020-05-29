
import socket
import pickle  # object serialization
import threading
import random
import functools
import sys
import reliable
import time 



def server(HOST, PORT):
    s_req = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # create a TCP Server
    s_req.bind((HOST, PORT))
    s_req.listen(1)
    conn, addr = s_req.accept()
    s_req.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    print(f'Server {HOST}:{PORT} waiting for a client')
    object_ = pickle.loads(reliable.receive(conn))
    print(f'Server {HOST}:{PORT} got a client ({addr})')
    object_.sort()
    print(object_)

    reliable.send(conn, object_)
    conn.close()

def argmin(values):
    return min(range(len(values)), key=values.__getitem__)

def merge(src):
    result = []
    while src:
        first = list(map(lambda arr: arr[0], src))  # get the first element from each list
        i_min = argmin(first)
        arr = src[i_min]  # get the reference of the list
        result.append(arr[0])
        arr.pop(0)
        if not arr:
            src.pop(i_min)
    return result

def chunks(lst, n):
    """Yield successive n-sized chunks from lst."""
    for i in range(0, len(lst), n):
        yield lst[i:i + n]

def client(HOST, PORT, arr):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # create TCP Socket
    s.connect((HOST, PORT))
    reliable.send(s, arr)
    res = pickle.loads(reliable.receive(s))
    print(res)
    # result[i] = res
    s.close()


n_threads = 1
# result_arr = [None] * n_threads
rand_arr = [random.randint(0, 5000) for _ in range(100000)]
# partition = list(chunks(rand_arr, int(len(rand_arr) / n_threads)))  # !!
HOST = 'localhost'
PORT = 50007

# servers = [threading.Thread(target=server, args=(HOST, PORT + i)) for i in range(n_threads)]
# for t in servers:
#     t.start()

# clients = [threading.Thread(target=client, args=(HOST, PORT + i, partition[i], result_arr, i)) 
#         for i in range(n_threads)]
# for t in clients:
#     t.start()
# for t in clients:
#     t.join()
# print(partition)
client(HOST, PORT, rand_arr)
# print(result_arr)
# print(merge(result_arr))

