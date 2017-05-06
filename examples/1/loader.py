import time

import frida

device = frida.get_usb_device()
pid = device.spawn(["com.example.a11x256.frida_test"])
device.resume(pid)
time.sleep(1)  # Without it Java.perform silently fails
session = device.attach(pid)
with open("s1.js") as f:
	script = session.create_script(f.read())
script.load()

#prevent the python script from terminating
raw_input()
