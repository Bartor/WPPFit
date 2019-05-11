package various.coders.wppfit.model.daos

import android.arch.persistence.room.*
import various.coders.wppfit.model.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE uid = :id LIMIT 1")
    suspend fun getUser(id: Int): User

    @Update
    suspend fun updateUser(user: User)

    @Insert
    suspend fun newUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}