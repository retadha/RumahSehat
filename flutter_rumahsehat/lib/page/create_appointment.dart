import 'package:intl/intl.dart';

import '../model/Appointment.dart';
import 'package:http/http.dart' as http;
import 'dart:core';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '/page/daftar_appointment.dart';

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

  TextEditingController dateinput = TextEditingController();

  @override
  void initState() {
    dateinput.text = "";
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
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
                  children: [
                    //waktu awal appointment
                    TextField(
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

                          if (pickedDate != null) {
                            print(pickedDate);
                            String formattedDate = DateFormat('dd MMMM yyyy')
                                .format(pickedDate);
                            print(formattedDate);

                            setState(() {
                              dateinput.text = formattedDate;
                            });
                          }
                          else {
                            print("Tanggal belum dipilih");
                          }
                        }
                    ),
                    //dokter - tarif

                    //submit button
                    ElevatedButton(
                      child: Text("Submit"),
                      style: ElevatedButton.styleFrom(
                          primary: Colors.green,
                          textStyle: const TextStyle(
                              color: Colors.white)
                      ),
                      onPressed: () {
                        if (_formKey.currentState!.validate()) {}
                      },
                    ),
                  ],
                )
            )
        )
    );
  }

  showSuccess(BuildContext context, String id) {
    Widget cancelButton = TextButton(
        child: Text("Kembali"),
        onPressed: () {
          Navigator.of(context, rootNavigator: true).pop();

          Navigator.of(context, rootNavigator: true).pop();
          Navigator.of(context).pushAndRemoveUntil(
              MaterialPageRoute(
                  builder: (BuildContext context) => DaftarAppointmentPage()),
                  (Route<dynamic> route) => false
          );
        }
    );

    Future<http.Response> createAppointment(context, String waktuAwal, String dokter) async {
      SharedPreferences sharedPreferences = await SharedPreferences
          .getInstance();
      var token = sharedPreferences.getString("token");
      String url = "http://localhost:8080/api/create-appointment";
      Map data = {
        'waktuAwal': waktuAwal,
        'dokter': dokter,
      };
      var body = json.encode(data);
      var response = await http.post(
          Uri.parse(url),
          headers: <String, String>{
            "Content-Type": "application/json",
            "Authorization": "Bearer $token",
            "Access-Control-Allow-Origin": "*"
          },
          body: body
      );
      if (response.statusCode == 200) {
        // showSuccess(context, id);
      }
      print(data);
      print("${response.statusCode}");
      print("${response.body}");
      return response;
    }
  }
}