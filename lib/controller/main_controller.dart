import 'package:get/get.dart';
import 'package:logger/logger.dart';
import 'package:simpletodo/domain/schedule_res.dart';
import 'package:simpletodo/provider/main_provider.dart';

class MainController extends GetxController {

  late Logger logger;
  late String token;
  late String name;
  late bool isGuest;
  MainProvider provider = Get.put(MainProvider());

  @override
  void onInit() {
    logger = Logger();
    token = Get.parameters['token']!;
    name = Get.parameters['name']!;
    isGuest = Get.parameters['guest']! == 'true';
    logger.i('token: $token, name: $name');
    getTodoList();
    super.onInit();
  }

  getTodoList() async {
    return await provider.callData(token);
  }

}

class MainBindings extends Bindings {
  @override
  void dependencies() {
    Get.put(MainController());
  }

}