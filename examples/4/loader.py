import time

import frida


def my_message_handler(message, payload):
    print message
    print payload
    if message["type"] == "send":
        # print message["payload"]
        data = message["payload"].split(":")[1].strip()
        # print 'message:', message
        data = data.decode("base64")
        user, pw = data.split(":")
        data = ("admin" + ":" + pw).encode("base64")
        # print "encoded data:", data
        script.post({"my_data": data})  # send JSON object
        print "Modified data sent"


device = frida.get_usb_device()
pid = device.spawn(["com.example.a11x256.frida_test"])
device.resume(pid)
time.sleep(1)
session = device.attach(pid)
with open("s4.js") as f:
    script = session.create_script(f.read())
script.on("message", my_message_handler)  # register the message handler
script.load()
raw_input()
