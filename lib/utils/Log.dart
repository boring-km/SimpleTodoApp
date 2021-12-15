import 'package:logger/logger.dart';

class Log {
  static v(dynamic message) {
    Logger(printer: PrettyPrinter(methodCount: 0)).v(message);
  }
  static d(dynamic message) {
    Logger(printer: PrettyPrinter(methodCount: 0)).d(message);
  }
  static i(dynamic message) {
    Logger(printer: PrettyPrinter(methodCount: 0)).i(message);
  }
  static e(dynamic message, [dynamic error, StackTrace? stackTrace]) {
    Logger(printer: PrettyPrinter(methodCount: 0)).e(message, error, stackTrace);
  }
  static w(dynamic message) {
    Logger(printer: PrettyPrinter(methodCount: 0)).w(message);
  }
}