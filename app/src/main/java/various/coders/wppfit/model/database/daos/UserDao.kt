package various.coders.wppfit.model.database.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import various.coders.wppfit.model.database.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE uid = :id LIMIT 1")
    fun getUser(id: Int): LiveData<User>

    @Query("SELECT * FROM user WHERE uid = (SELECT MAX(uid) FROM user)")
    fun getNewestUser(): LiveData<User>

    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData<List<User>>

    @Update
    fun updateUser(user: User)

    @Insert
    fun newUser(user: User)

    @Delete
    fun deleteUser(user: User)
}