import 'dart:collection';
import 'dart:convert';

import 'package:get/get.dart';
import 'package:simpletodo/domain/schedule_res.dart';

class MainProvider extends GetConnect {

  final url = 'http://192.168.200.166:8080/api';

  Future<List<ScheduleRes>> callData(String token) async {
    var header = withToken(token);
    final response = await get('$url/schedule', headers: header,);
    final String? body = response.bodyString;
    if (body == null || body.isEmpty) {
      return [ScheduleRes.nullObject()];
    }
    var dataList = json.decode(body);
    List<ScheduleRes> result = [];
    for (var item in dataList) {
      result.add(ScheduleRes.fromJson(item));
    }
    return result;
  }

  Map<String, String> withToken(String token) {
    Map<String, String> header = HashMap();
    header['Authorization'] = 'Bearer $token';
    return header;
  }
}