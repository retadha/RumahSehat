import '../model/Tagihan.dart';
import 'package:http/http.dart' as http;
import 'dart:core';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '/main.dart';
import 'package:flutter_rumahsehat/page/daftar_tagihan.dart';

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

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        leading: new IconButton(
          icon: new Icon(Icons.arrow_back, color: Colors.blue),
          onPressed: () => Navigator.of(context).pop(),
        ), 
        title: const Text('Detail Tagihan', style: TextStyle(color:Colors.black)),
      ),
      body: SingleChildScrollView(
        padding: EdgeInsets.all(20.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            SizedBox(height: 20),
            Container(
              alignment: Alignment.center,
              decoration: BoxDecoration(),
              child: Padding(
                padding: const EdgeInsets.all(15.0),
                child: Text("Detail Tagihan",
                  style: TextStyle(
                    color: Colors.black,
                    fontSize: 30,
                    fontWeight: FontWeight.w500,
                  ),
                ),
              )
            ),
            SizedBox(height: 20),
            Container(
              child: FutureBuilder<TagihanElement>(
                future: futureTagihan,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    return Text('Kode Tagihan  :  ' + snapshot.data!.kode, style:_style());
                  } else if (snapshot.hasError) {
                    return Text('${snapshot.error}');
                  }
                  return const CircularProgressIndicator();
                },
              ),
            ),
            SizedBox(height: 20),
            Container(
              child: Text("Appointment", style: _style())
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
              child: Text("Jumlah Tagihan", style:_style())
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
              child: Text("Tanggal Tagihan", style:_style())
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
                      tanggalBuatStr = snapshot.data!.tanggalDibuat;
                    }
                    return Text(tanggalBuatStr);
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
              child: Text("Tanggal Bayar", style : _style())
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
                      tanggalBayarStr = snapshot.data!.tanggalBayar;
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
              child: FutureBuilder<TagihanElement>(
                future: futureTagihan,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    if (snapshot.data!.status == false){
                      return buttonConditionFalse(context, kode);
                    }  
                  }
                  return Container();
                },
              ),
            ),
          ]
        )
      )
    );
  }
   
  buttonConditionFalse(BuildContext context, String kode){
    return ElevatedButton(
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
    );
  }

  showConfirmDialog(BuildContext context, String kode){
    Widget cancelButton = TextButton(
      child: Text("Batal"),
      onPressed: () {
        Navigator.of(context, rootNavigator: true).pop();
      },
    );

    Widget continueButton = TextButton(
      child: Text("Konfirmasi"),
      onPressed: () {
        bayar(context, kode);
      },
    );

    AlertDialog alert = AlertDialog(
      content: Text("Apakah Anda yakin ingin membayar tagihan ini?"),
      actions: [
        cancelButton,
        continueButton,
      ],
    );

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return alert;
      },
    );
  }

  bayar(context, kode) async{
    SharedPreferences sharedPreferences = await SharedPreferences.getInstance();
    var token = sharedPreferences.getString("token");
    var url = 'https://apap-050.cs.ui.ac.id/api/bayar-tagihan/' + kode;
    final response = await http.get(Uri.parse(url),
      headers: <String, String>{
        'Authorization': 'Bearer $token',
        "content-type": "application/json",
        "accept": "application/json",
        'Access-Control-Allow-Origin': '*'
      },
    );
    Map<String, dynamic> data = jsonDecode(response.body);
    if (data["status"] == "berhasil"){
      showSuccess(context, kode);
    } else {
      if (data["status"] == "saldoKurang"){
        showFailedSaldo(context, kode);
      }
      else if (data["status"] == "stokKurang"){
        showFailedStok(context, kode);
      }
    }

  }

  showFailedSaldo(BuildContext context, String kode) {
    Widget cancelButton = TextButton(
      child: Text("Kembali"),
      onPressed: () {
        Navigator.of(context, rootNavigator: true).pop();
        Navigator.of(context, rootNavigator: true).pop();
      },
    );
  
    AlertDialog alert = AlertDialog(
      content: Text("Maaf, saldo Anda tidak mencukupi. Silakan Top up terlebih dahulu."),
      actions: [
        cancelButton,
      ],
    );

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return alert;
      },
    );
  }

  showFailedStok(BuildContext context, String kode) {
    Widget cancelButton = TextButton(
      child: Text("Kembali"),
      onPressed: () {
        Navigator.of(context, rootNavigator: true).pop();
        Navigator.of(context, rootNavigator: true).pop();
      },
    );
  
    AlertDialog alert = AlertDialog(
      content: Text("Maaf, Stok tidak mencukupi. Silakan menunggu ketersediaan obat"),
      actions: [
        cancelButton,
      ],
    );

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return alert;
      },
    );
  }

  showSuccess(BuildContext context, String kode) {
    Widget cancelButton = TextButton(
      child: Text("Kembali"),
      onPressed: () {
        Navigator.of(context, rootNavigator: true).pop();
        Navigator.of(context, rootNavigator: true).pop();
        Navigator.of(context).pushAndRemoveUntil(
          MaterialPageRoute(builder: (BuildContext context) => RumahSehatApp()),
            (Route<dynamic> route) => false
        );        
      }
    );
  
    AlertDialog alert = AlertDialog(
      content: Text("Selamat, Pembayaran Anda Sukses!"),
      actions: [
        cancelButton,
      ],
    );

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return alert;
      },
    );
  }

  Future<TagihanElement> fetchTagihan(String kode) async {
    SharedPreferences sharedPreferences = await SharedPreferences.getInstance();
    var token = sharedPreferences.getString("token");
      var url = 'https://apap-050.cs.ui.ac.id/api/tagihan/' + kode;
      final response = await http.get(Uri.parse(url),
      headers: <String, String>{
        'Authorization': 'Bearer $token',
        "content-type": "application/json",
        "accept": "application/json",
        'Access-Control-Allow-Origin': '*'
        });
      Map<String, dynamic> data = jsonDecode(response.body);
      return TagihanElement.fromJson(jsonDecode(response.body));
  }
}

