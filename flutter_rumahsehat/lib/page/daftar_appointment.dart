import '../model/Appointment.dart';
import 'package:http/http.dart' as http;
import 'dart:core';
import 'dart:convert';
import 'package:flutter/material.dart';
import '/page/detail_appointment.dart';
import '/page/create_appointment.dart';
import 'package:shared_preferences/shared_preferences.dart';

class DaftarAppointmentPage extends StatefulWidget {
  DaftarAppointmentPage() : super(key: null);

  @override
  _DaftarAppointmentPage createState() => _DaftarAppointmentPage();
}

// class _DaftarAppointmentPage extends State<DaftarAppointmentPage> {
//   @override
//   Widget build(BuildContext context) {
//     Future<Appointment> futureAppointment = fetchAppointment();
//
//     return Scaffold(
//       body: SingleChildScrollView(
//         child: Container(
//           child:
//             FutureBuilder<Appointment>(
//               future: futureAppointment,
//               builder: (context, snapshot) {
//                 if (snapshot.hasData) {
//                   if (snapshot.data!.appointment.length!=0) {
//                     return Container(
//                       child: ListView.builder(
//                         physics: NeverScrollableScrollPhysics(),
//                         shrinkWrap: true,
//                         itemCount: snapshot.data!.appointment.length,
//                         itemBuilder: (BuildContext context, int index) {
//                           return buildCard(snapshot.data!.appointment[index]);
//                         })
//                     );
//                   } else {
//                     return Container(
//                       alignment: Alignment.center,
//                       child: Text("Anda belum memiliki appointment.")
//                     );
//                   }
//                 } else if (snapshot.hasError) {
//                   return Text('${snapshot.error}');
//                 }
//                 return const CircularProgressIndicator();
//               },
//             )
//         ),
//       )
//     );
//   }

class _DaftarAppointmentPage extends State<DaftarAppointmentPage> {
  @override
  Widget build(BuildContext context) {
    Future<Appointment> futureAppointment = fetchAppointment();

    return Scaffold(
        body: SingleChildScrollView(
          child:
            Column(
              children: [
                SizedBox(height: 20),
                Container(
                    alignment: Alignment.center,
                    child: ElevatedButton(
                        child: const Text('Buat Appointment'),
                        onPressed: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(builder: (context) => CreateAppointmentPage()),
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
                SizedBox(height: 20),
                Container(
                    child:
                    FutureBuilder<Appointment>(
                      future: futureAppointment,
                      builder: (context, snapshot) {
                        if (snapshot.hasData) {
                          if (snapshot.data!.appointment.length!=0) {
                            return Container(
                                child: ListView.builder(
                                    physics: NeverScrollableScrollPhysics(),
                                    shrinkWrap: true,
                                    itemCount: snapshot.data!.appointment.length,
                                    itemBuilder: (BuildContext context, int index) {
                                      return buildCard(snapshot.data!.appointment[index]);
                                    })
                            );
                          } else {
                            return Container(
                                alignment: Alignment.center,
                                child: Text("Anda belum memiliki appointment.")
                            );
                          }
                        } else if (snapshot.hasError) {
                          return Text('${snapshot.error}');
                        }
                        return const CircularProgressIndicator();
                      },
                    )
                ),
              ],
            )
        )
    );
  }

  Future<Appointment> fetchAppointment() async {
    SharedPreferences sharedPreferences = await SharedPreferences.getInstance();
    var token = sharedPreferences.getString("token");
    var url = 'http://localhost:8080/api/appointment/';
    final response = await http.get(Uri.parse(url),
    headers: <String, String>{
      'Authorization': 'Bearer $token',
      'Access-Control-Allow-Origin': '*'
    });
    Map<String, dynamic> data = jsonDecode(response.body);
    print(data);
    return Appointment.fromJson(jsonDecode(response.body));
  }

  Widget buildCard(data) {
    bool statusBool = data.status;
    String statusStr = "";
    if (statusBool == false){
      statusStr = "Belum Selesai";
    } else {
      statusStr = "Selesai";
    }
    return Padding(
        padding: const EdgeInsets.fromLTRB(60,20, 60, 20),
        child: Column(
            children: <Widget>[
              Card(
                shadowColor: Colors.grey,
                elevation: 7,
                shape: RoundedRectangleBorder(
                  side: BorderSide(
                    color: Colors.blueGrey.shade200,
                    width: 3)),
                  child: Container(
                    padding: const EdgeInsets.fromLTRB(0,30, 0, 0 ),
                    alignment: Alignment.center,
                    child: Column(
                      children: [
                        Text(data.id,
                        style: TextStyle(
                          fontSize: 20,
                        )),
                        Container(
                          padding: const EdgeInsets.fromLTRB(25, 10, 7, 10),
                          alignment: Alignment.centerLeft,
                          child: Text('Nama Dokter : ' + data.dokter.toString(),
                            style: TextStyle(
                              height: 1,
                            ))),
                        Container(
                          padding: const EdgeInsets.fromLTRB(25, 10, 7, 10),
                          alignment: Alignment.centerLeft,
                          child: Text('Waktu Awal : ' + data.waktuAwal,
                            style: TextStyle(
                              height: 1,
                            ))),
                        Container(
                          padding: const EdgeInsets.fromLTRB(25, 10, 7, 10),
                          alignment: Alignment.centerLeft,
                          child: Text('Status : ' + statusStr,
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
                              Navigator.push(
                                context,
                                MaterialPageRoute(builder: (context) => DetailAppointmentPage(data.id)),
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
                )),
            ],
        ),
    );
  }
}