
import 'package:flutter/material.dart';
<<<<<<< HEAD
import 'package:flutter_rumahsehat/page/detail_resep.dart';
=======
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter_rumahsehat/auth/login.dart';

>>>>>>> 9551ecbc9733d44faebc695a3c7a46c7373d54a5

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
<<<<<<< HEAD
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home page'),
=======
      title: 'Rumah Sehat App',
      home: RumahSehatApp(),
>>>>>>> 9551ecbc9733d44faebc695a3c7a46c7373d54a5
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
  List<Widget> bodies = [Container(color: Colors.cyan), Container(color: Colors.red), Container(color: Colors.blue), Container(color: Colors.yellow)];
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
