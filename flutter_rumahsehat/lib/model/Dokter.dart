import 'dart:convert';


Dokter dokterFromJson(String str) => Dokter.fromJson(json.decode(str));
String dokterToJson(Dokter data) => json.encode(data.toJson());

class Dokter {
  Dokter({
        required this.dokter,
      });

  List<DokterElement> dokter;

  factory Dokter.fromJson(Map<String, dynamic> json) => Dokter(
    dokter: List<DokterElement>.from(json["dokter"].map((x) => DokterElement.fromJson(x))),
  );

  Map<String, dynamic> toJson() => {
    "dokter": List<dynamic>.from(dokter.map((x) => x.toJson())),
  };
}

class DokterElement {
  String? uuid;
  String? nama;
  int? tarif;

  DokterElement({
    required this.uuid,
    required this.nama,
    required this.tarif
  });

  DokterElement.fromJson(Map<String, dynamic> json) {
    nama = json['nama'];
    tarif = json['tarif'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['uuid'] = this.uuid;
    data['nama'] = this.nama;
    data['tarif'] = this.tarif;
    return data;
  }
}