import 'package:get/get.dart';
import 'package:simpletodo/provider/util/net_utils.dart';

class LoginProvider extends GetConnect {

  late NetUtils utils;

  LoginProvider() {
    utils = NetUtils();
  }

  Future<bool> login(String token) async {
    var apiUrl = '${utils.url}/user';
    var header = utils.withToken(token);
    var bodyData = await utils.getBodyData(apiUrl, header);
    return bodyData == 'SUCCESS';
  }
}