import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_rumahsehat/auth/login.dart';
import 'package:flutter_rumahsehat/main.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';
import 'package:regexpattern/regexpattern.dart';

class Register extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return RegisterState();
  }
}

class RegisterState extends State<Register> {
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  var username;
  var nama;
  var email;
  var password;
  var umur;

  void displayDialog(context, title, text) => showDialog(
    context: context,
    builder: (context) =>
        AlertDialog(
            title: Text(title),
            content: Text(text)
        ),
  );

  String? validatePassword(String? password) {
    RegExp strongPassword = RegExp(r'^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#\$&*~]).{8,}$');
    if (password!.isEmpty) {
      return 'Mohon isi password';
    } else {
      if (!strongPassword.hasMatch(password)) {
        return 'Password minimal 8 karakter alfanumerik dengan karakter spesial, mengandung huruf besar/kecil';
      } else {
        return null;
      }
    }
  }

  String? validateEmail(String? email) {

    if (email!.isEmpty) {
      return 'Mohon isi email';
    } else {
      if (!email.isEmail()) {
        return 'Mohon isi email dengan format yang benar';
      } else {
        return null;
      }
    }
  }

  String? validateName(String? name) {

    print(name!.length);
    if (name!.isEmpty) {
      return 'Mohon isi nama';
    } else {
      if (name.length < 2) {
      return "Nama minimal 2 huruf";
      }
    }
  }

  String? validateUsername(String? username) {

    if (username!.isEmpty) {
      return 'Mohon isi username';
    } else {
      if (!username.isUsername()) {
        return "Mohon isi username dengan format yang benar";
      } else {
        return null;
      }
    }
  }


  Future<int?> attemptRegister(String username,
                                  String nama,
                                  String email,
                                  String password,
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
          "saldo" : "0",
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
          child: Form(
            key: _formKey,
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: SingleChildScrollView(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    SizedBox(
                      width: 550,
                      child: TextFormField(
                        // controller: _usernameController,
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
                        validator: validateUsername,
                        onSaved: (value) {
                          username = value;
                        },
                      ),
                    ),
                    SizedBox(height: 15),
                    SizedBox(
                      width: 550,
                      child: TextFormField(
                        // controller: _namaController,
                        inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z ]'))], // alfabetik
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
                        validator: validateName,
                        onSaved: (value) {
                          nama = value;
                        },
                      ),
                    ),
                    SizedBox(height: 15),
                    SizedBox(
                      width: 550,
                      child: TextFormField(
                        // controller: _emailController,
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
                        validator: validateEmail,
                        onSaved: (value) {
                          email = value;
                        },
                      ),
                    ),
                    SizedBox(height: 15),
                    SizedBox(
                      width: 550,
                      child: TextFormField(
                        // controller: _passwordController,
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
                        validator: validatePassword,
                        onSaved: (value) {
                          password = value;
                        },
                      ),
                    ),
                    SizedBox(height: 15),
                    SizedBox(
                      width: 550,
                      child: TextFormField(
                        inputFormatters: [FilteringTextInputFormatter.digitsOnly],
                        keyboardType: TextInputType.number,
                        // controller: _umurController,
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
                        validator: (value) {
                          if (value == null || value.isEmpty) {
                            return 'Mohon isi umur';
                          }

                          return null;
                        },
                        onSaved: (value) {
                          umur = value;
                        },
                      ),
                    ),
                    SizedBox(height: 15),
                    SizedBox(
                      width: 80,
                      height: 30,
                      child: TextButton(
                          onPressed: () async {

                            final isValid = _formKey.currentState!.validate();

                            if (isValid) {
                              _formKey.currentState!.save();
                              SharedPreferences sharedPreferences = await SharedPreferences.getInstance();

                              var response = await attemptRegister(username, nama, email, password, umur);

                              if (response == 200) {
                                displayDialog(context, "Registrasi berhasil", 'Pasien dengan username $username berhasil dibuat');
                                // emptyFields();
                                _formKey.currentState!.reset();


                              } else if (response == 409) {
                                displayDialog(context, "Registrasi Gagal", "Username tersebut sudah terdaftar");

                              } else if (response == 400) {
                                displayDialog(context, "Registrasi Gagal", "Pastikan data diisi dengan tepat");
                              }
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
              )
            ),
          ),

        )
      ),
    );
  }
}