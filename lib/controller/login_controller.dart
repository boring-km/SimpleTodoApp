import 'package:get/get.dart';

class LoginController extends GetxController {

}

class LoginBindings extends Bindings {
  @override
  void dependencies() {
    Get.put(LoginController());
  }

}