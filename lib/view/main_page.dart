import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:simpletodo/controller/main_controller.dart';
import 'package:simpletodo/domain/schedule_res.dart';
import 'package:simpletodo/utils/Log.dart';

class MainPage extends GetView<MainController> {
  final _inputController = TextEditingController();

  final _scrollController = ScrollController();

  MainPage({Key? key}): super(key: key) {
    controller.getTodoList();
  }

  @override
  Widget build(BuildContext context) {

    Log.d('received token: ${controller.token}');
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
          return SizedBox(
            height: height * 7/8,
            child: Column(
              children: [
                _buildTodoListView(todoList),
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
                            insertTodo(controller, text, context);
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
                            insertTodo(controller, text, context);
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
            ),
          );
        }),
      ),
    );
  }

  void moveToEndScroll() {
    _scrollController.jumpTo(_scrollController.position.maxScrollExtent);
  }

  void insertTodo(MainController controller, String text, BuildContext context) {
    controller.insertSchedule(text);
    FocusScope.of(context).unfocus();
    _inputController.text = '';
    moveToEndScroll();
  }

  Expanded _buildTodoListView(List<ScheduleRes> todoList) {
    return Expanded(
      child: ListView.builder(
        itemCount: todoList.length,
        controller: _scrollController,
        itemBuilder: (context, i) {
          return ListTile(
            title: GestureDetector(
              onTap: () {
                controller.changeDoneYn(i);
                FocusScope.of(context).unfocus();
              },
              child: Row(
                mainAxisSize: MainAxisSize.max,
                children: [
                  _buildCheckImage(todoList[i].doneYn),
                  const SizedBox(width: 8,),
                  Expanded(
                    child: Text(todoList[i].title!,
                      style: const TextStyle(
                        fontSize: 24,
                      ),
                    ),
                  ),
                ],
              ),
            ),
          );
        },
      ),
    );
  }

  Image _buildCheckImage(bool? doneYn) {
    if (doneYn == null || !doneYn) {
      return Image.asset('images/ic_radio_button_unchecked.png');
    } else {
      return Image.asset('images/ic_check_circle.png');
    }
  }

}