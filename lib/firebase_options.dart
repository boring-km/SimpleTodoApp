// File generated by FlutterFire CLI.
// ignore_for_file: lines_longer_than_80_chars
import 'package:firebase_core/firebase_core.dart' show FirebaseOptions;
import 'package:flutter/foundation.dart'
    show defaultTargetPlatform, kIsWeb, TargetPlatform;

/// Default [FirebaseOptions] for use with your Firebase apps.
///
/// Example:
/// ```dart
/// import 'firebase_options.dart';
/// // ...
/// await Firebase.initializeApp(
///   options: DefaultFirebaseOptions.currentPlatform,
/// );
/// ```
class DefaultFirebaseOptions {
  static FirebaseOptions get currentPlatform {
    if (kIsWeb) {
      return web;
    }
    // ignore: missing_enum_constant_in_switch
    switch (defaultTargetPlatform) {
      case TargetPlatform.android:
        return android;
      case TargetPlatform.iOS:
        return ios;
      case TargetPlatform.macOS:
        throw UnsupportedError(
          'DefaultFirebaseOptions have not been configured for macos - '
          'you can reconfigure this by running the FlutterFire CLI again.',
        );
    }

    throw UnsupportedError(
      'DefaultFirebaseOptions are not supported for this platform.',
    );
  }

  static const FirebaseOptions web = FirebaseOptions(
    apiKey: 'AIzaSyAshgp6-NL3-_EmPT3mvjOobZJivdgwEco',
    appId: '1:449723613673:web:26b39d738b3e483701cb78',
    messagingSenderId: '449723613673',
    projectId: 'simpletodo-73fdf',
    authDomain: 'simpletodo-73fdf.firebaseapp.com',
    storageBucket: 'simpletodo-73fdf.appspot.com',
  );

  static const FirebaseOptions android = FirebaseOptions(
    apiKey: 'AIzaSyDkmp3NsDE2u_U0y8WWJ2ikzY5IikdEZ5A',
    appId: '1:449723613673:android:a1141d3f5f91492701cb78',
    messagingSenderId: '449723613673',
    projectId: 'simpletodo-73fdf',
    storageBucket: 'simpletodo-73fdf.appspot.com',
  );

  static const FirebaseOptions ios = FirebaseOptions(
    apiKey: 'AIzaSyBrTElHgaaJUpgyqOeCa0VksxBFUZoEMk4',
    appId: '1:449723613673:ios:74237bbdfd58d7a701cb78',
    messagingSenderId: '449723613673',
    projectId: 'simpletodo-73fdf',
    storageBucket: 'simpletodo-73fdf.appspot.com',
    androidClientId: '449723613673-qgbai9qs64o66i2182lvom414hvtkmsj.apps.googleusercontent.com',
    iosClientId: '449723613673-1oo5l12h7nlkqfl5pb4ml68nlf960fif.apps.googleusercontent.com',
    iosBundleId: 'com.boringkm.simpletodo.simpletodo',
  );
}
