// IMyAidl.aidl
package xwh.demo.aidl;

import xwh.demo.aidl.Person;    // 用到的对象要导入，不然生成java文件的时候回导入错误。

// Declare any non-default types here with import statements

interface IMyAidl {
    int add(int num1, int num2);
    void addPerson(in Person p);
    Person getPerson();
}
