console.log("Script loaded successfully ");
var instances_array = [];
function callSecretFun() {
    Java.perform(function () {
        if (instances_array.length == 0) { // if array is empty
            Java.choose("com.example.a11x256.frida_test.my_activity", {
                onMatch: function (instance) {
                    console.log("Found instance: " + instance);
                    instances_array.push(instance)
                    console.log("Result of secret func: " + instance.secret());
                },
                onComplete: function () { }

            });
        }
        else {//else if the array has some values
            for (i = 0; i < instances_array.length; i++) {
                console.log("Result of secret func: " + instances_array[i].secret());
            }
        }

    });


}
rpc.exports = {
    callsecretfunction: callSecretFun


};