class PasienModel {
  String? uuid;
  String? nama;
  String? role;
  String? username;
  String? email;
  String? password;
  int? saldo;
  int? umur;

  PasienModel(
      {this.uuid,
        this.nama,
        this.role,
        this.username,
        this.email,
        this.password,
        this.saldo,
        this.umur});

  PasienModel.fromJson(Map<String, dynamic> json) {
    uuid = json['uuid'];
    nama = json['nama'];
    role = json['role'];
    username = json['username'];
    email = json['email'];
    password = json['password'];
    saldo = json['saldo'];
    umur = json['umur'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['uuid'] = this.uuid;
    data['nama'] = this.nama;
    data['role'] = this.role;
    data['username'] = this.username;
    data['email'] = this.email;
    data['password'] = this.password;
    data['saldo'] = this.saldo;
    data['umur'] = this.umur;
    return data;
  }
}