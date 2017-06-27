var http = require("http");
var crypto = require('crypto');
var qs = require('querystring');

var key= 'aaaaaaaaaaaaaaaa';
var iv = 'bbbbbbbbbbbbbbbb';
http.createServer(function(request , response)
		{
			var body = '';
			request.on('data' , function(data){

				body+=data;
			});

			console.log(body)
				response.writeHead(200,"");
			request.on('end', function(){
				console.log(body);
				var enc = '' ;
				var cipher = crypto.createCipheriv('aes-128-cbc' , key , iv);
				enc +=cipher.update("Can you see this secret message too !!!","utf8" , "binary");
				enc += cipher.final("binary");
				console.log(enc);
				response.end(new Buffer(enc, 'binary').toString('base64'));
			});



		}


		).listen(80, "0.0.0.0");
