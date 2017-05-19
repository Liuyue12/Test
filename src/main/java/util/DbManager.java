package util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import bean.Person;

/**
 * Created by Liuyue on 2017/5/12.
 */
public class DbManager {
    private static MySqliteHelper helper;
    public static MySqliteHelper getIntance(Context context){
        if(helper==null){
            helper=new MySqliteHelper(context);
        }
        return helper;
    }
    public static Cursor selectDataBySql(SQLiteDatabase db,String sql,String[] selectionArgs){
        Cursor cursor=null;
        if(db!=null){
            cursor=db.rawQuery(sql,selectionArgs);
        }
        return cursor;
    }
    /*
    将查询的cursor对象转换成List集合
    cursor 游标对象
    返回值 集合对象
     */
    public static List<Person> cursorToList(Cursor cursor){
        List<Person> list=new ArrayList<>();
        while(cursor.moveToNext()){
            //getColumnIndex(String columnName) 根据参数中指定的字段名称获取字段下标
            int columnIndex=cursor.getColumnIndex(Constant._ID);
            //getInt(int columnIndex) 根据参数中指定的字段下标，获取对应int类型的value
            int _id=cursor.getInt(columnIndex);

            String name=cursor.getString(cursor.getColumnIndex(Constant.NAME));
            int age=cursor.getInt(cursor.getColumnIndex(Constant.AGE));

            Person person=new Person(_id,name,age);
            list.add(person);
        }
        return list;
    }
}
