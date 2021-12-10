import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:simpletodo/controller/main_controller.dart';

class MainPage extends GetView<MainController> {
  const MainPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    controller.logger.i('received token', controller.token);
    controller.logger.i('received name', controller.name);
    return Scaffold(
      body: Container(
        child: Center(
          child: Text("main page"),
        ),
      ),
    );
  }

}