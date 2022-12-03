import '../model/Tagihan.dart';
import 'package:http/http.dart' as http;
import 'dart:core';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class DetailTagihanPage extends StatefulWidget {
  final String kode;
  DetailTagihanPage(this.kode) : super(key: null);

  @override
  _DetailTagihanPage createState() => _DetailTagihanPage();

}

class _DetailTagihanPage extends State<DetailTagihanPage> {

  TextStyle _style(){
    return TextStyle(
      fontWeight: FontWeight.bold
    );
  }

  @override
  Widget build(BuildContext context) {
    String kode = widget.kode;
    Future<TagihanElement> futureTagihan = fetchTagihan(kode);

    return MaterialApp(
       home: Scaffold(
        appBar: AppBar(
          title: const Text('Detail Tagihan'),
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
                  "Detail Tagihan",
                  style: TextStyle(
                    color: Colors.blue,
                    fontSize: 30,
                    fontWeight: FontWeight.w500,
                  ),
                ),
                )
              ),
              Container(
                child: FutureBuilder<TagihanElement>(
                future: futureTagihan,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    return Text('Kode Tagihan: ' + snapshot.data!.kode, style:_style());
                  } else if (snapshot.hasError) {
                    return Text('${snapshot.error}');
                  }
                  return const CircularProgressIndicator();
                },
              ),
              ),
              SizedBox(height: 20),
              Container(
                child: Text("Appointment:", style: _style())
              ),
              SizedBox(height: 10),
              Container(
                child: FutureBuilder<TagihanElement>(
                future: futureTagihan,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    return Text(snapshot.data!.appointment);
                  } else if (snapshot.hasError) {
                    return Text('${snapshot.error}');
                  }
                  return const CircularProgressIndicator();
                },
              ),
              ),
              SizedBox(height: 20),
              Container(
                child: Text("Jumlah Tagihan:", style:_style())
              ),
              SizedBox(height: 10),
              Container(
                child: FutureBuilder<TagihanElement>(
                future: futureTagihan,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    int jumlahInt= snapshot.data!.jumlahTagihan;
                    String jumlahStr = jumlahInt.toString();
                    return Text(jumlahStr);
                  } else if (snapshot.hasError) {
                    return Text('${snapshot.error}');
                  }
                  return const CircularProgressIndicator();
                },
              ),
              ),
              SizedBox(height: 20),
              Container(
                child: Text("Tanggal Tagihan:", style:_style())
              ),
              SizedBox(height: 10),
              Container(
                child: FutureBuilder<TagihanElement>(
                future: futureTagihan,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    String tanggalBuatStr = "";
                    if (snapshot.data!.tanggalDibuat == null){
                      tanggalBuatStr = "-";
                    } else {
                      tanggalBuatStr = DateFormat.yMMMd().format(snapshot.data!.tanggalDibuat);
                    }
                    return Text(tanggalBuatStr);
                  }
                  return const CircularProgressIndicator();
                },
              ),
              ),
              SizedBox(height: 20),
              Container(
                child: Text("Status:", style:_style())
              ),
              SizedBox(height: 10),
              Container(
                child: FutureBuilder<TagihanElement>(
                future: futureTagihan,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    String statusStr = "";
                    if (snapshot.data!.status == false){
                      statusStr = "Belum Dibayar";
                    } else {
                      statusStr = "Lunas";
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
                child: Text("Tanggal Bayar:", style : _style())
              ),
              SizedBox(height: 10),
              Container(
                child: FutureBuilder<TagihanElement>(
                future: futureTagihan,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    String tanggalBayarStr = "";
                    if (snapshot.data!.tanggalBayar == null){
                      tanggalBayarStr = "-";
                    } else {
                      tanggalBayarStr = DateFormat.yMMMd().format(snapshot.data!.tanggalBayar);
                    }
                    return Text(tanggalBayarStr);
                  }
                  return const CircularProgressIndicator();
                },
              ),
              ),
              Container(
                alignment: Alignment.center,
                height: 100,
                padding: const EdgeInsets.fromLTRB(60, 10, 60, 5),
                child: ElevatedButton(
                child: const Text('Bayar'),
                onPressed: () {
                  showConfirmDialog(context, kode);
                },
                style: ElevatedButton.styleFrom(
                    primary: Colors.green,
                    textStyle: const TextStyle(
                    color: Colors.white,
                    )
                )
            )
            ),
            ]
          )
        )
      ),
    );
  }
  showConfirmDialog(BuildContext context, String kode) {
    // set up the buttons
    Widget cancelButton = TextButton(
      child: Text("Batal"),
      onPressed: () {
        Navigator.of(context).pop();
      },
    );

    Widget continueButton = TextButton(
      child: Text("Konfirmasi"),
      onPressed: () {
        // submit(context, idUser);
        // Navigator.push(context,
        //     MaterialPageRoute(builder: (context) => BuatProjectPage(idUser)));
        // showAlertDialog(context, kode);
      },
    );

    // set up the AlertDialog
    AlertDialog alert = AlertDialog(
      content: Text("Apakah Anda yakin ingin membayar tagihan ini?"),
      actions: [
        cancelButton,
        continueButton,
      ],
    );

    // show the dialog
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return alert;
      },
    );
  }


    Future<TagihanElement> fetchTagihan(String kode) async {
        var url = 'http://localhost:8080/api/tagihan/' + kode;
        final response = await http.get(Uri.parse(url),
        headers: <String, String>{'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUGFzaWVuIiwidXVpZCI6IjMiLCJzdWIiOiJwYXNpZW4xIiwiaWF0IjoxNjY5MzY1OTg1LCJleHAiOjE2NjkzODM5ODV9.1gq10NjMot41jxs1mhVx1BTErSaryvfG1el_wNcXN80'},);
        Map<String, dynamic> data = jsonDecode(response.body);
        print(data);
        return TagihanElement.fromJson(jsonDecode(response.body));
    }
}

