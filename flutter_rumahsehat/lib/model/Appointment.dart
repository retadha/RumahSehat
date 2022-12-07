class Appointment {
  String? dokter;
  String? id;
  String? pasien;
  String? waktuAwal;
  int? resep;
  // String? tagihan;
  bool? status;

  Appointment(
      {this.dokter,
        this.id,
        this.pasien,
        this.waktuAwal,
        this.resep,
        // this.tagihan,
        this.status});

  Appointment.fromJson(Map<String, dynamic> json) {
    dokter = json['dokter'];
    id = json['id'];
    pasien = json['pasien'];
    waktuAwal = json['waktuAwal'];
    resep = json['resep'];
    // tagihan = json['tagihan'];
    status = json['status'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['dokter'] = this.dokter;
    data['id'] = this.id;
    data['pasien'] = this.pasien;
    data['waktuAwal'] = this.waktuAwal;
    data['resep'] = this.resep;
    // data['tagihan'] = this.tagihan;
    data['status'] = this.status;
    return data;
  }
}
