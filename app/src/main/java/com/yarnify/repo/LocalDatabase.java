/***************************************************************************************
 * Title: Mobile App Development with Android and Java
 * Author: Frank McCown, Associate Professor of Computer Science, Harding University
 * Date: 2018-2022
 * Code version: Java
 * Availability: https://www.zybooks.com/catalog/mobile-app-development/
 *
 ***************************************************************************************/

package com.yarnify.repo;

import androidx.room.RoomDatabase;

import com.yarnify.model.Needle;
import com.yarnify.model.Pattern;
import com.yarnify.model.User;
import com.yarnify.model.Yarn;

@androidx.room.Database( entities = { User.class, Yarn.class, Needle.class, Pattern.class }, version = 1,
        exportSchema = false )
abstract class LocalDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();
    public abstract YarnDAO yarnDAO();
    public abstract NeedleDAO needleDAO();

    public abstract PatternDAO patternDAO();
}
