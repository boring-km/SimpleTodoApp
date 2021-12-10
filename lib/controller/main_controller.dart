import 'package:get/get.dart';

class MainController extends GetxController {

}

class MainBindings extends Bindings {
  @override
  void dependencies() {
    Get.put(MainController());
  }

}