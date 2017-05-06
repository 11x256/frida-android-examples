console.log("Script loaded successfully ");
Java.perform(function x(){ //Silently fails without the sleep from the python code
    console.log("Inside java perform function");
    //get a wrapper for our class
    var my_class = Java.use("com.example.a11x256.frida_test.my_activity");
    //replace the original implmenetation of the function `fun` with our custom function
    my_class.fun.implementation = function(x,y){
    //print the original arguments
    console.log( "original call: fun("+ x + ", " + y + ")");
    //call the original implementation of `fun` with args (2,5)
    var ret_value = this.fun(2,5);
    return ret_value;
    }});