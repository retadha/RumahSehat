import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_rumahsehat/auth/login.dart';
import 'package:flutter_rumahsehat/main.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';

class Register extends StatelessWidget {
  final TextEditingController _usernameController = TextEditingController();
  final TextEditingController _namaController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _saldoController = TextEditingController();
  final TextEditingController _umurController = TextEditingController();

  void displayDialog(context, title, text) => showDialog(
    context: context,
    builder: (context) =>
        AlertDialog(
            title: Text(title),
            content: Text(text)
        ),
  );

  void emptyFields() {
    _usernameController.clear();
    _namaController.clear();
    _emailController.clear();
    _passwordController.clear();
    _saldoController.clear();
    _umurController.clear();
  }

  Future<int?> attemptRegister(String username,
                                  String nama,
                                  String email,
                                  String password,
                                  String saldo,
                                  String umur) async {
    final String apiUrl = 'https://apap-050.cs.ui.ac.id/api/users/pasien/register';
    var res = await http.post(
        Uri.parse(apiUrl),
        headers: {
          "content-type": "application/json",
          "accept": "application/json",
        },
        body: jsonEncode({
          "username" : username,
          "nama" : nama,
          "email" : email,
          "role" : "Pasien",
          "password" : password,
          "saldo" : saldo,
          "umur" : umur
        }));
    return res.statusCode;
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          leading: IconButton(
            icon: Icon(Icons.arrow_back),
            color: Colors.black,
            iconSize: 20.0,
            onPressed: () {
              Navigator.of(context).pushAndRemoveUntil(
                  MaterialPageRoute(builder: (BuildContext context) => LoginPage()),
                      (Route<dynamic> route) => false
              );
            },
          ),
          centerTitle: true,
          backgroundColor: Colors.white,
          title: Text("Sign Up",
              style: TextStyle(color: Colors.black)
          ),


        ),
        body: Align(
          alignment: Alignment.center,
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                SizedBox(
                  width: 500,
                  child: TextField(
                    controller: _usernameController,
                    decoration: InputDecoration(
                        focusedBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(15),
                          borderSide: BorderSide(color: Colors.black),
                        ),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(15)
                        ),
                        labelText: 'Username'
                    ),
                  ),
                ),
                SizedBox(height: 10),
                SizedBox(
                  width: 500,
                  child: TextField(
                    controller: _namaController,
                    decoration: InputDecoration(
                        focusedBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(15),
                          borderSide: BorderSide(color: Colors.black),
                        ),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(15)
                        ),
                        labelText: 'Nama'
                    ),
                  ),
                ),
                SizedBox(height: 10),
                SizedBox(
                  width: 500,
                  child: TextField(
                    controller: _emailController,
                    decoration: InputDecoration(
                        focusedBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(15),
                          borderSide: BorderSide(color: Colors.black),
                        ),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(15)
                        ),
                        labelText: 'Email'
                    ),
                  ),
                ),
                SizedBox(height: 10),
                SizedBox(
                  width: 500,
                  child: TextField(
                    controller: _passwordController,
                    obscureText: true,
                    decoration: InputDecoration(
                        focusedBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(15),
                          borderSide: BorderSide(color: Colors.black),
                        ),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(15)
                        ),
                        labelText: 'Password'
                    ),
                  ),
                ),
                SizedBox(height: 10),
                SizedBox(
                  width: 500,
                  child: TextField(

                    inputFormatters: [FilteringTextInputFormatter.digitsOnly],
                    keyboardType: TextInputType.number,
                    controller: _saldoController,
                    decoration: InputDecoration(
                        focusedBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(15),
                          borderSide: BorderSide(color: Colors.black),
                        ),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(15)
                        ),
                        labelText: 'Saldo'
                    ),
                  ),
                ),
                SizedBox(height: 10),
                SizedBox(
                  width: 500,
                  child: TextField(
                    inputFormatters: [FilteringTextInputFormatter.digitsOnly],
                    keyboardType: TextInputType.number,
                    controller: _umurController,
                    decoration: InputDecoration(
                        focusedBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(15),
                          borderSide: BorderSide(color: Colors.black),
                        ),
                        border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(15)
                        ),
                        labelText: 'Umur'
                    ),
                  ),
                ),
                SizedBox(height: 10),

                SizedBox(
                  width: 80,
                  height: 30,
                  child: TextButton(
                      onPressed: () async {
                        SharedPreferences sharedPreferences = await SharedPreferences.getInstance();

                        var username = _usernameController.text;
                        var nama = _namaController.text;
                        var email = _emailController.text;
                        var password = _passwordController.text;
                        var saldo = _saldoController.text;
                        var umur = _umurController.text;

                        var response = await attemptRegister(username, nama, email, password, saldo, umur);

                        if (response == 200) {
                          displayDialog(context, "Registrasi berhasil", 'Pasien dengan username $username berhasil dibuat');
                          emptyFields();

                        } else if (response == 409) {
                          displayDialog(context, "Registrasi Gagal", "Username tersebut sudah terdaftar");

                        } else if (response == 400) {
                          displayDialog(context, "Registrasi Gagal", "Pastikan data diisi dengan tepat");
                        }
                      },
                      child: Text("Sign Up",
                          style: new TextStyle(
                            fontSize: 16,
                          )
                      ),
                      style: TextButton.styleFrom(
                        primary: Colors.white,
                        backgroundColor: Colors.black,

                      )

                  ),
                ),
              ],
            ),
          ),
        )
      ),
    );
  }
}