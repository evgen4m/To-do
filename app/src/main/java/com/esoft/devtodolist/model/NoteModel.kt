package com.esoft.devtodolist.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class NoteModel(): Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "text")
    var text: String? = null

    @ColumnInfo(name = "textHead")
    var textHead: String? = null

    @ColumnInfo(name = "timestamp")
    var timestamp: Long = 0

    @ColumnInfo(name = "dataCalendar")
    var dataCalendar: String? = null

    @ColumnInfo(name = "notificationTime")
    var notifTime: String? = null

    @ColumnInfo(name = "timeCreate")
    var timeCreate: String? = null

    @ColumnInfo(name = "done")
    var done: Boolean = false

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        text = parcel.readString().toString()
        timestamp = parcel.readLong()
        done = parcel.readByte() != 0.toByte()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NoteModel

        if (id != other.id) return false
        if (text != other.text) return false
        if (timestamp != other.timestamp) return false
        if (done != other.done) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + text.hashCode()
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + done.hashCode()
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(text)
        parcel.writeLong(timestamp)
        parcel.writeByte(if (done) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteModel> {
        override fun createFromParcel(parcel: Parcel): NoteModel {
            return NoteModel(parcel)
        }

        override fun newArray(size: Int): Array<NoteModel?> {
            return arrayOfNulls(size)
        }
    }

}