package com.example.test.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.dataModels.User
import com.example.test.databinding.UserItemBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class UserAdapter(private val users: MutableList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvName.text = "${user.name.first} ${user.name.last}"
            binding.tvAgeCountry.text = "${user.dob.age} years from ${user.location.country}"

            binding.tvTimestamp.text = getLocalTime(user.location.timezone.offset)

            Glide.with(binding.root.context)
                .load(user.picture.thumbnail)
                .circleCrop()
                .into(binding.ivProfile)

            binding.ivAttachment.visibility = if (shouldShowAttachment()) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    fun addUsers(newUsers: List<User>) {
        val startPos = users.size
        users.addAll(newUsers)
        notifyItemRangeInserted(startPos, newUsers.size)
    }

    private fun getLocalTime(offset: String): String {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        return try {
            val utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

            // Parse the offset format
            val sign = if (offset.startsWith("+")) 1 else -1
            val parts = offset.substring(1).split(":")
            val hours = parts[0].toInt()
            val minutes = parts[1].toInt()

            // Apply offset
            utcCalendar.add(Calendar.HOUR_OF_DAY, sign * hours)
            utcCalendar.add(Calendar.MINUTE, sign * minutes)

            // Format the local time
            return timeFormat.format(utcCalendar.time)
        } catch (e: Exception) {
            e.printStackTrace()
            val deviceCalendar = Calendar.getInstance()
            timeFormat.format(deviceCalendar.time)
        }
    }

    private fun shouldShowAttachment(): Boolean {
        return (0..1).random() == 1
    }
}