import '../model/Tagihan.dart';
import 'package:http/http.dart' as http;
import 'dart:core';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
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

        return MaterialApp(
            home: Scaffold(
                appBar: AppBar(
                title: const Text('Daftar Tagihan'),
                ),
                body: SingleChildScrollView(
                  child: Container(
                    child: FutureBuilder<Tagihan>(
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
                          return Container(
                            alignment: Alignment.center,
                            child: Text("Anda belum memiliki tagihan.", )
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
            ), 
        );
    }

    Future<Tagihan> fetchTagihan() async {
      SharedPreferences sharedPreferences = await SharedPreferences.getInstance();
      var token = sharedPreferences.getString("token");
        var url = 'http://localhost:8080/api/list-tagihan/';
        final response = await http.get(Uri.parse(url),
        headers: <String, String>{'Authorization': 'Bearer $token'},);
        Map<String, dynamic> data = jsonDecode(response.body);
        print(data);
        return Tagihan.fromJson(jsonDecode(response.body));
    }

      Widget buildCard(data) {
        DateTime tanggalDibuat = data.tanggalDibuat;
        String tanggalStr = DateFormat.yMMMd().format(tanggalDibuat);
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
                shape: RoundedRectangleBorder(
                  side: BorderSide(
                      // border color
                      color: Colors.blue.shade200,
                      // border thickness
                      width: 4)),
                  child: Container(
                    padding: const EdgeInsets.fromLTRB(0,10, 0, 0 ),
                alignment: Alignment.center,
                child: Column(
                  children: [
                    Text(data.kode,
                        style: TextStyle(
                          fontSize: 20,
                        )),
                        Container(
                        padding: const EdgeInsets.fromLTRB(25, 30, 7, 10),
                        alignment: Alignment.centerLeft,
                        child: Text('Total Tagihan : Rp ' + data.jumlahTagihan.toString(),
                            style: TextStyle(
                              height: 1,
                            ))),
                    Container(
                        padding: const EdgeInsets.fromLTRB(25, 10, 7, 10),
                        alignment: Alignment.centerLeft,
                        child: Text('Tanggal Terbuat : ' + tanggalStr,
                            style: TextStyle(
                              height: 1,
                            ))),
                    Container(
                        padding: const EdgeInsets.fromLTRB(25, 10, 7, 10),
                        alignment: Alignment.centerLeft,
                        child: Text(
                            'Status: ' + statusStr,
                            style: TextStyle(
                              height: 1,
                            ))),
                    Container(
                        alignment: Alignment.center,
                        height: 100,
                        padding: const EdgeInsets.fromLTRB(60, 0, 60, 5),
                        child: ElevatedButton(
                            child: const Text('Detail'),
                            onPressed: () {
                              Navigator.of(context).push(MaterialPageRoute(
                                builder: (context) => DetailTagihanPage(data.kode),
                              ));
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
              )),
            ],
          ),
        );
      }


}


