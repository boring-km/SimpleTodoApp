import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:simpletodo/controller/main_controller.dart';

class MainPage extends GetView<MainController> {
  const MainPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    print('received token: ${controller.token}');
    return Scaffold(
      body: Container(
        child: Center(
          child: Text("main page"),
        ),
      ),
    );
  }

}