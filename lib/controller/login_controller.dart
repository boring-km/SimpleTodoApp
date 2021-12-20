import 'package:firebase_auth/firebase_auth.dart';
import 'package:get/get.dart';
import 'package:logger/logger.dart';
import 'package:simpletodo/provider/login_provider.dart';

class LoginController extends GetxController {

  bool isDialogVisible = false;
  late Logger logger;
  LoginProvider provider = Get.put(LoginProvider());

  @override
  void onInit() {
    logger = Logger();
    autoLogin();
    super.onInit();
  }

  void autoLogin() async {
    var user = FirebaseAuth.instance.currentUser;
    if (user != null) {
      final token = await user.getIdToken();
      final name = user.displayName;
      Get.offAllNamed('/main?token=$token&name=$name');
    }
  }

  void changeDialogState() {
    isDialogVisible = !isDialogVisible;
    update();
  }

  Future<bool> login(String token) async {
    return await provider.login(token);
  }

}

class LoginBindings extends Bindings {
  @override
  void dependencies() {
    Get.put(LoginController());
  }

}