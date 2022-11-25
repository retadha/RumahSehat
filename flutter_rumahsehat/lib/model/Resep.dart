// To parse this JSON data, do
//
//     final resep = resepFromJson(jsonString);

import 'dart:convert';

Resep resepFromJson(String str) => Resep.fromJson(json.decode(str));

String resepToJson(Resep data) => json.encode(data.toJson());

class Resep {
  Resep({
    required this.dokter,
    required this.listObat,
    required this.id,
    required this.pasien,
    required this.status,
    this.apoteker,
  });

  String dokter;
  List<ListObat> listObat;
  int id;
  String pasien;
  bool status;
  String? apoteker;

  factory Resep.fromJson(Map<String, dynamic> json) => Resep(
    dokter: json["dokter"],
    listObat: List<ListObat>.from(json["listObat"].map((x) => ListObat.fromJson(x))),
    id: json["id"],
    pasien: json["pasien"],
    status: json["status"],
    apoteker: json["apoteker"] ?? null,
  );

  Map<String, dynamic> toJson() => {
    "dokter": dokter,
    "listObat": List<dynamic>.from(listObat.map((x) => x.toJson())),
    "id": id,
    "pasien": pasien,
    "status": status,
    "apoteker": apoteker,
  };
}

class ListObat {
  ListObat({
    required this.kuantitas,
    required this.namaObat,
  });

  int kuantitas;
  String namaObat;

  factory ListObat.fromJson(Map<String, dynamic> json) => ListObat(
    kuantitas: json["kuantitas"],
    namaObat: json["namaObat"],
  );

  Map<String, dynamic> toJson() => {
    "kuantitas": kuantitas,
    "namaObat": namaObat,
  };
}
