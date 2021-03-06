import 'package:json_annotation/json_annotation.dart';
import 'package:simpletodo/domain/schedule_res.dart';

part 'schedule_req.g.dart';

@JsonSerializable()
class ScheduleReq {
  @JsonKey(name: 'userId')
  String? userId;

  @JsonKey(name: 'title')
  String? title;

  @JsonKey(name: 'des')
  String? des;


  @JsonKey(name: 'doneYn')
  bool? doneYn;

  ScheduleReq(this.userId, this.title, this.des, this.doneYn);

  factory ScheduleReq.fromScheduleRes(ScheduleRes scheduleRes) => ScheduleReq(scheduleRes.userId, scheduleRes.title, scheduleRes.des, scheduleRes.doneYn);

  factory ScheduleReq.onlyTitle(String data) => ScheduleReq(null, data, '', false);

  factory ScheduleReq.fromJson(Map<String, dynamic> json) => _$ScheduleReqFromJson(json);

  Map<String, dynamic> toJson() => _$ScheduleReqToJson(this);

  @override
  String toString() {
    return 'ScheduleReq{userId: $userId, title: $title, des: $des, doneYn: $doneYn}';
  }
}