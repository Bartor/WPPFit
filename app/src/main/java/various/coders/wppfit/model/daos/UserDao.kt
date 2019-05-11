package various.coders.wppfit.model.daos

import androidx.room.*
import various.coders.wppfit.model.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE uid = :id LIMIT 1")
    fun getUser(id: Int): User

    @Update
    fun updateUser(user: User)

    @Insert
    fun newUser(user: User)

    @Delete
    fun deleteUser(user: User)
}