import 'package:meta/meta.dart';
import 'dart:convert';

class Pasien {
    Pasien({
        required this.umur,
        required this.nama,
        required this.id,
        required this.saldo,
        required this.email,
        required this.username,
    });

    int umur;
    String nama;
    String id;
    int saldo;
    String email;
    String username;

    factory Pasien.fromRawJson(String str) => Pasien.fromJson(json.decode(str));

    String toRawJson() => json.encode(toJson());

    factory Pasien.fromJson(Map<String, dynamic> json) => Pasien(
        umur: json["umur"],
        nama: json["nama"],
        id: json["id"],
        saldo: json["saldo"],
        email: json["email"],
        username: json["username"],
    );

    Map<String, dynamic> toJson() => {
        "umur": umur,
        "nama": nama,
        "id": id,
        "saldo": saldo,
        "email": email,
        "username": username,
    };

}
