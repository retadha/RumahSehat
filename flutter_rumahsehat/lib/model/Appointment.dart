import 'dart:convert';

Appointment appointmentFromJson(String str) => Appointment.fromJson(json.decode(str));
String appointmentToJson(Appointment data) => json.encode(data.toJson());

class Appointment {
  String id,
  String waktuAwal,
  bool status,
  String dokter,
  String pasien,
  String resep,

  Appointment({
    required this.id,
    required this.waktuAwal,
    required this.status,
    required this.dokter,
    required this.pasien,
    this.resep,
  });

  factory Appointment.fromJson(Map<String, dynamic> json) => Appointment(
    id: json["id"],
    waktuAwal: json["waktuAwal"],
    status: json["status"],
    dokter: json["dokter"],
    pasien: json["pasien"],
    resep: json["resep"],
  );

  Map<String, dynamic> toJson() => {
  "id": id,
  "waktuAwal": waktuAwal;
  "status": status,
  "dokter": dokter,
  "pasien": pasien,
  "resep": resep,
  };
}
