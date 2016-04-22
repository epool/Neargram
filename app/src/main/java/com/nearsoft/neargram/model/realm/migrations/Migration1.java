package com.nearsoft.neargram.model.realm.migrations;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * First migration.
 * Created by epool on 4/22/16.
 */
public class Migration1 implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema realmSchema = realm.getSchema();

        RealmObjectSchema captionSchema = realmSchema.get("Caption");
        captionSchema.setNullable("id", true);

        RealmObjectSchema commentSchema = realmSchema.get("Comment");
        commentSchema.setNullable("id", true);

        RealmObjectSchema photoSchema = realmSchema.get("Photo");
        photoSchema.setNullable("id", true);

        RealmObjectSchema userSchema = realmSchema.get("User");
        userSchema.setNullable("id", true);
    }

}
