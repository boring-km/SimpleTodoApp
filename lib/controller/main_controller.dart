import 'package:get/get.dart';
import 'package:logger/logger.dart';
import 'package:simpletodo/domain/schedule_res.dart';
import 'package:simpletodo/provider/main_provider.dart';

class MainController extends GetxController {

  late Logger logger;
  late String token;
  late String name;
  final MainProvider _provider = Get.put(MainProvider());

  @override
  void onInit() {
    logger = Logger();
    token = Get.parameters['token']!;
    name = Get.parameters['name']!;
    logger.i('token: $token, name: $name');
    super.onInit();
  }

  getTodoList() async {
    return await _provider.getTodoList(token);
  }
  
  getTodoListWithTitle(String title) async {
    return await _provider.getScheduleWithTitle(token, title);
  }

  insertSchedule(Map<String, dynamic> body) async {
    return await _provider.insertSchedule(token, body);
  }

  updateSchedule(String id, Map<String, dynamic> body) async {
    return await _provider.updateSchedule(token, id, body);
  }

  deleteSchedule(String id) async {
    return await _provider.deleteSchedule(token, id);
  }

}

class MainBindings extends Bindings {
  @override
  void dependencies() {
    Get.put(MainController());
  }

}