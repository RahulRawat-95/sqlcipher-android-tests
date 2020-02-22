package net.zetetic.tests;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.zetetic.tests.support.EncryptedRoomTest;

public class NegativeCursorTestTwo extends SQLCipherTest {

    @Override
    public boolean execute(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE `ParentEntity` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `intValue` INTEGER NOT NULL, `floatValue` REAL NOT NULL, `doubleValue` REAL NOT NULL, `charValue` INTEGER NOT NULL, `boolValue` INTEGER NOT NULL)");
        database.execSQL("CREATE TABLE `ChildEntity` (`uuid` TEXT NOT NULL, `stringValue` TEXT, `parentId` INTEGER NOT NULL, PRIMARY KEY(`uuid`), FOREIGN KEY(`parentId`) REFERENCES `ParentEntity`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        database.execSQL("INSERT INTO ParentEntity (id, intValue, floatValue, doubleValue, charValue, boolValue) VALUES ('1', '1337', '2.71828007698059', '3.14159', '120', '1')");
        database.execSQL("INSERT INTO ChildEntity (uuid, stringValue, parentId) VALUES ('8fa35fb0-969d-4a25-8404-271997f37483', 'Um, hi!', '1')");
        database.execSQL("INSERT INTO ChildEntity (uuid, stringValue, parentId) VALUES ('ff5f54a3-8499-4e00-8ec3-3654055ac47e', 'And now for something completely different', '1');");
        Cursor cursor = database.rawQuery("SELECT * FROM ChildEntity;", new EncryptedRoomTest.ChildEntity[]{});
        Cursor cursorNegativeOne = database.rawQuery("SELECT * FROM ChildEntity where parentId = -1 or -1 = -1;", new EncryptedRoomTest.ChildEntity[]{});
        if (cursor != null && cursorNegativeOne != null) {
            return cursor.getCount() == cursorNegativeOne.getCount();
        }
        return false;
    }

    @Override
    public String getName() {
        return "Negative Cursor Test 2";
    }
}
