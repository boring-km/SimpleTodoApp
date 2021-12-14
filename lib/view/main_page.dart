import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:simpletodo/controller/main_controller.dart';
import 'package:simpletodo/domain/schedule_res.dart';

class MainPage extends GetView<MainController> {
  final _inputController = TextEditingController();

  MainPage({Key? key}): super(key: key) {
    controller.getTodoList();
  }

  @override
  Widget build(BuildContext context) {

    print('received token: ${controller.token}');
    double width = context.width;
    double height = context.height;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Simple Todo',
          style: TextStyle(
            fontSize: 30,
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
      body: SafeArea(
        child: GetBuilder<MainController>(builder: (controller) {
          var todoList = controller.todoList;
          return Stack(
            children: [
              Center(child: _buildTodoListView(todoList)),
              Align(
                alignment: Alignment.bottomCenter,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    SizedBox(
                      width: width * (7/9),
                      height: 50,
                      child: TextField(
                        controller: _inputController,
                        onSubmitted: (String text) async {
                          controller.insertSchedule(text);
                          _inputController.text = '';
                        },
                        decoration: const InputDecoration(
                          border: OutlineInputBorder(),
                          labelText: 'Todo 작성',
                        ),
                      ),
                    ),
                    const SizedBox(width: 10,),
                    SizedBox(
                      height: 50,
                      child: ElevatedButton(
                        onPressed: () async {
                          var text = _inputController.text;
                          controller.insertSchedule(text);
                          _inputController.text = '';
                        },
                        child: const Text('등록',
                          style: TextStyle(fontSize: 18,),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          );
        }),
      ),
    );
  }

  ListView _buildTodoListView(List<ScheduleRes> todoList) {
    return ListView.builder(
      itemCount: todoList.length,
      itemBuilder: (context, i) {
        return ListTile(
          title: Text(todoList[i].title!),
        );
      },
    );
  }

}