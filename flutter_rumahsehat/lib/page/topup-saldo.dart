import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class TopUpSaldoPage extends StatefulWidget {
  @override
  _TopUpSaldoPage createState() => _TopUpSaldoPage();
}

class _TopUpSaldoPage extends State<TopUpSaldoPage> {
  final _formKey = GlobalKey<FormState>();
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Top Up Saldo"),
      ),
      body: Form(
        key: _formKey,
        child: Container(
          padding: EdgeInsets.all(20.0),
          child: Column(
            children: [
              // TextField(),
              TextFormField(
                inputFormatters: [FilteringTextInputFormatter.digitsOnly],
                decoration: new InputDecoration(
                  hintText: "Rp0",
                  labelText: "Jumlah Uang",
                  icon: Icon(Icons.money_outlined),
                  border: OutlineInputBorder(
                      borderRadius: new BorderRadius.circular(5.0)),
                ),
                validator: (value) {
                  if (value!.isEmpty) {
                    return 'Jumlah uang tidak boleh kosong';
                  }
                  return null;
                },
              ),
              ElevatedButton(
                child: Text(
                  "Submit",
                  style: TextStyle(color: Colors.white),
                ),
                // color: Colors.blue,
                onPressed: () {
                  if (_formKey.currentState!.validate()) {}
                },
              ),
            ],
          ),
        ),
      ),
    );
  }
}