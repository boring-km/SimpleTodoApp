import 'package:firebase_auth/firebase_auth.dart';
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
    getTodoList();
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
    item.doneYn = !item.doneYn!;
    var body = ScheduleReq.fromScheduleRes(item);
    var result = await _provider.updateSchedule(token, item.id!, body);
    todoList[index] = result;
    update();
  }

  changeTitle(int index, String title) async {
    var item = todoList[index];
    item.title = title;
    var body = ScheduleReq.fromScheduleRes(item);
    var result = await _provider.updateSchedule(token, item.id!, body);
    todoList[index] = result;
    update();
  }

  changeDetail(int index, String detail) async {
    var item = todoList[index];
    item.des = detail;
    var body = ScheduleReq.fromScheduleRes(item);
    var result = await _provider.updateSchedule(token, item.id!, body);
    todoList[index] = result;
    update();
  }

  deleteSchedule(int index) async {
    var id = todoList[index].id!;
    var schedule = await _provider.deleteSchedule(token, id);
    if (schedule.id == id) {
      todoList.removeAt(index);
      update();
    }
  }

  logout() async {
    await FirebaseAuth.instance.signOut();
  }

}

class MainBindings extends Bindings {
  @override
  void dependencies() {
    Get.put(MainController());
  }

}