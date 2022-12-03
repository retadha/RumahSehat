import 'package:flutter/material.dart';
import 'package:flutter_rumahsehat/main.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';

void main() {
  runApp(LoginForm());
}
class LoginForm extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return LoginFormState();
  }
}

class LoginFormState extends State<LoginForm> {
  String? username;
  String? password;

  int? loginResponseCode;

  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  Widget buildUsername(BuildContext context) {
    // TODO: implement build
    return TextFormField(
      decoration: InputDecoration(
          labelText: "Username",
          labelStyle: TextStyle(fontSize: 20, fontFamily: 'ABeeZee'),
          focusedBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(10.0),
            borderSide: BorderSide(
              color: Colors.blue,
            ),
          ),
          enabledBorder: OutlineInputBorder(
              borderRadius: BorderRadius.circular(10.0),
              borderSide: BorderSide(
                color: Colors.blue,
                width: 2.0,
              )),
          errorBorder: OutlineInputBorder(
              borderRadius: BorderRadius.circular(10.0),
              borderSide: BorderSide(
                color: Colors.red,
                width: 2.0,
              ))),
      validator: (value) {
        if (value == null || value.isEmpty) {
          return 'Mohon isi data ini';
        }

        return null;
      },
      onSaved: (value) {
        username = value;
      },
    );
  }

  Widget buildPassword(BuildContext context) {
    // TODO: implement build
    return TextFormField(
      decoration: InputDecoration(
          labelText: "Password",
          labelStyle: TextStyle(fontSize: 20, fontFamily: 'ABeeZee'),
          focusedBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(10.0),
            borderSide: BorderSide(
              color: Colors.blue,
            ),
          ),
          enabledBorder: OutlineInputBorder(
              borderRadius: BorderRadius.circular(10.0),
              borderSide: BorderSide(
                color: Colors.blue,
                width: 2.0,
              )),
          errorBorder: OutlineInputBorder(
              borderRadius: BorderRadius.circular(10.0),
              borderSide: BorderSide(
                color: Colors.red,
                width: 2.0,
              ))),
      validator: (value) {
        if (value == null || value.isEmpty) {
          return 'Mohon isi data ini';
        }
        return null;
      },
      onSaved: (value) {
        username = value;
      },
    );
  }

  Future<int> signIn(String? username, String? password) async {
    SharedPreferences sharedPreferences = await SharedPreferences.getInstance();

    final String apiUrl = 'http://localhost:8080/api/authenticate';
    final response = await http.post(Uri.parse(apiUrl),
        headers: {
          "content-type": "application/json",
          "accept": "application/json",
        },
        body: jsonEncode({
          "username": username,
          "password": password,
        }));

    var jsonResponse = json.decode(response.body);
    print(jsonResponse);
    if (response.statusCode == 200) {
      sharedPreferences.setString("token", jsonResponse["token"]);
    }

    return response.statusCode;
  }

  Widget buildSubmitButton(BuildContext context) {
    return ElevatedButton(
      style: ButtonStyle(
        shape: MaterialStateProperty.all<RoundedRectangleBorder>(
            RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(18.0),
                side: BorderSide(color: Colors.blue))),
      ),
      onPressed: () async {
        print(username);
        print(password);
        final isValid = _formKey.currentState!.validate();
        if (isValid) {
          _formKey.currentState!.save();

          final int responseCode = await signIn(username, password);

          setState(() {
            loginResponseCode = responseCode;
          });


          if (loginResponseCode==200) {
            _formKey.currentState!.reset();
            // Navigator.of(context).pushAndRemoveUntil(
            //     MaterialPageRoute(builder: (BuildContext context) => MyApp()),
            //         (Route<dynamic> route) => false
            // );
          } else if (loginResponseCode == 401) {
            ScaffoldMessenger.of(context).showSnackBar(
              SnackBar(
                  content: Text("Username atau Password salah"),
                      style: TextStyle(fontSize: 20, fontFamily: 'ABeeZee'))),
            );
          }
        }
      },
      child: Text(
        "Submit",
        style: TextStyle(fontSize: 20, fontFamily: 'ABeeZee'),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return MaterialApp(
     home: Scaffold(
       appBar: AppBar(
         backgroundColor: Colors.white,
         title: Text("Login"),
       ),
       body: SingleChildScrollView(
         child: Container(
           margin: EdgeInsets.all(24),
           child: Form(
             key: _formKey,
             child: Column(
               mainAxisAlignment: MainAxisAlignment.center,
               children: <Widget>[
                 buildUsername(context),
                 buildPassword(context),
                 buildSubmitButton(context),
               ],
             ),
           ),
         ),
       ),
     ),
    );
  }
}
