import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:simpletodo/controller/main_controller.dart';
import 'package:simpletodo/domain/schedule_res.dart';
import 'package:simpletodo/utils/menu_type.dart';

class MainPage extends GetView<MainController> {
  final _inputController = TextEditingController();

  final _scrollController = ScrollController();

  final _popupTextController = TextEditingController();

  MainPage({Key? key}): super(key: key);

  @override
  Widget build(BuildContext context) {

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
        actions: [
          GestureDetector(
            onTap: () async {
              await controller.logout();
              Get.offAndToNamed('/');
            },
            child: const Padding(
              padding: EdgeInsets.only(right: 16.0),
              child: Icon(
                Icons.logout,
              ),
            ),
          ),
        ],
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
                  child: Padding(
                    padding: const EdgeInsets.all(4.0),
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
                showUpdateDialog(context, i);
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
                    offset: const Offset(0, 24),
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
                          showUpdateDialog(context, i);
                          break;
                        case MenuType.detail:
                          showDetailDialog(context, i);
                          break;
                        case MenuType.delete:
                          showDeleteDialog(context, i);
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

  void showDeleteDialog(BuildContext context, int index) {
    showDialog(
      context: context,
      builder: (BuildContext dialogContext) {
        return AlertDialog(
          title: const Text('삭제하기'),
          content: const Text('정말로 삭제하시겠습니까?'),
          actions: <Widget>[
            TextButton(
              child: const Text('취소',
                style: TextStyle(color: Colors.grey,),
              ),
              onPressed: () {
                Navigator.of(dialogContext).pop();
              },
            ),
            ElevatedButton(
              onPressed: () {
                controller.deleteSchedule(index);
                Navigator.of(dialogContext).pop();
              },
              style: ElevatedButton.styleFrom(
                primary: Colors.red,
              ),
              child: const Text('삭제'),
            ),
          ],
        );
      },
    );
  }

  void showDetailDialog(BuildContext context, int index) {
    var item = controller.todoList[index];
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
              child: const Text('취소',
                style: TextStyle(color: Colors.grey,),
              ),
              onPressed: () {
                Navigator.of(dialogContext).pop();
              },
            ),
            ElevatedButton(
              onPressed: () async {
                await controller.changeDetail(index, _popupTextController.text);
                Navigator.of(dialogContext).pop();
              },
              child: const Text('수정'),
            ),
          ],
        );
      },
    );
  }

  void showUpdateDialog(BuildContext context, int index) {
    _popupTextController.text = controller.todoList[index].title ?? '';
    showDialog(
      context: context,
      builder: (BuildContext dialogContext) {
        return AlertDialog(
          title: const Text('수정하기'),
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
              child: const Text('취소',
                style: TextStyle(color: Colors.grey,),
              ),
              onPressed: () {
                Navigator.of(dialogContext).pop();
              },
            ),
            ElevatedButton(
              onPressed: () async {
                await controller.changeTitle(index, _popupTextController.text);
                Navigator.of(dialogContext).pop();
              },
              child: const Text('수정'),
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