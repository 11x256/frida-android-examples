import time

import frida


def my_message_handler(message, payload):
	print message
	print payload


device = frida.get_usb_device()
pid = device.spawn(["com.example.a11x256.frida_test"])
device.resume(pid)
time.sleep(1)  # Without it Java.perform silently fails
session = device.attach(pid)
with open("s2.js") as f:
	script = session.create_script(f.read())
script.on("message", my_message_handler)
script.load()

# prevent the python script from terminating
raw_input()
