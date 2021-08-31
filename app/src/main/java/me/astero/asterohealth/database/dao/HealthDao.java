package me.astero.asterohealth.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import me.astero.asterohealth.database.objects.GridData;
import me.astero.asterohealth.database.objects.NotesData;

@Dao
public interface HealthDao {

    @Insert
    void insertAll(GridData... info);

    @Delete
    void delete(GridData info);

    @Query("SELECT * FROM griddata WHERE type = 'physical_activities'")
    List<GridData> getAllPhysicalActivities();

    @Query("SELECT * FROM griddata WHERE type = 'water_intake'")
    List<GridData> getAllWaterIntake();

    @Query("SELECT * FROM griddata WHERE type = 'vital_stats'")
    List<GridData> getAllVital();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GridData gridData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NotesData notesData);

    @Query("SELECT * FROM notesdata")
    List<NotesData> getAllNotes();
}