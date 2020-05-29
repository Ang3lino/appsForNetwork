
import socket
import pickle
import threading


class Angel:
    def __init__(self):
        pass

    def __str__(self):
        return 'Wusup Im Angel'


def server(HOST, PORT):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((HOST, PORT))
    s.listen(1)
    conn, addr = s.accept()
    print('Connected by', addr)

    data = conn.recv(4096)
    data_variable = pickle.loads(data)
    print(data)
    print(data_variable)
    conn.close()


thread = threading.Thread(target=server, args=('localhost', 50007))
thread.start()

HOST = 'localhost'
PORT = 50007
# Create a socket connection.
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((HOST, PORT))

# Create an instance of ProcessData() to send to server.
variable = Angel()
# Pickle the object and send it to the server
data_string = pickle.dumps(variable)
s.send(data_string)

s.close()
print ('Data Sent to Server')