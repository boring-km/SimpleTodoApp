// import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:logger/logger.dart';
import 'package:simpletodo/provider/login_provider.dart';
import 'package:simpletodo/provider/main_provider.dart';

void main() async {
  /*
    network 테스트 시에 사용 불가
    TestWidgetsFlutterBinding.ensureInitialized();
  await dotenv.load(fileName: 'dev.env');
   */
  Logger logger = Logger();
  MainProvider mainProvider = MainProvider();
  LoginProvider loginProvider = LoginProvider();
  // TODO ui test에서 token을 받아와 테스트 할 수 있도록...
  var token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjQ3OTg5ZTU4ZWU1ODM4OTgzZDhhNDQwNWRlOTVkYTllZTZmNWVlYjgiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoi7KeE6rCV66-8IiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hLS9BT2gxNEdpVm9ybm4xdGRFaFp0QnRxdFpJNk5oZXRheEFweWJNcF9wUHhWRD1zOTYtYyIsImlzcyI6Imh0dHBzOi8vc2VjdXJldG9rZW4uZ29vZ2xlLmNvbS9zaW1wbGV0b2RvLTczZmRmIiwiYXVkIjoic2ltcGxldG9kby03M2ZkZiIsImF1dGhfdGltZSI6MTYzOTMxNTk4MywidXNlcl9pZCI6ImlKQVFpWmUwWGZVVldJVmhISG40cE9kSjI5dDEiLCJzdWIiOiJpSkFRaVplMFhmVVZXSVZoSEhuNHBPZEoyOXQxIiwiaWF0IjoxNjM5MzE1OTgzLCJleHAiOjE2MzkzMTk1ODMsImVtYWlsIjoidHM0ODQwNjQ0ODA0QGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7Imdvb2dsZS5jb20iOlsiMTE1NTQzNjcwNTI2MTAwNjIwODAyIl0sImVtYWlsIjpbInRzNDg0MDY0NDgwNEBnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJnb29nbGUuY29tIn19.G4O6mkNecNcZReD0pkKC5BV0_wTsFder_6z2-2A00Y94WZw10GIPoSbntg8gec1Egg2Eo-8QQGX93_ptEfEEOrQqAIiddu1YgfRyVMhi5mggXaKEtaI0PMcmI5noiDSkFJgGiNREXhciqeibOmWck46LQyBvHFebdrUDnW7ozB1JyE55js7CANVLHcT89KrCESARK0FKsVEx502DziaRK03w4aSZpbyw-YncmtEjrUn-nUHl4uOVDR_MoMy7HaSd0GE1GxLMG63mY8FkvmIbXoX4WbwFjGziQzuVsakyd0RBLgeqwVLYgH7HKaoCnxnDRovvv8oqDdOhrYCANWQ1Pg";

  test('저장된 todo 리스트를 조회하면 비어있지 않다.', () async {
    var result = await mainProvider.getTodoList(token);
    for (var item in result) {
      logger.i('data: $item');
    }
    expect(result.isNotEmpty, true);
  });

  test('저장된 todo 항목 중 title이 "gfsthdd"인 리스트 불러오기', () async {
    var title = "gfsthdd";
    var result = await mainProvider.getScheduleWithTitle(token, title);
    for (var item in result) {
      logger.i('data: $item');
    }
  });

  test('login 테스트', () async {
    var result = await loginProvider.login("eyJhbGciOiJSUzI1NiIsImtpZCI6IjQ3OTg5ZTU4ZWU1ODM4OTgzZDhhNDQwNWRlOTVkYTllZTZmNWVlYjgiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoi7KeE6rCV66-8IiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hLS9BT2gxNEdpVm9ybm4xdGRFaFp0QnRxdFpJNk5oZXRheEFweWJNcF9wUHhWRD1zOTYtYyIsImlzcyI6Imh0dHBzOi8vc2VjdXJldG9rZW4uZ29vZ2xlLmNvbS9zaW1wbGV0b2RvLTczZmRmIiwiYXVkIjoic2ltcGxldG9kby03M2ZkZiIsImF1dGhfdGltZSI6MTYzOTMxNTk4MywidXNlcl9pZCI6ImlKQVFpWmUwWGZVVldJVmhISG40cE9kSjI5dDEiLCJzdWIiOiJpSkFRaVplMFhmVVZXSVZoSEhuNHBPZEoyOXQxIiwiaWF0IjoxNjM5MzE1OTgzLCJleHAiOjE2MzkzMTk1ODMsImVtYWlsIjoidHM0ODQwNjQ0ODA0QGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7Imdvb2dsZS5jb20iOlsiMTE1NTQzNjcwNTI2MTAwNjIwODAyIl0sImVtYWlsIjpbInRzNDg0MDY0NDgwNEBnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJnb29nbGUuY29tIn19.G4O6mkNecNcZReD0pkKC5BV0_wTsFder_6z2-2A00Y94WZw10GIPoSbntg8gec1Egg2Eo-8QQGX93_ptEfEEOrQqAIiddu1YgfRyVMhi5mggXaKEtaI0PMcmI5noiDSkFJgGiNREXhciqeibOmWck46LQyBvHFebdrUDnW7ozB1JyE55js7CANVLHcT89KrCESARK0FKsVEx502DziaRK03w4aSZpbyw-YncmtEjrUn-nUHl4uOVDR_MoMy7HaSd0GE1GxLMG63mY8FkvmIbXoX4WbwFjGziQzuVsakyd0RBLgeqwVLYgH7HKaoCnxnDRovvv8oqDdOhrYCANWQ1Pg");
    expect(true, result);
  });
}