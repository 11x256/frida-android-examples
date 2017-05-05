import frida

device = frida.get_usb_device()
pid = device.spawn(["com.example.a11x256.frida_test"])
process = device.attach(pid)
script = process.create_script(open("s1.js").read())
device.resume(pid)
script.load()

#prevent the python script from terminating
raw_input()
