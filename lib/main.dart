import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:get/get.dart';
import 'package:simpletodo/controller/login_controller.dart';
import 'package:simpletodo/controller/main_controller.dart';
import 'package:simpletodo/firebase_options.dart';
import 'package:simpletodo/view/login_page.dart';
import 'package:simpletodo/view/main_page.dart';

Future<void> main() async {
  await dotenv.load(fileName: 'dev.env');
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  runApp(const SimpleTodoApp());
}

class SimpleTodoApp extends StatelessWidget {
  const SimpleTodoApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      title: 'SimpleTodo',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        fontFamily: 'NanumSquareRound',
      ),
      initialRoute: '/',
      getPages: [
        GetPage(name: '/', page: () => const LoginPage(), binding: LoginBindings()),
        GetPage(name: '/main', page: () => MainPage(), binding: MainBindings()),
      ],
    );
  }
}
