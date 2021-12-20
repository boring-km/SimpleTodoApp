import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:simpletodo/controller/login_controller.dart';
import 'package:simpletodo/res/custom_colors.dart';
import 'package:simpletodo/view/splash_screen.dart';

class LoginPage extends GetView<LoginController> {
  const LoginPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: CustomColors.loginBgColor,
      body: GetBuilder<LoginController>(builder: (controller) {
        if (controller.isDialogVisible) {
          return const Center(
            child: SizedBox(
              width: 40, height: 40,
              child: CircularProgressIndicator(
                color: Colors.redAccent,
              ),
            ),
          );
        }
        return Stack(
          children: [
            Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  buildElevatedButton(() => signInWithGoogle(controller),
                    'images/icon_google.png',
                    'Google 로그인',
                    Colors.white,
                    Colors.black,
                  ),
                ],
              ),
            ),
            const SplashScreen(),
          ],
        );
      }),
    );
  }

  void signInWithGoogle(LoginController controller) async {
    controller.changeDialogState();

    // Trigger the authentication flow
    final GoogleSignInAccount? googleUser = await GoogleSignIn().signIn();

    // Obtain the auth details from the request
    final GoogleSignInAuthentication googleAuth = await googleUser!.authentication;

    // Create a new credential
    final googleAuthCredential = GoogleAuthProvider.credential(
      accessToken: googleAuth.accessToken,
      idToken: googleAuth.idToken,
    );

    // Once signed in, return the UserCredential
    final credential = await FirebaseAuth.instance.signInWithCredential(googleAuthCredential);
    final user = credential.user;
    if (user != null) {
      final token = await user.getIdToken(false);
      final name = user.displayName;
      Get.offAllNamed('/main?token=$token&name=$name');
    } else {
      controller.changeDialogState();
    }
  }

  ElevatedButton buildElevatedButton(Function() onPressed, String imgPath, String text, Color bgColor, Color textColor) {
    return ElevatedButton.icon(
      onPressed: onPressed,
      icon: Image.asset(
        imgPath,
        height: 30,
      ),
      label: Text(text),
      style: ElevatedButton.styleFrom(
        fixedSize: const Size(280, 40),
        primary: bgColor,
        onPrimary: textColor,
        textStyle: const TextStyle(
          fontSize: 16,
          fontWeight: FontWeight.bold,
        ),
      ),
    );
  }

}