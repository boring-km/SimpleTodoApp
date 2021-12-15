import 'package:get/get.dart';
import 'package:logger/logger.dart';
import 'package:simpletodo/domain/schedule_req.dart';
import 'package:simpletodo/domain/schedule_res.dart';
import 'package:simpletodo/provider/main_provider.dart';

class MainController extends GetxController {

  late Logger logger;
  late String token;
  late String name;
  final MainProvider _provider = Get.put(MainProvider());

  List<ScheduleRes> todoList = [];


  @override
  void onInit() {
    logger = Logger();
    token = Get.parameters['token']!;
    name = Get.parameters['name']!;
    logger.i('token: $token, name: $name');
    super.onInit();
  }

  getTodoList() async {
    todoList = await _provider.getTodoList(token);
    update();
  }
  
  getTodoListWithTitle(String title) async {
    return await _provider.getScheduleWithTitle(token, title);
  }

  insertSchedule(String data) async {
    if (data.isEmpty) return;
    var schedule = ScheduleReq.onlyTitle(data);
    var result = await _provider.insertSchedule(token, schedule);
    todoList.add(result);
    update();
  }

  changeDoneYn(int index) async {
    var item = todoList[index];
    var body = ScheduleReq(item.userId, item.title, item.des, !item.doneYn!);
    var result = await _provider.updateSchedule(token, item.id!, body);
    todoList[index] = result;
    update();
  }

  updateSchedule(String id, ScheduleReq body) async {
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