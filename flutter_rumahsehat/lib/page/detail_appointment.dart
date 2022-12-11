import '../model/Appointment.dart';
import 'package:http/http.dart' as http;
import 'dart:core';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '/page/detail_resep.dart';

class DetailAppointmentPage extends StatefulWidget {
  final String id;
  DetailAppointmentPage(this.id) : super(key: null);

  @override
  _DetailAppointmentPage createState() => _DetailAppointmentPage();
}

class _DetailAppointmentPage extends State<DetailAppointmentPage> {

  TextStyle _style(){
    return TextStyle(
        fontWeight: FontWeight.bold
    );
  }

  @override
  Widget build(BuildContext context) {
    String id = widget.id;
    Future<AppointmentElement> futureAppointment = fetchAppointment(id);

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        leading: new IconButton(
            onPressed: () => Navigator.of(context).pop(),
            icon: new Icon(Icons.arrow_back, color: Colors.blue),
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
                child: Text(
                  "Detail Appointment",
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
              child: FutureBuilder<AppointmentElement>(
                future: futureAppointment,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    return Text('ID Appointment: ' + snapshot.data!.id, style:_style());
                  } else if (snapshot.hasError) {
                    return Text('${snapshot.error}');
                  }
                  return const CircularProgressIndicator();
                },
              ),
            ),
            SizedBox(height: 20),
            Container(
                child: Text("Waktu Awal", style:_style())
            ),
            Container(
              child: FutureBuilder<AppointmentElement>(
                future: futureAppointment,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    return Text(snapshot.data!.waktuAwal);
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
              child: FutureBuilder<AppointmentElement>(
                future: futureAppointment,
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
                child: Text("Dokter", style:_style())
            ),
            SizedBox(height: 10),
            Container(
              child: FutureBuilder<AppointmentElement>(
                future: futureAppointment,
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
                child: Text("Pasien", style:_style())
            ),
            SizedBox(height: 10),
            Container(
              child: FutureBuilder<AppointmentElement>(
                future: futureAppointment,
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
            //DETAIL RESEP
            SizedBox(height: 20),
            Container(
              child: FutureBuilder<AppointmentElement>(
                future: futureAppointment,
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    int idResep = snapshot.data!.resep ?? 0;
                    if (idResep != 0) {
                      return buttonDetailResep(context, idResep.toString());
                    }
                  }
                  return Container();
                }
              )
            )
          ],
        )
      )
    );
  }

  buttonDetailResep(BuildContext context, String id) {
    return ElevatedButton(
      onPressed: () {
        Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => DetailResepPage(id))
        );
      },
      child: const Text('Detail Resep'),
      style: ElevatedButton.styleFrom(
          primary: Colors.green,
          textStyle: const TextStyle(
              color: Colors.white
          )
      )
    );
  }

  Future<AppointmentElement> fetchAppointment(String id) async {
    SharedPreferences sharedPreferences = await SharedPreferences.getInstance();
    var token = sharedPreferences.getString("token");
    var url = 'http://localhost:8080/api/appointment/' + id;
    final response = await http.get(Uri.parse(url),
    headers: <String, String>{'Authorization': 'Bearer $token'},);
    Map<String, dynamic> data = jsonDecode(response.body);
    print(data);
    return AppointmentElement.fromJson(jsonDecode(response.body));
  }
}