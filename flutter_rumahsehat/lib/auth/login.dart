import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_rumahsehat/main.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';

class LoginPage extends StatelessWidget {
  final TextEditingController _usernameController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();

  void displayDialog(context, title, text) => showDialog(
    context: context,
    builder: (context) =>
        AlertDialog(
            title: Text(title),
            content: Text(text)
        ),
  );

  Future<String?> attemptLogIn(String username, String password) async {
    final String apiUrl = 'http://localhost:8080/api/authenticate';
    var res = await http.post(
        Uri.parse(apiUrl),
        headers: {
          "content-type": "application/json",
          "accept": "application/json",
        },
        body: jsonEncode({
          "username": username,
          "password": password,
        }));
    if(res.statusCode == 200) return res.body;
    return null;
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.white,
          title: Text("Log In",
              style: TextStyle(color: Colors.black)
          ),
          actions: <Widget>[
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextButton(
                style: TextButton.styleFrom(

                  foregroundColor: Colors.white,
                  backgroundColor: Colors.blue,
                ),
                onPressed: () {},
                child: Text("Sign Up"),
              ),
            ),

          ],

        ),
        body: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              TextField(
                controller: _usernameController,
                decoration: InputDecoration(
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(30)
                    ),
                    labelText: 'Username'
                ),
              ),
              SizedBox(height: 10),
              TextField(
                controller: _passwordController,
                obscureText: true,
                decoration: InputDecoration(
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(30)
                    ),
                    labelText: 'Password'
                ),
              ),
              SizedBox(height: 10),
              TextButton(
                  onPressed: () async {
                    SharedPreferences sharedPreferences = await SharedPreferences.getInstance();

                    var username = _usernameController.text;
                    var password = _passwordController.text;

                    print(username);
                    print(password);

                    var response = await attemptLogIn(username, password);
                    print(response);

                    if(response != null) {
                      sharedPreferences.setString("token", json.decode(response)["token"]);
                      Navigator.of(context).pushAndRemoveUntil(
                          MaterialPageRoute(builder: (BuildContext context) => RumahSehatApp()),
                              (Route<dynamic> route) => false
                      );
                    } else {
                      displayDialog(context, "Login gagal", "Tidak ada akun pasien dengan username dan password tersebut");
                    }
                  },
                  child: Text("Log In"),
                  style: TextButton.styleFrom(
                    primary: Colors.white,
                    backgroundColor: Colors.black,

                  )

              ),
            ],
          ),
        )
      ),
    );
  }
}