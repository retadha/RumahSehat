// import '../model/Appointment.dart';
// import 'package:http/http.dart' as http;
// import 'dart:core';
// import 'dart:convert';
// import 'package:flutter/material.dart';
// import '../model/Pasien.dart';
// import 'package:shared_preferences/shared_preferences.dart';
// import '/main.dart';
// import 'package:flutter_rumahsehat/page/daftar_appointment.dart';
//
// class DetailAppointmentPage extends StatefulWidget {
//   final String id;
//   DetailAppointmentPage(this.id) : super(key: null);
//
//   @override
//   _DetailAppointmentPage createState() => _DetailAppointmentPage();
// }
//
// class _DetailAppointmentPage extends State<DetailAppointmentPage> {
//
//   TextStyle _style(){
//     return TextStyle(
//         fontWeight: FontWeight.bold
//     );
//   }
//
//   @override
//   Widget build(BuildContext context) {
//     String id = widget.id;
//     Future<AppointmentElement> futureAppointment = fetchAppointment(id);
//   }
//
//
// }