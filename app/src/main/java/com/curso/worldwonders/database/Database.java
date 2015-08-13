package com.curso.worldwonders.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.curso.worldwonders.database.tables.WondersTable;

/**
 * Created by Junior on 06/08/2015.
 */
public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "worldwonders";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(WondersTable.SQL_CREATE);

        String sqlMock1 = "INSERT INTO wonders VALUES(1, 'Machu Picchu', 'Peru', " +
                "'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras ac pulvinar justo. Phasellus dignissim dignissim metus vitae gravida. Cras sagittis erat lacus, eget mollis orci consequat molestie. Nullam accumsan feugiat sem, tincidunt placerat nisl efficitur ac. Quisque tempor velit ac massa pellentesque, vel condimentum risus dictum. Suspendisse potenti. In hac habitasse platea dictumst. Duis in mi eu velit cursus varius. Maecenas efficitur, nisi quis molestie molestie, metus diam tincidunt ipsum, bibendum sollicitudin sapien augue ac massa. Nunc eget pretium nisl. Cras eu ex sed orci facilisis aliquam nec id urna. Integer in massa orci. Phasellus nulla ipsum, porta ac neque eu, cursus sollicitudin sem. Aliquam semper libero a nibh commodo convallis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean bibendum vitae metus et rutrum. Praesent in nulla vitae urna scelerisque placerat ut vitae magna. Suspendisse sed pretium augue. Mauris quam risus, lacinia at pellentesque eu, convallis id augue. Nam mollis volutpat mauris, nec accumsan felis venenatis eu. Pellentesque elementum lacus in erat sollicitudin, non posuere orci lacinia. Sed rhoncus purus at ante venenatis porta.', 'http://atp.cx/wp-content/uploads/2011/02/Machu-Picchu1.jpg');";
        String sqlMock2 = "INSERT INTO wonders VALUES(2, 'Chichen Itza', 'Mexico', " +
                "'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras ac pulvinar justo. Phasellus dignissim dignissim metus vitae gravida. Cras sagittis erat lacus, eget mollis orci consequat molestie. Nullam accumsan feugiat sem, tincidunt placerat nisl efficitur ac. Quisque tempor velit ac massa pellentesque, vel condimentum risus dictum. Suspendisse potenti. In hac habitasse platea dictumst. Duis in mi eu velit cursus varius. Maecenas efficitur, nisi quis molestie molestie, metus diam tincidunt ipsum, bibendum sollicitudin sapien augue ac massa. Nunc eget pretium nisl. Cras eu ex sed orci facilisis aliquam nec id urna. Integer in massa orci. Phasellus nulla ipsum, porta ac neque eu, cursus sollicitudin sem. Aliquam semper libero a nibh commodo convallis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean bibendum vitae metus et rutrum. Praesent in nulla vitae urna scelerisque placerat ut vitae magna. Suspendisse sed pretium augue. Mauris quam risus, lacinia at pellentesque eu, convallis id augue. Nam mollis volutpat mauris, nec accumsan felis venenatis eu. Pellentesque elementum lacus in erat sollicitudin, non posuere orci lacinia. Sed rhoncus purus at ante venenatis porta. ', 'http://www.chichenitza.com/images/chichenitza.jpg');";
        String sqlMock3 = "INSERT INTO wonders VALUES(3, 'Christ Redeemer', 'Brazil', " +
                "'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras ac pulvinar justo. Phasellus dignissim dignissim metus vitae gravida. Cras sagittis erat lacus, eget mollis orci consequat molestie. Nullam accumsan feugiat sem, tincidunt placerat nisl efficitur ac. Quisque tempor velit ac massa pellentesque, vel condimentum risus dictum. Suspendisse potenti. In hac habitasse platea dictumst. Duis in mi eu velit cursus varius. Maecenas efficitur, nisi quis molestie molestie, metus diam tincidunt ipsum, bibendum sollicitudin sapien augue ac massa. Nunc eget pretium nisl. Cras eu ex sed orci facilisis aliquam nec id urna. Integer in massa orci. Phasellus nulla ipsum, porta ac neque eu, cursus sollicitudin sem. Aliquam semper libero a nibh commodo convallis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean bibendum vitae metus et rutrum. Praesent in nulla vitae urna scelerisque placerat ut vitae magna. Suspendisse sed pretium augue. Mauris quam risus, lacinia at pellentesque eu, convallis id augue. Nam mollis volutpat mauris, nec accumsan felis venenatis eu. Pellentesque elementum lacus in erat sollicitudin, non posuere orci lacinia. Sed rhoncus purus at ante venenatis porta.', 'http://siliconindia.com:81/travelcity/images/special_images/c9h17kR45vzd38x.jpeg');";
        String sqlMock4 = "INSERT INTO wonders VALUES(4, 'Great Wall', 'China', " +
                "'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras ac pulvinar justo. Phasellus dignissim dignissim metus vitae gravida. Cras sagittis erat lacus, eget mollis orci consequat molestie. Nullam accumsan feugiat sem, tincidunt placerat nisl efficitur ac. Quisque tempor velit ac massa pellentesque, vel condimentum risus dictum. Suspendisse potenti. In hac habitasse platea dictumst. Duis in mi eu velit cursus varius. Maecenas efficitur, nisi quis molestie molestie, metus diam tincidunt ipsum, bibendum sollicitudin sapien augue ac massa. Nunc eget pretium nisl. Cras eu ex sed orci facilisis aliquam nec id urna. Integer in massa orci. Phasellus nulla ipsum, porta ac neque eu, cursus sollicitudin sem. Aliquam semper libero a nibh commodo convallis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean bibendum vitae metus et rutrum. Praesent in nulla vitae urna scelerisque placerat ut vitae magna. Suspendisse sed pretium augue. Mauris quam risus, lacinia at pellentesque eu, convallis id augue. Nam mollis volutpat mauris, nec accumsan felis venenatis eu. Pellentesque elementum lacus in erat sollicitudin, non posuere orci lacinia. Sed rhoncus purus at ante venenatis porta.', 'http://img.timeinc.net/time/photoessays/2008/beijing_travel/great_wall_beijing.jpg');";
        String sqlMock5 = "INSERT INTO wonders VALUES(5, 'Taj Mahal', 'India', " +
                "'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras ac pulvinar justo. Phasellus dignissim dignissim metus vitae gravida. Cras sagittis erat lacus, eget mollis orci consequat molestie. Nullam accumsan feugiat sem, tincidunt placerat nisl efficitur ac. Quisque tempor velit ac massa pellentesque, vel condimentum risus dictum. Suspendisse potenti. In hac habitasse platea dictumst. Duis in mi eu velit cursus varius. Maecenas efficitur, nisi quis molestie molestie, metus diam tincidunt ipsum, bibendum sollicitudin sapien augue ac massa. Nunc eget pretium nisl. Cras eu ex sed orci facilisis aliquam nec id urna. Integer in massa orci. Phasellus nulla ipsum, porta ac neque eu, cursus sollicitudin sem. Aliquam semper libero a nibh commodo convallis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean bibendum vitae metus et rutrum. Praesent in nulla vitae urna scelerisque placerat ut vitae magna. Suspendisse sed pretium augue. Mauris quam risus, lacinia at pellentesque eu, convallis id augue. Nam mollis volutpat mauris, nec accumsan felis venenatis eu. Pellentesque elementum lacus in erat sollicitudin, non posuere orci lacinia. Sed rhoncus purus at ante venenatis porta.', 'http://www.tajmahal.com/images/taj_mahal_india.jpg');";
        String sqlMock6 = "INSERT INTO wonders VALUES(6, 'Petra', 'Jordan', " +
                "'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras ac pulvinar justo. Phasellus dignissim dignissim metus vitae gravida. Cras sagittis erat lacus, eget mollis orci consequat molestie. Nullam accumsan feugiat sem, tincidunt placerat nisl efficitur ac. Quisque tempor velit ac massa pellentesque, vel condimentum risus dictum. Suspendisse potenti. In hac habitasse platea dictumst. Duis in mi eu velit cursus varius. Maecenas efficitur, nisi quis molestie molestie, metus diam tincidunt ipsum, bibendum sollicitudin sapien augue ac massa. Nunc eget pretium nisl. Cras eu ex sed orci facilisis aliquam nec id urna. Integer in massa orci. Phasellus nulla ipsum, porta ac neque eu, cursus sollicitudin sem. Aliquam semper libero a nibh commodo convallis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean bibendum vitae metus et rutrum. Praesent in nulla vitae urna scelerisque placerat ut vitae magna. Suspendisse sed pretium augue. Mauris quam risus, lacinia at pellentesque eu, convallis id augue. Nam mollis volutpat mauris, nec accumsan felis venenatis eu. Pellentesque elementum lacus in erat sollicitudin, non posuere orci lacinia. Sed rhoncus purus at ante venenatis porta.', 'http://www.culturefocus.com/jordan/pictures/petra-26small.jpg');";
        String sqlMock7 = "INSERT INTO wonders VALUES(7, 'Colosseum', 'Italy', " +
                "'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras ac pulvinar justo. Phasellus dignissim dignissim metus vitae gravida. Cras sagittis erat lacus, eget mollis orci consequat molestie. Nullam accumsan feugiat sem, tincidunt placerat nisl efficitur ac. Quisque tempor velit ac massa pellentesque, vel condimentum risus dictum. Suspendisse potenti. In hac habitasse platea dictumst. Duis in mi eu velit cursus varius. Maecenas efficitur, nisi quis molestie molestie, metus diam tincidunt ipsum, bibendum sollicitudin sapien augue ac massa. Nunc eget pretium nisl. Cras eu ex sed orci facilisis aliquam nec id urna. Integer in massa orci. Phasellus nulla ipsum, porta ac neque eu, cursus sollicitudin sem. Aliquam semper libero a nibh commodo convallis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean bibendum vitae metus et rutrum. Praesent in nulla vitae urna scelerisque placerat ut vitae magna. Suspendisse sed pretium augue. Mauris quam risus, lacinia at pellentesque eu, convallis id augue. Nam mollis volutpat mauris, nec accumsan felis venenatis eu. Pellentesque elementum lacus in erat sollicitudin, non posuere orci lacinia. Sed rhoncus purus at ante venenatis porta.', 'http://www.altiusdirectory.com/Arts/images/Colosseum.jpg');";

        db.execSQL(sqlMock1);
        db.execSQL(sqlMock2);
        db.execSQL(sqlMock3);
        db.execSQL(sqlMock4);
        db.execSQL(sqlMock5);
        db.execSQL(sqlMock6);
        db.execSQL(sqlMock7);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
