
import 'package:flutter/material.dart';




void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  @override
  int index = 0;
  List<Widget> bodies = [Container(color: Colors.cyan), Container(color: Colors.red), Container(color: Colors.blue), Container(color: Colors.yellow)];
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Rumah Sehat',
      debugShowCheckedModeBanner: false,
      //theme: theme,
      // home : sesuaikan dengan halaman masing2 buat dicoba
      home: Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.white,
            actions: <Widget>[
              TextButton(
                style: TextButton.styleFrom(
                  foregroundColor: Colors.black,
                ),
                onPressed: () {},
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
      ),
    );
  }
}
