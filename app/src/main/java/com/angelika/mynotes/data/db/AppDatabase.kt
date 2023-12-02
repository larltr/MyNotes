package com.angelika.mynotes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.angelika.mynotes.data.db.daos.NoteDao
import com.angelika.mynotes.data.models.NoteModel

@Database(entities = [NoteModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}