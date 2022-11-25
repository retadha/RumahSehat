import 'package:meta/meta.dart';
import 'dart:convert';

Tagihan tagihanFromJson(String str) => Tagihan.fromJson(json.decode(str));

String tagihanToJson(Tagihan data) => json.encode(data.toJson());

class Tagihan {
    Tagihan({
        required this.tagihan,
    });

    List<TagihanElement> tagihan;

    factory Tagihan.fromJson(Map<String, dynamic> json) => Tagihan(
        tagihan: List<TagihanElement>.from(json["tagihan"].map((x) => TagihanElement.fromJson(x))),
    );

    Map<String, dynamic> toJson() => {
        "tagihan": List<dynamic>.from(tagihan.map((x) => x.toJson())),
    };
}

class TagihanElement {
    TagihanElement({
        this.tanggalBayar,
        required this.jumlahTagihan,
        required this.kode,
        required this.appointment,
        required this.tanggalDibuat,
        required this.status,
    });

    dynamic? tanggalBayar;
    int jumlahTagihan;
    String kode;
    String appointment;
    DateTime tanggalDibuat;
    bool status;

    factory TagihanElement.fromJson(Map<String, dynamic> json) => TagihanElement(
        tanggalBayar: json["tanggalBayar"] ?? null,
        jumlahTagihan: json["jumlahTagihan"],
        kode: json["kode"],
        appointment: json["appointment"],
        tanggalDibuat: DateTime.parse(json["tanggalDibuat"]),
        status: json["status"],
    );

    Map<String, dynamic> toJson() => {
        "tanggalBayar": tanggalBayar,
        "jumlahTagihan": jumlahTagihan,
        "kode": kode,
        "appointment": appointment,
        "tanggalDibuat": tanggalDibuat.toIso8601String(),
        "status": status,
    };
}
