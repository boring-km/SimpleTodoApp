import 'package:flutter_test/flutter_test.dart';
import 'package:logger/logger.dart';
import 'package:simpletodo/provider/main_provider.dart';

void main() {
  MainProvider provider = MainProvider();
  Logger logger = Logger();

  test('todo 저장된 리스트 불러오기', () async {
    var result = await provider.callData("token");  // TODO ui test에서 token을 받아와 테스트 할 수 있도록...
    for (var item in result) {
      logger.i('data: $item');
    }
  });
}