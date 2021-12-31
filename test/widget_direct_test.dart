import 'package:flutter_test/flutter_test.dart';

void main() {
  // flutter run test/widget_direct_test.dart
  test('통과하는 테스트', () {
    expect(1+1, 2);
  });

  test('통과하지 않는 테스트', () {
    expect("1+1", 2);
  });
}
