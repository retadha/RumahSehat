import '../model/Tagihan.dart';
import 'package:http/http.dart' as http;
import 'dart:core';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class DaftarTagihanPage extends StatefulWidget {
  final String username;
  DaftarTagihanPage(this.username) : super(key: null);

  @override
  _DaftarTagihanPage createState() => _DaftarTagihanPage();

}
class _DaftarTagihanPage extends State<DaftarTagihanPage> {

    @override
    Widget build(BuildContext context){
        String username = widget.username;
        Future<Tagihan> futureTagihan = fetchTagihan(username);

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

    Future<Tagihan> fetchTagihan(String username) async {
        var url = 'https://apap-050.cs.ui.ac.id/api/list-tagihan/' + username;
        final response = await http.get(Uri.parse(url));
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
                  child: Container(
                    padding: const EdgeInsets.fromLTRB(0,10, 0, 0 ),
                color: Colors.blue,
                alignment: Alignment.center,
                child: Column(
                  children: [
                    Text(data.kode,
                        style: TextStyle(
                          fontSize: 20,
                        )),
                    Container(
                        padding: const EdgeInsets.fromLTRB(15, 20, 7, 10),
                        alignment: Alignment.centerLeft,
                        child: Text('Tanggal Terbuat : ' + tanggalStr,
                            style: TextStyle(
                              height: 1,
                              color: Colors.white,
                            ))),
                    Container(
                        padding: const EdgeInsets.fromLTRB(15, 10, 7, 10),
                        alignment: Alignment.centerLeft,
                        child: Text(
                            'Status: ' + statusStr,
                            style: TextStyle(
                              height: 1,
                              color: Colors.white,
                            ))),
                    Container(
                        alignment: Alignment.center,
                        height: 100,
                        padding: const EdgeInsets.fromLTRB(60, 10, 60, 5),
                        child: ElevatedButton(
                            child: const Text('Detail'),
                            onPressed: () {
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


