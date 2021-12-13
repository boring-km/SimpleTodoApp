import 'dart:collection';
import 'dart:convert';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:get/get.dart';

class NetUtils extends GetConnect {

  late String url;

  NetUtils() {
    // url = dotenv.env['API_URL']!;
    url = 'http://192.168.35.2:8080/api';
  }

  Future<dynamic> getBodyData(String apiUrl, Map<String, String> header) async {
    final response = await get(apiUrl, headers: header,);
    final body = response.bodyString;
    if (body == null || body.isEmpty) {
      throw Error();
    }
    try {
      return json.decode(body);
    } catch (e) {
      return body;
    }
  }

  Future<dynamic> postData(String apiUrl, Map<String, String> header, dynamic body) async {
    final response = await post(apiUrl, body, headers: header);
    final result = response.bodyString;
    if (result == null || result.isEmpty) {
      throw Error();
    }
    try {
      return json.decode(result);
    } catch (e) {
      return result;
    }
  }

  Future<dynamic> updateData(String apiUrl, Map<String, String> header, dynamic body) async {
    final response = await put(apiUrl, body, headers: header);
    final result = response.bodyString;
    if (result == null || result.isEmpty) {
      throw Error();
    }
    try {
      return json.decode(result);
    } catch (e) {
      return result;
    }
  }

  Future<dynamic> deleteData(String apiUrl, Map<String, String> header) async {
    final response = await delete(apiUrl, headers: header);
    final result = response.bodyString;
    if (result == null || result.isEmpty) {
      throw Error();
    }
    try {
      return json.decode(result);
    } catch (e) {
      return result;
    }
  }



  Map<String, String> withToken(String token) {
    Map<String, String> header = HashMap();
    header['Authorization'] = 'Bearer $token';
    return header;
  }
}