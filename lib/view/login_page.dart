import 'package:flutter/material.dart';
import 'package:get/get.dart';

class LoginPage extends GetView {
  const LoginPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        child: Center(
          child: Column(
            children: [
              Text('로그인'),

            ],
          ),
        ),
      ),
    );
  }

}