// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'schedule_req.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ScheduleReq _$ScheduleReqFromJson(Map<String, dynamic> json) => ScheduleReq(
      json['userId'] as String?,
      json['title'] as String?,
      json['des'] as String?,
      json['doneYn'] as bool?,
    );

Map<String, dynamic> _$ScheduleReqToJson(ScheduleReq instance) =>
    <String, dynamic>{
      'userId': instance.userId,
      'title': instance.title,
      'des': instance.des,
      'doneYn': instance.doneYn,
    };
