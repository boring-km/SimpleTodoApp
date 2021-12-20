import 'package:json_annotation/json_annotation.dart';

part 'schedule_res.g.dart';

@JsonSerializable()
class ScheduleRes {
  @JsonKey(name: 'id')
  String? id;

  @JsonKey(name: 'userId')
  String? userId;

  @JsonKey(name: 'title')
  String? title;

  @JsonKey(name: 'doneYn')
  bool? doneYn;

  @JsonKey(name: 'des')
  String? des;

  @JsonKey(name: 'regDt')
  DateTime? regDt;


  ScheduleRes(this.id, this.userId, this.title, this.doneYn, this.des, this.regDt);

  // factory ScheduleRes.fromJsonList(List<Map<String, dynamic>> jsonList) {
  //   List<ScheduleRes> result = [];
  //   for (var item in jsonList) {
  //     result.add(_$ScheduleResFromJson(item));
  //   }
  //   return result;
  // }

  factory ScheduleRes.fromJson(Map<String, dynamic> json) => _$ScheduleResFromJson(json);

  Map<String, dynamic> toJson() => _$ScheduleResToJson(this);

  factory ScheduleRes.nullObject() => ScheduleRes('', '', '', false, '', null);

  @override
  String toString() {
    return 'ScheduleRes{id: $id, userId: $userId, title: $title, doneYn: $doneYn, des: $des, regDt: $regDt}';
  }
}