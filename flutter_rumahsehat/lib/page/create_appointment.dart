import 'package:intl/intl.dart';

import '../model/Dokter.dart';
import 'package:http/http.dart' as http;
import 'dart:core';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:shared_preferences/shared_preferences.dart';

class CreateAppointmentPage extends StatefulWidget {
  @override
  _CreateAppointmentPage createState() => _CreateAppointmentPage();
}

class _CreateAppointmentPage extends State<CreateAppointmentPage> {
  TextStyle _style() {
    return TextStyle(
        fontWeight: FontWeight.bold
    );
  }

  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  var waktuAwal;
  var dokter;

  TextEditingController dateinput = TextEditingController();

  @override
  Widget build(BuildContext context) {
    Future<Dokter> futureDokterAppointment = fetchDokterAppointment();
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.white,
          leading: new IconButton(
            onPressed: () => Navigator.of(context).pop(),
            icon: new Icon(Icons.arrow_back, color: Colors.green),
          ),
          title: const Text('Buat Appointment', style: TextStyle(color:Colors.black)),
        ),
        body: Form(
            key: _formKey,
            child: Container(
                padding: EdgeInsets.all(20.0),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    //waktu awal appointment
                    SizedBox(
                      width: 550,
                      child: TextField(
                        controller: dateinput,
                        decoration: InputDecoration(
                          icon: Icon(Icons.calendar_today),
                          labelText: "Waktu Awal"
                        ),
                        readOnly: true,
                        onTap: () async {
                          DateTime? pickedDate = await showDatePicker(
                              context: context,
                              initialDate: DateTime.now(),
                              firstDate: DateTime(2000),
                              lastDate: DateTime(2101)
                          );
                          validator: (value) {
                            if (pickedDate != null) {
                              String formattedDate = DateFormat('dd MMMM yyyy').format(pickedDate);
                              waktuAwal = formattedDate;
                            }
                            else {
                              print("Tanggal belum dipilih");
                            }
                          };
                        },
                      )
                    ),
                    //dokter - tarif
                    // SizedBox(height: 15),
                    // SizedBox(
                    //   width: 550,
                    //   child: DropdownButton(
                    //     value: dokter,
                    //
                    //   )
                    // ),
                    //button simpan
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

                            var response = await createAppointment(context, waktuAwal, dokter);

                            if (response == 200) {
                              displayDialog(context, "Appointment berhasil dibuat");
                              _formKey.currentState!.reset();
                            }

                            else if (response == 400) {
                              displayDialog(context, "Buat appointment gagal, pastikan data diisi dengan benar!");
                            }
                          }
                        },
                        child: Text("Simpan"),
                          style: TextButton.styleFrom(
                              primary: Colors.green,
                              textStyle: const TextStyle(
                                  color: Colors.white
                              )
                          )
                      )
                    )
                  ],
                )
            )
        )
    );
  }

  void displayDialog(context, title) => showDialog(
    context: context,
    builder: (context) => AlertDialog(title: Text(title)),
  );

    Future<int?> createAppointment(context, String waktuAwal, String dokter) async {
      SharedPreferences sharedPreferences = await SharedPreferences
          .getInstance();
      var token = sharedPreferences.getString("token");
      final String url = "https://apap-050.cs.ui.ac.id/api/create-appointment";
      var response = await http.post(
          Uri.parse(url),
          headers: <String, String>{
            "Content-Type": "application/json",
            "Authorization": "Bearer $token",
            "Access-Control-Allow-Origin": "*"
          },
          body: jsonEncode({
            'waktuAwal': waktuAwal,
            'dokter': dokter,
          }));
      return response.statusCode;
    }

  Future<Dokter> fetchDokterAppointment() async {
    var url = 'https://apap-050.cs.ui.ac.id/api/dokter-appointment';
    final response = await http.get(Uri.parse(url),
        headers: <String, String>{
          'Access-Control-Allow-Origin': '*'
        });
    Map<String, dynamic> data = jsonDecode(response.body);
    return Dokter.fromJson(jsonDecode(response.body));
  }
}