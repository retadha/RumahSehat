import 'dart:convert';

Appointment appointmentFromJson(String str) => Appointment.fromJson(json.decode(str));
String appointmentToJson(Appointment data) => json.encode(data.toJson());

class Appointment {

}