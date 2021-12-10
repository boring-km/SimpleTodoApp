import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:simpletodo/controller/login_controller.dart';
import 'package:simpletodo/controller/main_controller.dart';
import 'package:simpletodo/view/login_page.dart';
import 'package:simpletodo/view/main_page.dart';
// import 'firebase_options.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    // options: DefaultFirebaseOptions.currentPlatform,
  );
  runApp(const SimpleTodoApp());
}

class SimpleTodoApp extends StatelessWidget {
  const SimpleTodoApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      title: 'SimpleTodo',
      theme: ThemeData(
        fontFamily: 'NanumSquareRound',
        backgroundColor: Colors.black,
      ),
      initialRoute: '/',
      getPages: [
        GetPage(name: '/', page: () => const LoginPage(), binding: LoginBindings()),
        GetPage(name: '/main', page: () => const MainPage(), binding: MainBindings()),
      ],
    );
  }
}
