package no.uio.ifi.in2000.testgit.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//City entity for database
@Entity(tableName = "cities")
data class City (
    @PrimaryKey(autoGenerate = true) // Default is true
    val cityId: Int = 0, // Autogenerates if null
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "latitude") val lat : Double,
    @ColumnInfo(name = "longitude") val lon : Double,
    @ColumnInfo(name = "favorite") var favorite : Int = 0,
    @ColumnInfo(name = "customized") var customized : Int = 0,
     )
