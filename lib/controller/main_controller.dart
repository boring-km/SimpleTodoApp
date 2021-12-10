import 'package:get/get.dart';
import 'package:logger/logger.dart';

class MainController extends GetxController {

  late Logger logger;
  late String token;
  late String name;
  late bool isGuest;

  @override
  void onInit() {
    logger = Logger();
    token = Get.parameters['token']!;
    name = Get.parameters['name']!;
    isGuest = Get.parameters['guest']! == 'true';
    logger.i('token: $token, name: $name');
    super.onInit();
  }

}

class MainBindings extends Bindings {
  @override
  void dependencies() {
    Get.put(MainController());
  }

}