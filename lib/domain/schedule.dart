import 'package:json_annotation/json_annotation.dart';

part 'schedule.g.dart';

@JsonSerializable()
class Schedule {
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


  Schedule(this.id, this.userId, this.title, this.doneYn, this.des, this.regDt);

  factory Schedule.fromJson(Map<String, dynamic> json) => _$ScheduleFromJson(json);

  Map<String, dynamic> toJson() => _$ScheduleToJson(this);

  @override
  String toString() {
    return 'Schedule{id: $id, userId: $userId, title: $title, doneYn: $doneYn, des: $des, regDt: $regDt}';
  }
}