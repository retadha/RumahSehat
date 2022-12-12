import 'package:flutter/material.dart';
import 'package:flutter_rumahsehat/model/PasienModel.dart';
import 'package:flutter_rumahsehat/page/topup_saldo.dart';
import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';

class ProfilPasienPage extends StatefulWidget {
  @override
  _ProfilPasienPage createState() => _ProfilPasienPage();
}

class _ProfilPasienPage extends State<ProfilPasienPage> {
  Future<PasienModel?> fetchPasien() async {
    SharedPreferences sharedPreferences = await SharedPreferences.getInstance();
    var token = sharedPreferences.getString("token");
    String url1 =
        "https://apap-050.cs.ui.ac.id/api/profil/user";
    String url2 =
        "https://apap-050.cs.ui.ac.id/api/profil/pasien";
    var headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token',
      'Access-Control-Allow-Origin': '*'
    };
    final response1 = await http.get(Uri.parse(url1), headers: headers);
    final response2 = await http.get(Uri.parse(url2), headers: headers);
    var data = jsonDecode(response1.body);
    data.addAll(jsonDecode(response2.body));

    PasienModel pasien = PasienModel.fromJson(data);

    if (response1.statusCode == 200) {
      return pasien;
    } else
      return null;
  }


  @override
  Widget build(BuildContext context) {

    return Scaffold(
        backgroundColor: Colors.grey[900],
        body: Container(
          child: FutureBuilder(
            future: fetchPasien(),
            builder: (BuildContext context, AsyncSnapshot snapshot) {
              if (snapshot.data == null) {
                return Container(
                    child: Center(child: Icon(Icons.error))
                );
              }
              else {
                return Container(
                    padding: EdgeInsets.fromLTRB(30, 40, 30, 0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                        Text(
                          'Nama Pasien',
                          style: TextStyle(
                              color: Colors.grey,
                              letterSpacing: 2
                          ),
                        ),
                        SizedBox(height: 10),
                        Text(
                          snapshot.data.nama,
                          style: TextStyle(
                              color: Colors.amberAccent[200],
                              letterSpacing: 2,
                              fontSize: 28,
                              fontWeight: FontWeight.bold
                          ),
                        ),


                        SizedBox(height: 30),
                        Text(
                          'Username',
                          style: TextStyle(
                              color: Colors.grey,
                              letterSpacing: 2
                          ),
                        ),
                        SizedBox(height: 10),
                        Text(
                          snapshot.data.username,
                          style: TextStyle(
                              color: Colors.amberAccent[200],
                              letterSpacing: 2,
                              fontSize: 28,
                              fontWeight: FontWeight.bold
                          ),
                        ),


                        SizedBox(height: 30),
                        Text(
                          'Email',
                          style: TextStyle(
                              color: Colors.grey,
                              letterSpacing: 2
                          ),
                        ),
                        SizedBox(height: 10),
                        Text(
                          snapshot.data.email,
                          style: TextStyle(
                              color: Colors.grey[400],
                              fontSize: 18,
                              letterSpacing: 1
                          ),
                        ),


                        SizedBox(height: 30),
                        Text(
                          'Saldo',
                          style: TextStyle(
                              color: Colors.grey,
                              letterSpacing: 2
                          ),
                        ),
                        SizedBox(height: 10),
                        Text(
                          'Rp' + snapshot.data.saldo.toString(),
                          style: TextStyle(
                              color: Colors.grey[400],
                              fontSize: 18,
                              letterSpacing: 1
                          ),
                        ),

                        SizedBox(height: 30),
                        TextButton(
                          style:
                          TextButton.styleFrom(
                              backgroundColor: Colors.amberAccent[200]),
                          onPressed: () {
                            Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (context) => TopUpSaldoPage(),)
                            );
                          },
                          child: Text(
                            "Top Up",
                            style: TextStyle(
                                color: Colors.grey[900],
                                letterSpacing: 2
                            ),
                          ),
                        ),
                      ],
                    )
                );
              }
            },
          ),
        )
    );
  }
}