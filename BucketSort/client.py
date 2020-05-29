
import socket
import pickle  # object serialization
import threading
import random
import functools
import sys
import reliable
import time 


def server(HOST, PORT):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # create a TCP Server
    s.bind((HOST, PORT))
    s.listen(1)  # accept n per time
    print('esperando accept')
    conn, addr = s.accept()
    s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)  # reuse socket (for dev)
    print('esperando recv')
    object_ = pickle.loads(reliable.receive(conn))
    object_.sort()
    # print(object_)
    reliable.send(conn, object_)
    conn.close()

def argmin(values):
    return min(range(len(values)), key=values.__getitem__)

def merge(src):
    '''the argument must be a list of list, where each list is sorted, it returns a list from all this lists sorted.'''
    result = []
    while src:
        first = list(map(lambda arr: arr[0], src))  # get the first element from each list
        i_min = argmin(first)
        arr = src[i_min]  # get the reference of the list
        result.append(arr[0])  # append the min element
        arr.pop(0)  # remove the element i
        if not arr:  # if arr is empty
            src.pop(i_min)  # remove it from the list of lists
    return result

def chunks(lst, n):
    """Yield successive n-sized chunks from lst."""
    for i in range(0, len(lst), n):
        yield lst[i:i + n]

def client(HOST, PORT, arr, result, i):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # create TCP Socket
    s.connect((HOST, PORT))
    reliable.send(s, arr)
    res = pickle.loads(reliable.receive(s))
    # print(res)
    result[i] = res  # store the result in the mutable array
    s.close()


n_threads = 4
result_arr = [None] * n_threads  # create a list of n elements of None
rand_arr = [random.randint(0, 70_000) for _ in range(80_000)]
assert len(rand_arr) % n_threads == 0, 'the amount of threads is not appropiate.'
partition = list(chunks(rand_arr, int(len(rand_arr) / n_threads)))  
HOST = 'localhost'
PORT = 50007

# start the servers for listening the requests
servers = [threading.Thread(target=server, args=(HOST, PORT + i)) for i in range(n_threads)]
for t in servers: t.start()

clients = [threading.Thread(target=client, args=(HOST, PORT + i, partition[i], result_arr, i)) 
        for i in range(n_threads)]
for t in clients: t.start()
for t in clients: t.join()
sorted_arr = merge(result_arr)
print(sorted_arr)

for t in servers: t.join()

# client(HOST, PORT, rand_arr)