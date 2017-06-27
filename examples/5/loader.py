import time
import frida
import json
enc_cipher_hashcodes = [] #cipher objects with Cipher.ENCRYPT_MODE will be stored here
dec_cipher_hashcodes = [] #cipher objects with Cipher.ENCRYPT_MODE will be stored here


def my_message_handler(message, payload):
    #mainly printing the data sent from the js code, and managing the cipher objects according to their operation mode  
    if message["type"] == "send":
        # print message["payload"]
        my_json = json.loads(message["payload"])
        if my_json["my_type"] == "KEY":
            print "Key sent to SecretKeySpec()", payload.encode("hex")
        elif my_json["my_type"] == "IV":
            print "Iv sent to IvParameterSpec()", payload.encode("hex")
        elif my_json["my_type"] == "hashcode_enc":
            enc_cipher_hashcodes.append(my_json["hashcode"])
        elif my_json["my_type"] == "hashcode_dec":
            dec_cipher_hashcodes.append(my_json["hashcode"])
        elif my_json["my_type"] == "Key from call to cipher init":
            print "Key sent to cipher init()", payload.encode("hex")
        elif my_json["my_type"] == "IV from call to cipher init":
            print "Iv sent to cipher init()", payload.encode("hex")
        elif my_json["my_type"] == "before_doFinal" and my_json["hashcode"] in enc_cipher_hashcodes:
            #if the cipher object has Cipher.MODE_ENCRYPT as the operation mode, the data before doFinal will be printed
            #and the data returned (ciphertext) will be ignored
            print "Data to be encrypted :", payload
        elif my_json["my_type"] == "after_doFinal" and my_json["hashcode"] in dec_cipher_hashcodes:
            print "Decrypted data :", payload
    else:
        print message
        print '*' * 16
        print payload


device = frida.get_usb_device()
pid = device.spawn(["com.example.a11x256.frida_test"])
device.resume(pid)
time.sleep(1)  # Without it Java.perform silently fails
session = device.attach(pid)

with open("s5.js") as f:
    script = session.create_script(f.read())
script.on("message", my_message_handler)  # register the message handler
script.load()

raw_input()
