import '../model/Resep.dart';
import 'package:http/http.dart' as http;
import 'dart:core';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';


class DetailResepPage extends StatefulWidget {
  final String id;
  DetailResepPage(this.id) : super(key: null);

  @override
  _DetailResepPage createState() => _DetailResepPage();

}

class _DetailResepPage extends State<DetailResepPage> {

  TextStyle _style(){
    return TextStyle(
      fontWeight: FontWeight.bold
    );
  }

  @override
  Widget build(BuildContext context) {
    String id = widget.id;
    Future<Resep> futureResep = fetchResep(id);

    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.white,
          leading: new IconButton(
            icon: new Icon(Icons.arrow_back, color: Colors.blue),
            onPressed: () => Navigator.of(context).pop(),
          ), 
          title: const Text('Detail Resep', style: TextStyle(color:Colors.black)),
        ),
        body: SingleChildScrollView(
          padding: EdgeInsets.all(20.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              SizedBox(height: 20),
              Container(
                alignment: Alignment.center,
                decoration: BoxDecoration(
                ),
                child: Padding(
                padding: const EdgeInsets.all(15.0),
                child: Text(
                  "Detail Resep",
                  style: TextStyle(
                    color: Colors.black,
                    fontSize: 30,
                    fontWeight: FontWeight.w500,
                  ),
                ),
                )
              ),
              Container(
                child: FutureBuilder<Resep>(
                future: futureResep,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    int idInt = snapshot.data!.id;
                    String idStr = idInt.toString();
                    return Text('ID Resep :  ' + idStr, style:_style());
                  } else if (snapshot.hasError) {
                    return Text('${snapshot.error}');
                  }
                  return const CircularProgressIndicator();
                },
              ),
              ),
              SizedBox(height: 20),
              Container(
                child: Text("Pasien", style: _style())
              ),
              SizedBox(height: 10),
              Container(
                child: FutureBuilder<Resep>(
                future: futureResep,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    return Text(snapshot.data!.pasien);
                  } else if (snapshot.hasError) {
                    return Text('${snapshot.error}');
                  }
                  return const CircularProgressIndicator();
                },
              ),
              ),
              SizedBox(height: 20),
              Container(
                child: Text("Dokter", style:_style())
              ),
              SizedBox(height: 10),
              Container(
                child: FutureBuilder<Resep>(
                future: futureResep,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    return Text(snapshot.data!.dokter);
                  } else if (snapshot.hasError) {
                    return Text('${snapshot.error}');
                  }
                  return const CircularProgressIndicator();
                },
              ),
              ),
              SizedBox(height: 20),
              Container(
                child: Text("Status", style:_style())
              ),
              SizedBox(height: 10),
              Container(
                child: FutureBuilder<Resep>(
                future: futureResep,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    String statusStr = "";
                    if (snapshot.data!.status == false){
                      statusStr = "Belum Selesai";
                    } else {
                      statusStr = "Selesai";
                    }
                    return Text(statusStr);
                  } else if (snapshot.hasError) {
                    return Text('${snapshot.error}');
                  }
                  return const CircularProgressIndicator();
                },
              ),
              ),
              SizedBox(height: 20),
              Container(
                child: Text("Apoteker", style : _style())
              ),
              SizedBox(height: 10),
              Container(
                child: FutureBuilder<Resep>(
                future: futureResep,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    String apotekerStr = "";
                    if (snapshot.data!.apoteker == null){
                      apotekerStr = "-";
                    } else {
                      apotekerStr = snapshot.data!.apoteker.toString();
                    }
                    return Text(apotekerStr);
                  }
                  return const CircularProgressIndicator();
                },
              ),
              ),
              SizedBox(height: 20),
              SizedBox(height: 20),
              Container(
                child: Text("Daftar Obat", style: TextStyle(
                    fontSize: 18,
                    fontWeight: FontWeight.bold,
                  ),)
              ),
              SizedBox(height: 10),
              Container(
                child: FutureBuilder<Resep>(
                future: futureResep,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {

                    return Container(
                      child: DataTable(
                        columns:[
                          DataColumn(label: Text("Nama Obat", 
                            style: TextStyle(
                              fontWeight: FontWeight.w600,
                            )
                          )),
                          DataColumn(label: Text("Kuantitas", 
                            style: TextStyle(
                              fontWeight: FontWeight.w600,
                            )
                          ))
                        ],
                        rows:snapshot.data!.listObat.map((obat)=> 
                          DataRow(
                            cells:[
                              DataCell(Text(obat.namaObat)),
                              DataCell(Text(obat.kuantitas.toString()))
                            ]
                          )
                        ).toList(), 
                      )
                    );
                  } else if (snapshot.hasError) {
                    return Text('${snapshot.error}');
                  }
                  return const CircularProgressIndicator();
                },
              ),
              ),
            ]
          )
        )
      );
    
  }
}

Future<Resep> fetchResep(String id) async {
  String idResep = id;

  SharedPreferences sharedPreferences = await SharedPreferences.getInstance();
  var token = sharedPreferences.getString("token");

  var url = 'https://apap-050.cs.ui.ac.id/api/resep/' + idResep;
  final response = await http.get(Uri.parse(url), 
    headers: <String, String>{
      'Authorization': 'Bearer $token',
      "content-type": "application/json",
      "accept": "application/json",
      'Access-Control-Allow-Origin': '*'
    });

  Map<String, dynamic> data = jsonDecode(response.body);
  print(data);
  return Resep.fromJson(jsonDecode(response.body));
}

