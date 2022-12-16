import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

import '../main.dart';


class TopUpSaldoPage extends StatefulWidget {
  @override
  _TopUpSaldoPage createState() => _TopUpSaldoPage();
}

class _TopUpSaldoPage extends State<TopUpSaldoPage> {
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Top Up Saldo"),
      ),
      body: Form(
        key: _formKey,
        child: Container(
          padding: EdgeInsets.all(20.0),
          child: Column(
            children: [
              // TextField(),
              TextFormField(
                inputFormatters: [FilteringTextInputFormatter.digitsOnly],
                decoration: new InputDecoration(
                  hintText: "Rp0",
                  labelText: "Jumlah Uang",
                  icon: Icon(Icons.money_outlined),
                  border: OutlineInputBorder(
                      borderRadius: new BorderRadius.circular(5.0)),
                ),
                validator: (value) {
                  if (value!.isEmpty) {
                    return 'Jumlah uang tidak boleh kosong';
                  }
                  else{
                    showConfirmDialog(context, value);
                  }
                },
              ),
              ElevatedButton(
                child: Text(
                  "Submit",
                  style: TextStyle(color: Colors.white),
                ),
                onPressed: () {
                  if (_formKey.currentState!.validate()) {
                  }
                },
              ),
            ],
          ),
        ),
      ),
    );
  }

  Future<http.Response> topUp(context, String saldo) async {
    SharedPreferences sharedPreferences = await SharedPreferences.getInstance();
    var token = sharedPreferences.getString("token");
    String url =
        "https://apap-050.cs.ui.ac.id/api/profil/topupsaldo";
    Map data = {
      'saldo': saldo
    };
    var body = json.encode(data);
    var res = await http.post(
        Uri.parse(url),
        headers: <String, String>{
          "Content-Type": "application/json",
          "Authorization": "Bearer $token",

        },
        body: body
    );
    if (res.statusCode == 200){
      showSuccess(context, saldo);
    }
    print(data);
    print("${res.statusCode}");
    print("${res.body}");
    return res;
  }

  showConfirmDialog(context, String saldo){
    // set up the buttons
    Widget cancelButton = TextButton(
      child: Text("Batal"),
      onPressed: () {
        Navigator.of(context, rootNavigator: true).pop();
      },
    );

    Widget continueButton = TextButton(
      child: Text("Konfirmasi"),
      onPressed: () {
        topUp(context, saldo);

      },
    );

    // set up the AlertDialog
    AlertDialog alert = AlertDialog(
      content: Text("Apakah Anda yakin ingin melakukan TopUp sebesar Rp" + saldo+ "?"),
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

  showSuccess(BuildContext context, String kode) {
    // set up the buttons
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

    // set up the AlertDialog
    AlertDialog alert = AlertDialog(
      content: Text("Selamat, Top Up Saldo Anda Sukses!"),
      actions: [
        cancelButton,
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
}