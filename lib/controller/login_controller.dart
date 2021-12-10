import 'package:get/get.dart';

class LoginController extends GetxController {

  bool isDialogVisible = false;

  void changeDialogState() {
    isDialogVisible = !isDialogVisible;
    update();
  }

}

class LoginBindings extends Bindings {
  @override
  void dependencies() {
    Get.put(LoginController());
  }

}