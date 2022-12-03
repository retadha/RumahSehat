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
        appBar: AppBar(title: Text("Log In"),),
        body: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Column(
            children: <Widget>[
              TextField(
                controller: _usernameController,
                decoration: InputDecoration(
                    labelText: 'Username'
                ),
              ),
              TextField(
                controller: _passwordController,
                obscureText: true,
                decoration: InputDecoration(
                    labelText: 'Password'
                ),
              ),
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
                  child: Text("Log In")
              ),
            ],
          ),
        )
      ),
    );
  }
}