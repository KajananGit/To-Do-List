package com.example.to_do_list.service;

import com.example.to_do_list.models.Task;
import com.example.to_do_list.models.TaskStatus;
import com.example.to_do_list.models.User;
import com.example.to_do_list.repositories.TaskRepository;
import com.example.to_do_list.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(Map<String, Object> fields, long id){
        User userUpdate = userRepository.findById(id).get();
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(User.class, key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, userUpdate, value);
        });
        return userRepository.save(userUpdate);
    }

    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users;
    }

    public Optional<User> getUserById(long id){
        return userRepository.findById(id);

    }

    public void deleteUserById(long id){
        User user = userRepository.findById(id).get();
        for(Task task : user.getTasks()){
            taskRepository.delete(task);
        }
        userRepository.deleteById(id);
    }

}
