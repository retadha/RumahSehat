import 'dart:convert';

Appointment appointmentFromJson(String str) => Appointment.fromJson(json.decode(str));
String appointmentToJson(Appointment data) => json.encode(data.toJson());

class Appointment {
  Appointment({
    required this.appointment,
  });

  List<AppointmentElement> appointment;

  factory Appointment.fromJson(Map<String, dynamic> json) => Appointment(
    appointment: List<AppointmentElement>.from(json["appointment"].map((x) => AppointmentElement.fromJson(x))),
  );

  Map<String, dynamic> toJson() => {
    "appointment": List<dynamic>.from(appointment.map((x) => x.toJson())),
  };
}

class AppointmentElement {
  String? dokter;
  String? id;
  String? pasien;
  String? waktuAwal;
  int? resep;
  String? tagihan;
  bool? status;

  AppointmentElement(
      { required this.dokter,
        required this.id,
        required this.pasien,
        required this.waktuAwal,
        this.resep,
        this.tagihan,
        required this.status});

  AppointmentElement.fromJson(Map<String, dynamic> json) {
    dokter = json['dokter'];
    id = json['id'];
    pasien = json['pasien'];
    waktuAwal = json['waktuAwal'];
    resep = json['resep'];
    tagihan = json['tagihan'];
    status = json['status'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['dokter'] = this.dokter;
    data['id'] = this.id;
    data['pasien'] = this.pasien;
    data['waktuAwal'] = this.waktuAwal;
    data['resep'] = this.resep;
    data['tagihan'] = this.tagihan;
    data['status'] = this.status;
    return data;
  }
}
