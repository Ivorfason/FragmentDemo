package com.jskz.test;
 
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
 
public class ContactContentProvider extends ContentProvider {
 
         // 通过UriMatcher匹配外部请求
         private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
         // 通过openHelper进行数据库读写
         private DatabaseHelper openHelper;
         // 匹配状态常量
         private static final int CONTACT_LIST = 1;
         private static final int CONTACT = 2;
         // 表名
         private static final String tableName = "contacts";
         // 添加Uri
         static {
                   uriMatcher.addURI("com.changcheng.sqlite.provider", "contact",
                                     CONTACT_LIST);
                   uriMatcher.addURI("com.changcheng.sqlite.provider", "contact/#",
                                     CONTACT);
         }
 
         @Override
         public int delete(Uri uri, String selection, String[] selectionArgs) {
                   SQLiteDatabase db = this.openHelper.getWritableDatabase();
                  int result;
                   switch (uriMatcher.match(uri)) {
                   case CONTACT_LIST:
                            result = db.delete(tableName, selection, selectionArgs);
                            break;
                   case CONTACT:
                            long id = ContentUris.parseId(uri);
                            String where = "_id=" + id;
                            if (selection != null && !"".equals(selection)) {
                                     where = where + " and " + selection;
                            }
                            result = db.delete(tableName, where, selectionArgs);
                            break;
                   default:
                            throw new IllegalArgumentException("Uri IllegalArgument:" + uri);
                   }
                   return result;
         }
 
         @Override
         public String getType(Uri uri) {
                   switch (uriMatcher.match(uri)) {
                   case CONTACT_LIST:// 集合类型必须在前面加上vnd.android.cursor.dir/
                            return "vnd.android.cursor.dir/contactlist";
                   case CONTACT:// 非集合类型必须在前面加上vnd.android.cursor.item/
                            return "vnd.android.cursor.item/contact";
                   default:
                            throw new IllegalArgumentException("Uri IllegalArgument:" + uri);
                   }
         }
 
         @Override
         public Uri insert(Uri uri, ContentValues values) {
                   SQLiteDatabase db = this.openHelper.getWritableDatabase();
                   long id;
                   switch (uriMatcher.match(uri)) {
                   case CONTACT_LIST:
                            // 因为后台需要生成SQL语句，当values为null时，必须提第二个参数。生成的SQL语句才不会出错！
                            id = db.insert(tableName, "_id", values);
                            return ContentUris.withAppendedId(uri, id);
                   case CONTACT:
                            id = db.insert(tableName, "_id", values);
                            String uriPath = uri.toString();
                            String path = uriPath.substring(0, uriPath.lastIndexOf("/")) + id;
                            return Uri.parse(path);
                   default:
                            throw new IllegalArgumentException("Uri IllegalArgument:" + uri);
                   }
         }
 
         @Override
         public boolean onCreate() {
                   this.openHelper = new DatabaseHelper(this.getContext());
                   return true;
         }
 
         @Override
         public Cursor query(Uri uri, String[] projection, String selection,
                            String[] selectionArgs, String sortOrder) {
                   SQLiteDatabase db = this.openHelper.getWritableDatabase();
                   switch (uriMatcher.match(uri)) {
                   case CONTACT_LIST:
                            return db.query(tableName, projection, selection, selectionArgs,
                                               null, null, sortOrder);
                   case CONTACT:
                            long id = ContentUris.parseId(uri);
                            String where = "_id=" + id;
                            if (selection != null && !"".equals(selection)) {
                                     where = where + " and " + selection;
                            }
                            return db.query(tableName, projection, where, selectionArgs, null,
                                               null, sortOrder);
                   default:
                            throw new IllegalArgumentException("Uri IllegalArgument:" + uri);
                   }
         }
 
         @Override
         public int update(Uri uri, ContentValues values, String selection,
                            String[] selectionArgs) {
                   SQLiteDatabase db = this.openHelper.getWritableDatabase();
                   int result;
                   switch (uriMatcher.match(uri)) {
                   case CONTACT_LIST:
                            result = db.update(selection, values, selection, selectionArgs);
                            break;
                   case CONTACT:
                            long id = ContentUris.parseId(uri);
                            String where = "_id=" + id;
                            if (selection != null && !"".equals(selection)) {
                                     where = where + " and " + selection;
                            }
                            result = db.update(tableName, values, where, selectionArgs);
                            break;
                   default:
                            throw new IllegalArgumentException("Uri IllegalArgument:" + uri);
                   }
                   return result;
         }
 
}
