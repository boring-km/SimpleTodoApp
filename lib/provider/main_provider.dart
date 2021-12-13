import 'package:get/get.dart';
import 'package:simpletodo/domain/schedule_res.dart';
import 'package:simpletodo/provider/util/net_utils.dart';

class MainProvider extends GetConnect {

  late NetUtils utils;

  MainProvider() {
    utils = NetUtils();
  }

  Future<List<ScheduleRes>> getTodoList(String token) async {
    final apiUrl = '${utils.url}/schedule';
    final header = utils.withToken(token);
    final bodyData = await utils.getBodyData(apiUrl, header);
    List<ScheduleRes> result = [];
    for (var item in bodyData) {
      result.add(ScheduleRes.fromJson(item));
    }
    return result;
  }

  Future<List<ScheduleRes>> getScheduleWithTitle(String token, String title) async {
    final apiUrl = '${utils.url}/schedule/$title';
    final header = utils.withToken(token);
    final bodyData = await utils.getBodyData(apiUrl, header);
    List<ScheduleRes> result = [];
    for (var item in bodyData) {
      result.add(ScheduleRes.fromJson(item));
    }
    return result;
  }

  Future<ScheduleRes> insertSchedule(String token, Map<String, dynamic> body) async {
    final apiUrl = '${utils.url}/schedule';
    final header = utils.withToken(token);
    final bodyData = await utils.postData(apiUrl, header, body);
    return ScheduleRes.fromJson(bodyData);
  }

  Future<ScheduleRes> updateSchedule(String token, String id, Map<String, dynamic> body) async {
    final apiUrl = '${utils.url}/schedule/$id';
    final header = utils.withToken(token);
    final bodyData = await utils.updateData(apiUrl, header, body);
    return ScheduleRes.fromJson(bodyData);
  }

  Future<ScheduleRes> deleteSchedule(String token, String id) async {
    final apiUrl = '${utils.url}/schedule/$id';
    final header = utils.withToken(token);
    final bodyData = await utils.deleteData(apiUrl, header);
    return ScheduleRes.fromJson(bodyData);
  }
}