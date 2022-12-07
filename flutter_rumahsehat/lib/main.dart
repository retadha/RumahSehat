
import 'package:flutter/material.dart';
import '/page/daftar_tagihan.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter_rumahsehat/auth/login.dart';
import 'package:flutter_rumahsehat/page/daftar_tagihan.dart';

void main() {
  runApp(MaterialApp(home: MyApp()));
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Rumah Sehat App',
      home: RumahSehatApp(),
    );
  }
}

class RumahSehatApp extends StatefulWidget {
  const RumahSehatApp({Key? key}) : super(key: key);

  @override
  _RumahSehatAppState createState() => _RumahSehatAppState();
}

class _RumahSehatAppState extends State<RumahSehatApp> {

  late SharedPreferences sharedPreferences;

  @override
  void initState(){
    super.initState();
    checkLoginStatus();
  }

  checkLoginStatus() async{
    sharedPreferences = await SharedPreferences.getInstance();
    if(sharedPreferences.getString("token") == null){
      Navigator.of(context).pushAndRemoveUntil(
          MaterialPageRoute(builder: (BuildContext context) => LoginPage()),
              (Route<dynamic> route) => false
      );
    }
  }

  @override
  int index = 0;
  final bodies = [Container(color: Colors.cyan), DaftarTagihanPage(), Container(color: Colors.blue), Container(color: Colors.yellow)];
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.white,
            actions: <Widget>[
              TextButton(
                style: TextButton.styleFrom(
                  foregroundColor: Colors.black,
                ),
                onPressed: () {
                  sharedPreferences.clear();
                  Navigator.of(context).pushAndRemoveUntil(
                      MaterialPageRoute(builder: (BuildContext context) => LoginPage()),
                          (Route<dynamic> route) => false
                  );
                },
                child: Text("Logout"),

              ),
            ],
          title: Text("Rumah Sehat",
            style: TextStyle(color: Colors.black)
          ),

        ),
        body: bodies[index],
        bottomNavigationBar: BottomNavigationBar(

          selectedItemColor: Colors.black,
          currentIndex: index,
          onTap: (val) {
            setState(() {
              index = val;
            });
          }, //(item) => onSelected(context, item),

          type: BottomNavigationBarType.fixed,
          items: <BottomNavigationBarItem>[
            const BottomNavigationBarItem(
              icon: Icon(Icons.person),
              label: 'Profil',
            ),
            const BottomNavigationBarItem(
              icon: Icon(Icons.monetization_on),
              label: 'Tagihan',
            ),
            const BottomNavigationBarItem(
              icon: Icon(Icons.timer),
              label: 'Appointment',
            ),
            const BottomNavigationBarItem(
              icon: Icon(Icons.medical_information),
              label: 'Resep',
            ),
          ],
        ),
      );
  }
}
