import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  runApp(MaterialApp(
    home: TestApp(),
  ));
}

class TestApp extends StatelessWidget {
  TestApp({Key? key}) : super(key: key) {
    test('통과하는 테스트', () {
      expect(1+1, 2);
    });

    test('통과하지 않는 테스트', () {
      expect("1+1", 2);
    });
  }


  @override
  Widget build(BuildContext context) {
    return Container();
  }

}