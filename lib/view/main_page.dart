import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:simpletodo/controller/main_controller.dart';
import 'package:simpletodo/domain/schedule_res.dart';
import 'package:simpletodo/utils/Log.dart';
import 'package:simpletodo/utils/menu_type.dart';

class MainPage extends GetView<MainController> {
  final _inputController = TextEditingController();

  final _scrollController = ScrollController();

  final _popupTextController = TextEditingController();

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
              onLongPress: () {

              },
              child: Row(
                mainAxisSize: MainAxisSize.max,
                children: [
                  _buildCheckImage(todoList[i].doneYn),
                  widthMargin(8),
                  Expanded(
                    child: Text(todoList[i].title!,
                      style: const TextStyle(
                        fontSize: 24,
                      ),
                    ),
                  ),
                  widthMargin(8),
                  PopupMenuButton(
                    child: Image.asset('images/ic_more_vert.png'),
                    offset: Offset(0, 24),
                    itemBuilder: (context) {
                      return [
                        const PopupMenuItem(
                          child: Text('수정하기'),
                          value: MenuType.update,
                        ),
                        const PopupMenuItem(
                          child: Text('상세내용'),
                          value: MenuType.detail,
                        ),
                        const PopupMenuItem(
                          child: Text('삭제하기'),
                          value: MenuType.delete,
                        ),
                      ];
                    },
                    onSelected: (menu) {
                      switch(menu) {
                        case MenuType.update:
                          showUpdateDialog(context, todoList[i].title ?? '');
                          break;
                        case MenuType.detail:
                          showDetailDialog(context, todoList[i]);
                          break;
                        case MenuType.delete:
                          showDeleteDialog(context);
                          break;
                      }
                    },
                  ),
                ],
              ),
            ),
          );
        },
      ),
    );
  }

  void showDeleteDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext dialogContext) {
        return AlertDialog(
          title: Text('삭제하기'),
          content: Text('정말로 삭제하시겠습니까?'),
          actions: <Widget>[
            TextButton(
              child: Text('취소',
                style: TextStyle(color: Colors.grey,),
              ),
              onPressed: () {
                Navigator.of(dialogContext).pop(); // Dismiss alert dialog
              },
            ),
            ElevatedButton(
              onPressed: () {},
              style: ElevatedButton.styleFrom(
                primary: Colors.red,
              ),
              child: Text('삭제'),
            ),
          ],
        );
      },
    );
  }

  void showDetailDialog(BuildContext context, ScheduleRes item) {
    String title = item.title ?? '';
    String des = item.des ?? '';
    _popupTextController.text = des;
    showDialog(
      context: context,
      builder: (BuildContext dialogContext) {
        return AlertDialog(
          title: Text(title),
          content: TextField(
            controller: _popupTextController,
            decoration: InputDecoration(
              floatingLabelBehavior: FloatingLabelBehavior.never,
              border: const UnderlineInputBorder(),
              suffixIcon: IconButton(
                onPressed: _popupTextController.clear,
                icon: const Icon(CupertinoIcons.clear_thick_circled),
              ),
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: Text('취소',
                style: TextStyle(color: Colors.grey,),
              ),
              onPressed: () {
                Navigator.of(dialogContext).pop(); // Dismiss alert dialog
              },
            ),
            ElevatedButton(
              onPressed: () {},
              child: Text('수정'),
            ),
          ],
        );
      },
    );
  }

  void showUpdateDialog(BuildContext context, String before) {
    _popupTextController.text = before;
    showDialog(
      context: context,
      builder: (BuildContext dialogContext) {
        return AlertDialog(
          title: Text('수정하기'),
          content: TextField(
            controller: _popupTextController,
            decoration: InputDecoration(
              floatingLabelBehavior: FloatingLabelBehavior.never,
              border: const UnderlineInputBorder(),
              suffixIcon: IconButton(
                onPressed: _popupTextController.clear,
                icon: const Icon(CupertinoIcons.clear_thick_circled),
              ),
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: Text('취소',
                style: TextStyle(color: Colors.grey,),
              ),
              onPressed: () {
                Navigator.of(dialogContext).pop(); // Dismiss alert dialog
              },
            ),
            ElevatedButton(
              onPressed: () {},
              child: Text('수정'),
            ),
          ],
        );
      },
    );
  }

  SizedBox widthMargin(double width) => SizedBox(width: width,);

  Image _buildCheckImage(bool? doneYn) {
    if (doneYn == null || !doneYn) {
      return Image.asset('images/ic_radio_button_unchecked.png');
    } else {
      return Image.asset('images/ic_check_circle.png');
    }
  }

}