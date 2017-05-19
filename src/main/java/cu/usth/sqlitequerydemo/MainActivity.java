package cu.usth.sqlitequerydemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import bean.Person;
import util.Constant;
import util.DbManager;
import util.MySqliteHelper;

public class MainActivity extends AppCompatActivity {

    private MySqliteHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper= DbManager.getIntance(this);
    }

    public void createDb(View view){
        SQLiteDatabase db=helper.getWritableDatabase();
        for(int i=1;i<=30;i++){
            String sql="insert into "+Constant.TABLE_NAME+"("+Constant.NAME+","+Constant.AGE+") values('张三"+i+"',20)";
            db.execSQL(sql);
        }
        db.close();
    }
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_query:
                SQLiteDatabase db=helper.getWritableDatabase();
                String selectSql="select * from "+Constant.TABLE_NAME+"";
                Cursor cursor=DbManager.selectDataBySql(db,selectSql,null);
                List<Person > list=DbManager.cursorToList(cursor);
                for(Person p:list){
                    Log.i("tag",p.toString());
                }
                db.close();
                break;
            case R.id.btn_queryApi:
                db=helper.getWritableDatabase();
                /*
                query(String table, String[] columns, String selection,
                String[] selectionArgs, String groupBy, String having,
                String orderBy)
                    String table 表示查询的表名
                    String[] columns 表示查询表中的字段名称 null 查询所有
                    String selection 表示查询条件 where子句
                    String[] selectionArgs 表示查询条件占位符的取值
                    String groupBy 表示分组条件 group by子句
                    tring having 表示筛选条件 having子句
                    String orderBy 表示排序条件 order by子句
                 */
                cursor=db.query(Constant.TABLE_NAME,null,Constant._ID+">?"
                        ,new String[]{"10"},null,null,Constant._ID+" desc");
                list=DbManager.cursorToList(cursor);
                for (Person p:list){
                    Log.i("tag",p.toString());
                }
                break;
        }
    }
}
