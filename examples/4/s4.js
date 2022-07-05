console.log("Script loaded successfully ");
Java.perform(function () {
    var tv_class = Java.use("android.widget.TextView");
    tv_class.setText.overload("java.lang.CharSequence").implementation = function (x) {
        var string_to_send = x.toString();
        var string_to_recv;
        send(string_to_send); // send data to python code
        recv(function (received_json_object) {
            var string = Java.use("java.lang.String");
            string_to_recv = string.$new(received_json_object.my_data);
        }).wait(); //block execution till the message is received
        return this.setText(string_to_recv);
    }
});