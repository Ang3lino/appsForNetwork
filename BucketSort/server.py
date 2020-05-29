import socket
import pickle
import threading
import random
import functools
import sys
import reliable


def chunks(lst, n):
    """Yield successive n-sized chunks from lst."""
    for i in range(0, len(lst), n):
        yield lst[i:i + n]

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

def server(HOST, PORT):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # create a TCP Server
    s.bind((HOST, PORT))
    s.listen(1)
    print('esperando accept')
    conn, addr = s.accept()
    s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    print('esperando recv')
    object_ = pickle.loads(reliable.receive(conn))
    object_.sort()
    print(object_)
    reliable.send(conn, object_)
    conn.close()


server('localhost', 50007)
# arr = [random.randint(0, 10_000) for _ in range(100)]
# arr = list(chunks(arr, 10))
# for a in arr:
#     a.sort()
# print(merge(arr))