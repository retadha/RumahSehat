import '../model/Tagihan.dart';
import 'package:http/http.dart' as http;
import 'dart:core';
import 'dart:convert';
import 'package:flutter/material.dart';
import '/page/detail_tagihan.dart';
import 'package:shared_preferences/shared_preferences.dart';

class DaftarTagihanPage extends StatefulWidget {
  DaftarTagihanPage() : super(key: null);

  @override
  _DaftarTagihanPage createState() => _DaftarTagihanPage();
}

class _DaftarTagihanPage extends State<DaftarTagihanPage> {

  @override
  Widget build(BuildContext context){
    Future<Tagihan> futureTagihan = fetchTagihan();
    return Scaffold(
      body: SingleChildScrollView(
        child: Container(
          child: 
            FutureBuilder<Tagihan>(
              future: futureTagihan,
              builder: (context, snapshot) {
              if (snapshot.hasData) {
                if (snapshot.data!.tagihan.length!=0){
                  return Container(
                    child: ListView.builder(
                    physics: NeverScrollableScrollPhysics(),
                    shrinkWrap: true,
                    itemCount: snapshot.data!.tagihan.length,
                    itemBuilder: (BuildContext context, int index) {
                      return buildCard(snapshot.data!.tagihan[index]);
                    })
                  );
                } else {
                  return Column(
                    children: const [
                      SizedBox(
                        height: 100,
                      ),
                      Icon(
                        Icons.error_outline,
                        color: Colors.red,
                        size: 60.0,
                      ),
                      SizedBox(
                        height: 30,
                      ),
                      Align(
                        alignment: Alignment.center,
                        child: Text(
                          'Saat Ini Anda Belum Memiliki Tagihan',
                          style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                        ),
                      ),
                    ],
                  );
                }
              } else if (snapshot.hasError) {
                return Text('${snapshot.error}');
              }
                return const CircularProgressIndicator();
            },
          )
        )
      )
    );
  }

  Future<Tagihan> fetchTagihan() async {
    SharedPreferences sharedPreferences = await SharedPreferences.getInstance();
    var token = sharedPreferences.getString("token");
      var url = 'https://apap-050.cs.ui.ac.id/api/list-tagihan/';
      final response = await http.get(Uri.parse(url),
      headers: <String, String>{
        'Authorization': 'Bearer $token',
        "content-type": "application/json",
        "accept": "application/json",
        'Access-Control-Allow-Origin': '*'
        });
      Map<String, dynamic> data = jsonDecode(response.body);
      return Tagihan.fromJson(jsonDecode(response.body));
  }


  Widget buildCard(data) {
    bool statusBool = data.status;
    String statusStr = "";
    if (statusBool == false){
      statusStr = "Belum Lunas";
    } else {
      statusStr = "Lunas";

    }
    return Padding(
      padding: const EdgeInsets.fromLTRB(60,20, 60, 20  ),
      child: Column(
        children: <Widget>[
          Card(
            shadowColor: Colors.grey,
            elevation: 7,
            shape: RoundedRectangleBorder(
              side: BorderSide(
                color: Colors.blueGrey.shade200,
                width: 3
              )
            ),
            child: Container(
              padding: const EdgeInsets.fromLTRB(0,30, 0, 0 ),
              alignment: Alignment.center,
              child: Column(
                children: [
                  Text(data.kode, style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),),
                  Container(
                    padding: const EdgeInsets.fromLTRB(25, 30, 7, 10),
                    alignment: Alignment.centerLeft,
                    child: Text('Total Tagihan         : Rp ' + data.jumlahTagihan.toString(),
                      style: TextStyle(
                      height: 1,
                      )
                    )
                  ),
                  Container(
                    padding: const EdgeInsets.fromLTRB(25, 10, 7, 10),
                    alignment: Alignment.centerLeft,
                    child: Text('Tanggal Terbuat    : ' + data.tanggalDibuat,
                      style: TextStyle(
                      height: 1,
                      )
                    )
                  ),
                  Container(
                    padding: const EdgeInsets.fromLTRB(25, 10, 7, 10),
                    alignment: Alignment.centerLeft,
                    child: Text('Status                     : ' + statusStr, style: TextStyle(height: 1))
                  ),
                  Container(
                    alignment: Alignment.center,
                    height: 100,
                    padding: const EdgeInsets.fromLTRB(60, 0, 60, 5),
                    child: ElevatedButton(
                      child: const Text('Detail'),
                      onPressed: () {
                        Navigator.push(context,
                          MaterialPageRoute(builder: (context) => DetailTagihanPage(data.kode)),
                        );
                      },
                      style: ElevatedButton.styleFrom(
                        primary: Colors.green,
                        textStyle: const TextStyle(
                          color: Colors.white,
                        )
                      )
                    )
                  ),
                ],
              ),
            )
          ),
        ],
      ),
    );
  }
}

