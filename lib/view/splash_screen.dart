import 'package:flutter/material.dart';
class SplashScreen extends StatefulWidget {
  const SplashScreen({Key? key}) : super(key: key);

  @override
  _SplashScreenState createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {

  bool isDelayed = false;

  @override
  void initState() {

    Future.delayed(const Duration(seconds: 2), () {
      setState(() {
        isDelayed = true;
      });
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    if (isDelayed) {
      return const SizedBox.shrink();
    }
    return Container(
      color: Colors.white,
      child: const Center(
        child: Text(
          'SimpleTodo',
          style: TextStyle(
            fontSize: 32,
            fontWeight: FontWeight.w900,
          ),
        ),
      ),
    );
  }

}