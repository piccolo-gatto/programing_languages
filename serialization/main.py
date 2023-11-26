from enum import Enum
import struct
import json


class Alignment(Enum):
    HORIZONTAL = 1
    VERTICAL = 2


class Widget():
    def __init__(self, parent):
        self.parent = parent
        self.childrens = []
        if self.parent is not None:
            self.parent.add_children(self)

    def add_children(self, children: "Widget"):
        if children not in self.childrens:
            self.childrens.append(children)

    def to_json(self):
        classname = self.__class__.__name__
        result = {
            'classname': classname,
            'children': [child.to_json() for child in self.childrens]
        }
        if classname == 'Layout':
            result['Layout'] = self.alignment.name
        elif classname == 'LineEdit':
            result['LineEdit'] = self.max_length
        elif classname == 'ComboBox':
            result['ComboBox'] = self.items
        elif classname == 'MainWindow':
            result['MainWindow'] = self.title

        return result

    @classmethod
    def from_json(cls, data, parent=None):
        classname = data['classname']
        root = None
        if classname == 'MainWindow':
            root = cls(data['MainWindow'])
        elif classname == 'Layout':
            root = Layout(parent, Alignment[data['Layout']])
        elif classname == 'LineEdit':
            root = LineEdit(parent, data['LineEdit'])
        elif classname == 'ComboBox':
            root = ComboBox(parent, data['ComboBox'])
        for data_children in data['children']:
            child_cursor  = cls.from_json(data_children, parent=root)
            root.add_children(child_cursor)
        return root

    def __str__(self):
        return f"{self.__class__.__name__}{self.childrens}"

    def __repr__(self):
        return str(self)


class MainWindow(Widget):
    def __init__(self, title: str):
        super().__init__(None)
        self.title = title


class Layout(Widget):
    def __init__(self, parent, alignment: Alignment):
        super().__init__(parent)
        self.alignment = alignment


class LineEdit(Widget):
    def __init__(self, parent, max_length: int=10):
        super().__init__(parent)
        self.max_length = max_length


class ComboBox(Widget):
    def __init__(self, parent, items):
        super().__init__(parent)
        self.items = items


app = MainWindow("Application")
layout1 = Layout(app, Alignment.HORIZONTAL)
layout2 = Layout(app, Alignment.VERTICAL)

edit1 = LineEdit(layout1, 20)
edit2 = LineEdit(layout1, 30)

box1 = ComboBox(layout2, [1, 2, 3, 4])
box2 = ComboBox(layout2, ["a", "b", "c"])

print(app)

bts = app.to_json()
print(bts)

new_app = MainWindow.from_json(bts)
print(new_app)