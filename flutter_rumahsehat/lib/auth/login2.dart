import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_rumahsehat/main.dart';
// import 'package:jwt_token_flutter_app/main.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;

void main() {
  runApp(LoginPage());
}
class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {

  bool _isLoading = false;

  final TextEditingController usernameController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

  @override
  Widget build(BuildContext context) {

    SystemChrome.setSystemUIOverlayStyle(SystemUiOverlayStyle.light.copyWith(statusBarColor: Colors.transparent));

    return Scaffold(
      body: Container(
        decoration: BoxDecoration(
          gradient: LinearGradient(
            colors: [Colors.blue, Colors.teal],
            begin: Alignment.topCenter,
            end: Alignment.bottomCenter,
          ),
        ),
        child: _isLoading ? Center(child: CircularProgressIndicator(),) : ListView(
          children: <Widget>[
            heardSection(),
            textSection(),
            buttonSection(),
          ],
        ),
      ),
    );
  }

  signIn(String username, String password) async {
    SharedPreferences sharedPreferences = await SharedPreferences.getInstance();
    Map data = {
      'email': username,
      'password': password
    };

    print(data);
    var jsonResponse = null;
    Map<String, String> headers = {"Content-Type":"application/json"};

    final msg = jsonEncode({"username":username,"password":password});

    var response = await http.post(
        Uri.parse("http://localhost:8080/api/authenticate"),
        body: msg,
        headers: headers
    );
    jsonResponse = json.decode(response.body);

    print('Response Status: ${response.statusCode}');
    print('Response Body: ${response.body}');

    if(jsonResponse != null){
      setState(() {
        _isLoading = false;
      });
      sharedPreferences.setString("token", jsonResponse['token']);
      Navigator.of(context).pushAndRemoveUntil(
          MaterialPageRoute(builder: (BuildContext context) => MyApp()),
              (Route<dynamic> route) => false
      );
    } else {
      setState(() {
        _isLoading = false;
      });
      print(response.body);
    }
  }

  Container buttonSection(){
    return Container(
      width: MediaQuery.of(context).size.width,
      height: 40.0,
      padding: EdgeInsets.symmetric(horizontal: 15.0),
      child: ElevatedButton(
        onPressed: (){
          setState(() {
            _isLoading = true;
          });
          signIn(usernameController.text,passwordController.text);
        },
        // elevation: 0.0,
        // color: Colors.purple,
        child: Text('SignIn', style: TextStyle(color: Colors.white70),),
        // shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(5.0)),
      ),
    );
  }

  Container textSection(){
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 15.0,vertical: 20.0),
      child: Column(
        children: <Widget>[
          TextFormField(
            controller: usernameController,
            cursorColor: Colors.white,
            style: TextStyle(color: Colors.white70),
            decoration: InputDecoration(
                icon: Icon(Icons.email,color: Colors.white70,),
                hintText: "Username",
                border: UnderlineInputBorder(borderSide: BorderSide(color: Colors.white70)),
                hintStyle: TextStyle(color: Colors.white)
            ),
          ),
          SizedBox(height: 30.0,),
          TextFormField(
            obscureText: true,
            controller: passwordController,
            cursorColor: Colors.white,
            style: TextStyle(color: Colors.white70),
            decoration: InputDecoration(
                icon: Icon(Icons.lock,color: Colors.white70,),
                hintText: "Password",
                border: UnderlineInputBorder(borderSide: BorderSide(color: Colors.white70)),
                hintStyle: TextStyle(color: Colors.white)
            ),
          ),
        ],
      ),
    );
  }

  Container heardSection(){
    return Container(
      margin: EdgeInsets.only(top: 50.0,),
      padding: EdgeInsets.symmetric(horizontal: 20.0,vertical: 30.0),
      child: Text("app JWT",style: TextStyle(color: Colors.white70,fontSize: 40.0,fontWeight: FontWeight.bold),),
    );
  }

}