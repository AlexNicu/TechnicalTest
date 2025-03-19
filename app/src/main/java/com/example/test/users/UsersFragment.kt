package com.example.test.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.databinding.FragmentUsersBinding

/**
 * The User Fragment class
 */
class UsersFragment : Fragment() {
    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UsersViewModel by viewModels()
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        adapter = UserAdapter(mutableListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Observe ViewModel
        viewModel.users.observe(viewLifecycleOwner) { users ->
            adapter.addUsers(users)
        }

        // Fetch First Page
        viewModel.fetchUsers(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}