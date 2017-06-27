console.log("Script loaded successfully 55");


Java.perform(function x(){
    var secret_key_spec = Java.use("javax.crypto.spec.SecretKeySpec");
    secret_key_spec.$init.overload("[B", "java.lang.String").implementation  = function(x , y){
        send('{"my_type" : "KEY"}' ,new Uint8Array(x));
       //console.log(xx.join(" "))
        return this.$init(x , y);
    }
    var iv_parameter_spec = Java.use("javax.crypto.spec.IvParameterSpec");
    iv_parameter_spec.$init.overload("[B").implementation = function(x){
        send('{"my_type" : "IV"}' , new Uint8Array(x));
        return this.$init(x);
    }
    var cipher = Java.use("javax.crypto.Cipher");
    cipher.init.overload("int" , "java.security.Key" , "java.security.spec.AlgorithmParameterSpec").implementation = function(x,y,z){
        //console.log(z.getClass());
        if (x == 1)
            send('{"my_type" : "hashcode_enc", "hashcode" :"' +this.hashCode().toString() +'" }');
        else
            send('{"my_type" : "hashcode_dec", "hashcode" :"' +this.hashCode().toString() +'" }');

        send('{"my_type" : "Key from call to cipher init"}', new Uint8Array(y.getEncoded()) );
        send('{"my_type" : "IV from call to cipher init"}' , new Uint8Array(Java.cast(z , iv_parameter_spec).getIV() ));
        return cipher.init.overload("int" , "java.security.Key" , "java.security.spec.AlgorithmParameterSpec").call(this,x,y,z);

    }

    cipher.doFinal.overload("[B").implementation = function(x){
        send('{"my_type" : "before_doFinal" , "hashcode" :"' +this.hashCode().toString() +'" }' , new Uint8Array(x));
        var ret = cipher.doFinal.overload("[B").call(this,x);
        send('{"my_type" : "after_doFinal" , "hashcode" :"' +this.hashCode().toString() +'" }' , new Uint8Array(ret));

        return ret;
    }
});
