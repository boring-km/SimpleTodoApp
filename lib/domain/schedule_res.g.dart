// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'schedule_res.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ScheduleRes _$ScheduleResFromJson(Map<String, dynamic> json) => ScheduleRes(
      json['id'] as String?,
      json['userId'] as String?,
      json['title'] as String?,
      json['doneYn'] as bool?,
      json['des'] as String?,
      json['regDt'] == null ? null : DateTime.parse(json['regDt'] as String),
    );

Map<String, dynamic> _$ScheduleResToJson(ScheduleRes instance) =>
    <String, dynamic>{
      'id': instance.id,
      'userId': instance.userId,
      'title': instance.title,
      'doneYn': instance.doneYn,
      'des': instance.des,
      'regDt': instance.regDt?.toIso8601String(),
    };
